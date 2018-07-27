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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GameServiceImpl extends Thread implements GameService {

    private Socket socket;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream clientInput;

    private int duration;
    private List<Card> cards;
    private AtomicLong atomicRoundId;
    private AtomicInteger atomicClientsConnected;

    public GameServiceImpl(Socket socket,AtomicInteger atomicClientsConnected, AtomicLong atomicRoundId) throws IOException {
        this.socket = socket;
        serverOutput = new ObjectOutputStream(socket.getOutputStream());
        clientInput = new ObjectInputStream(socket.getInputStream());

        duration = 10;
        cards = createCardDeck();
        this.atomicRoundId = atomicRoundId;
        this.atomicClientsConnected = atomicClientsConnected;
    }

    @Override
    public void run() {
        while (socket != null) {
            try {
                Object objectRead; // player responses will be referenced to this, later will be cast correctly
                boolean isPlayerRight = false; // will stay false if player fails to respond
                if (!isNextBaseCardPossible(cards)) {
                    addNewCardDeck(cards, createCardDeck());
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
                StartRoundRequest roundRequest = new StartRoundRequest(duration, System.currentTimeMillis(), atomicRoundId.incrementAndGet(), cards.get(0));
                serverOutput.writeObject(roundRequest);
                // Waiting for a player action
                objectRead = clientInput.readObject();
                // Analyzing players response
                if (objectRead instanceof PlayerActionRequest) {
                    if (((PlayerActionRequest) objectRead).getPlayerAction() != null) {
                        isPlayerRight = isPlayerActionRight((PlayerActionRequest)objectRead, cards);
                    }
                }
                // Creating an instance of FinishRoundRequest and sending it to client
                serverOutput.writeObject(new FinishRoundRequest(atomicRoundId.get(), isPlayerRight));
            } catch (Exception e) {
                System.out.println("A client has disconnected!");
                System.out.println("Clients connected: " + atomicClientsConnected.decrementAndGet());
                this.stop();
            }
            removeCurrentBaseCard(cards);
        }
    }

    @Override
    public PlayerActionResponse playerAction(PlayerActionRequest playerActionRequest) {
        return new PlayerActionResponse("");
    }

    @Override
    public SetBaseCardResponse setBaseCard(SetBaseCardRequest setBaseCardRequest) {
        cards.set(0, setBaseCardRequest.getBaseCard());
        return new SetBaseCardResponse("");
    }

    /**
     * Creates a list of cards
     * @return a list of 52 cards
     */
    private List createCardDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        for(Card card : Card.values()) {
            cards.add(card);
            Collections.shuffle(cards);
        }
        return cards;
    }

    /**
     * Checks if player made the right decision
     * @param playerActionRequest players action
     * @param cards list of cards
     * @return returns true if the player responded with the correct PlayerAction, otherwise false
     */
    private boolean isPlayerActionRight(PlayerActionRequest playerActionRequest, List<Card> cards) {
        PlayerAction rightAction = getRightAction(cards);
        PlayerAction playerAction = playerActionRequest.getPlayerAction();
        return playerAction.compareTo(rightAction) == 0;
    }

    /**
     * Compares the value of current base card to the next base card
     * @param cards list of cards
     * @return returns enum PlayerAction
     */
    private PlayerAction getRightAction(List<Card> cards) {
        CardRank currentBaseCard = cards.get(0).getValue();
        CardRank nextBaseCard = cards.get(1).getValue();
        if (currentBaseCard.compareTo(nextBaseCard) > 0) {
            return PlayerAction.LOWER;
        }
        if (currentBaseCard.compareTo(nextBaseCard) < 0) {
            return PlayerAction.HIGHER;
        }
        return PlayerAction.EQUALS;
    }

    /**
     * Checks whether next card would be possible
     * @param cards List
     * @return returns true if there is more than 1 element left in the list, otherwise false
     */
    private boolean isNextBaseCardPossible(List<Card> cards) {
        return cards.size() > 1;
    }

    /**
     * Adds new list of cards to the old list of cards
     * @param lastCardDeck existing list
     * @param newDeck new list
     */
    private void addNewCardDeck(List<Card> lastCardDeck, List<Card> newDeck) {
        lastCardDeck.addAll(newDeck);
    }

    /**
     * Removes first element from the list of cards
     * @param cards list that contains cards
     */
    private void removeCurrentBaseCard(List<Card> cards) {
        cards.remove(0);
    }
}
