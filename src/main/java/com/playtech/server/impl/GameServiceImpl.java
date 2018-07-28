package com.playtech.server.impl;

import com.playtech.common.CardRank;
import com.playtech.common.PlayerAction;
import com.playtech.game.protocol.FinishRoundRequest;
import com.playtech.game.protocol.PlayerActionRequest;
import com.playtech.game.protocol.PlayerActionResponse;
import com.playtech.game.protocol.StartRoundRequest;
import com.playtech.server.api.GameService;
import com.playtech.server.api.SetBaseCardRequest;
import com.playtech.server.api.SetBaseCardResponse;
import com.playtech.common.Card;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GameServiceImpl extends Thread implements GameService {

    private Socket socket;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream clientInput;
    private int duration;
    private AtomicLong atomicRoundId;
    private AtomicInteger atomicClientsConnected;
    private Deck deck;
    private Object objectRead;

    public GameServiceImpl(Socket socket,AtomicInteger atomicClientsConnected, AtomicLong atomicRoundId) throws IOException {
        this.socket = socket;
        serverOutput = new ObjectOutputStream(socket.getOutputStream());
        clientInput = new ObjectInputStream(socket.getInputStream());
        duration = 10;
        this.atomicRoundId = atomicRoundId;
        this.atomicClientsConnected = atomicClientsConnected;
        deck = new Deck();
    }

    @Override
    public void run() {
        while (socket != null) {
            try {
                objectRead = null; // player responses will be referenced to this, later will be cast explicitly
                boolean isPlayerRight = false; // will stay false if player fails to respond
                if(!deck.isNextBasePossible()) {
                    deck.generateNewDeck();
                }
                serverOutput.writeObject("Press enter to start a round...");
                objectRead = clientInput.readObject(); // Waiting for a client response - to continue or set new base card
                if (objectRead instanceof String) { // player wants to continue
                }
                if (objectRead instanceof Card) { // players wants to set a new base card
                    SetBaseCardRequest setBaseCardRequest = new SetBaseCardRequest((Card)objectRead);
                    serverOutput.writeObject(setBaseCard(setBaseCardRequest));
                }

                // Creating an instance of StartRoundRequest and sending it to the client
                StartRoundRequest roundRequest = new StartRoundRequest(duration, System.currentTimeMillis(), atomicRoundId.incrementAndGet(), deck.getCurrentCard());
                serverOutput.writeObject(roundRequest);

                // Waiting for player response
                long startTime = System.currentTimeMillis();
                objectRead = clientInput.readObject();
                long endTime = System.currentTimeMillis();
                if (endTime - startTime > duration * 1000 + 300) { // if response came in late it's auto-loss
                    objectRead = new PlayerActionRequest(null);
                    System.out.println("Round ID: #" + atomicRoundId.get() + " Players response was delayed over countdown timer!");
                }

                // Analyzing players response
                if (objectRead instanceof PlayerActionRequest) {
                    if (((PlayerActionRequest) objectRead).getPlayerAction() != null) {
                        isPlayerRight = isPlayerActionRight((PlayerActionRequest)objectRead, deck);
                    }
                }
                // Creating an instance of FinishRoundRequest and sending it to client
                serverOutput.writeObject(new FinishRoundRequest(atomicRoundId.get(), isPlayerRight));
                deck.nextCard();
            } catch (Exception e) {
                System.out.println("A client has disconnected!");
                System.out.println("Clients connected: " + atomicClientsConnected.decrementAndGet());
                Thread.currentThread().stop();
            }
        }
    }

    @Override
    public PlayerActionResponse playerAction(PlayerActionRequest playerActionRequest) {
        return new PlayerActionResponse("");
    }

    @Override
    public SetBaseCardResponse setBaseCard(SetBaseCardRequest setBaseCardRequest) {
        deck.setCurrentCard(setBaseCardRequest.getBaseCard());
        return new SetBaseCardResponse("");
    }

    /**
     * Checks if player made the right decision
     * @param playerActionRequest players action
     * @param deck Object representation of cards
     * @return returns true if the player responded with the correct PlayerAction, otherwise false
     */
    private boolean isPlayerActionRight(PlayerActionRequest playerActionRequest, Deck deck) {
        PlayerAction rightAction = getRightAction(deck);
        PlayerAction playerAction = playerActionRequest.getPlayerAction();
        return playerAction.compareTo(rightAction) == 0;
    }

    /**
     * Compares the value of current base card to the next base card
     * @param deck Object representation of cards
     * @return returns enum PlayerAction
     */
    private PlayerAction getRightAction(Deck deck) {
        CardRank currentBaseCard = deck.getCurrentCard().getValue();
        CardRank nextBaseCard = deck.getNextExpectedCard().getValue();
        if (currentBaseCard.compareTo(nextBaseCard) > 0) {
            return PlayerAction.LOWER;
        }
        if (currentBaseCard.compareTo(nextBaseCard) < 0) {
            return PlayerAction.HIGHER;
        }
        return PlayerAction.EQUALS;
    }


}
