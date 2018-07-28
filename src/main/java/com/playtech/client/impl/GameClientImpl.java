package com.playtech.client.impl;

import com.playtech.client.api.GameClient;
import com.playtech.common.Card;
import com.playtech.common.PlayerAction;
import com.playtech.game.protocol.FinishRoundRequest;
import com.playtech.game.protocol.PlayerActionRequest;
import com.playtech.game.protocol.StartRoundRequest;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameClientImpl implements GameClient {

    private ObjectInputStream serverInput;
    private ObjectOutputStream clientOutput;
    private BufferedReader consoleInput;
    private HashMap<String, Card> cards;

    public GameClientImpl(Socket socket) throws IOException {
        clientOutput = new ObjectOutputStream(socket.getOutputStream());
        serverInput = new ObjectInputStream(socket.getInputStream());
        consoleInput = new BufferedReader(new InputStreamReader(System.in));
        cards = createCardsHashMap();
    }

    public void run() {
        System.out.println("Hi-Lo card game started." +
                "\nYou have to guess if next card drawn from the deck is equal, higher or lower to the base card." +
                "\nYou have 10 seconds to make an action!");
        while (true) {
            try {
                Object objectRead = serverInput.readObject(); // waiting for server
                if (objectRead instanceof StartRoundRequest) {
                    startRound((StartRoundRequest) objectRead);
                } else if (objectRead instanceof FinishRoundRequest) {
                    finishRound((FinishRoundRequest) objectRead);
                } else if (objectRead instanceof String) {
                    System.out.println(objectRead);
                    if(consoleInput.readLine().equals("SET BASE")) {
                        Card chosenBaseCard = chooseBaseCard();
                        clientOutput.writeObject(chosenBaseCard);
                        serverInput.readObject();
                    } else {
                        clientOutput.writeObject("");
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection lost with server!");
                System.exit(0);
            }
        }
    }

    @Override
    public void startRound(StartRoundRequest startRoundRequest) {
        Card baseCard = startRoundRequest.getBaseCard();
        // Printing instructions to the client
        System.out.println("The base card is: " + baseCard + "" +
                "\nChoose option and press enter:" +
                "\n EQUAL   - type 1" +
                "\n HIGHER  - type 2" +
                "\n LOWER   - type 3");
        // Getting player action
        PlayerAction playerAction = playerInputWithCountdown();
        System.out.println("Your guess: " + playerAction);
        // Sending answer for the server to check
        try {
            clientOutput.writeObject(new PlayerActionRequest(playerAction));
        } catch (IOException e) {
            System.out.println("Could not send your action to the server!");
        }
    }

    @Override
    public void finishRound(FinishRoundRequest finishRoundRequest) {
        long roundId = finishRoundRequest.getRoundId();
        String result;
        if (finishRoundRequest.isWin()) {
            result = "WON";
        } else {
            result = "LOST";
        }
        System.out.println("Round ID: #" + roundId + ": You have " + result + "!");
    }


    /**
     * Choose a new base card to be sent to the server.
     * @return chosen card
     */
    private Card chooseBaseCard() {
        System.out.print("Choose base card: ");
        String chosenCard = "";
        while(!cards.containsKey(chosenCard)) {
            try {
                chosenCard = consoleInput.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cards.get(chosenCard));
        return cards.get(chosenCard);
    }


    /**
     * Creates a HashMap object of cards. Helps to manually set a new base bard.
     * @return HashMap, Key: String, Value: Card
     */
    private HashMap<String, Card> createCardsHashMap() {
        HashMap<String, Card> cards = new HashMap<>();
        int i = 0;
        for (Card card : Card.values()) {
            cards.put(card.toString(), card.values()[i]);
            i++;
        }
        return cards;
    }

    /**
     * Creates a thread that will print countdown time to console.
     * @param setTime int number that will be decrementing every 1000 ms
     * @return Thread object
     */
    public Thread createCountdownTimer(int setTime) {
        return new Thread(() -> {
            int time;
            time = setTime;
            while (time > 0) {
                System.out.println(time);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time--;
            }
            System.out.println(time);
        });
    }

    /**
     * Asks for a valid player input while countdown thread is alive.
     * @return returns decided PlayerAction or null if no valid playerAction was given
     */
    private PlayerAction playerInputWithCountdown() {
        System.out.println("Guess now!");
        // Creating Thread countDownTimer
        Thread countdownTimer = createCountdownTimer(10);
        countdownTimer.start();
        // Storing valid player input into an ArrayList
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1");
        commands.add("2");
        commands.add("3");
        String input = "";
        // Looping until countdownTimer is alive or user inputs valid command
        while (!commands.contains(input) && countdownTimer.isAlive()) {
            try {
                while (!consoleInput.ready() && countdownTimer.isAlive()) {
                    Thread.sleep(100);
                }
                if (consoleInput.ready()) {
                    input = consoleInput.readLine();
                }
                if (commands.contains(input)) {
                    countdownTimer.stop();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //
        if (input.equals("1")) {
            return PlayerAction.EQUALS;
        }

        if (input.equals("2")) {
            return PlayerAction.HIGHER;
        }

        if (input.equals("3")) {
            return PlayerAction.LOWER;
        }
        return null;
    }

    /**
     * Pauses the GameClientImpl for a specified time.
     * @param milliseconds how long should wait
     */
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


