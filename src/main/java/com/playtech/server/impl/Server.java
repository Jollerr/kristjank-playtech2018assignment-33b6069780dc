package com.playtech.server.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Server {
    public static void main(String[] args) {
        int serverPort = chooseServerPort();
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            AtomicInteger atomicClientsConnected = new AtomicInteger(0);
            AtomicLong atomicRoundId = new AtomicLong(0);
            System.out.println("Server has been started!");
            while (true) { // creates a thread for every connect client
                System.out.println("Clients connected: " + atomicClientsConnected.get());
                Socket clientSocket = serverSocket.accept();
                atomicClientsConnected.incrementAndGet();
                GameServiceImpl serverThread = new GameServiceImpl(clientSocket,atomicClientsConnected, atomicRoundId);
                serverThread.start();
            }
        } catch (IOException e) {
            System.out.println("The port " + serverPort + " is already opened! Please use another port number (0 to 65535)!");
        }
    }

    /**
     * Asks for a port number from the console until the number passes checks
     * @return port number
     */
    private static int chooseServerPort() {
        int portNumber = 0;
        boolean validPort = false;
        System.out.println("A port number must be chosen! The valid range of ports is 0 - 65536.");
        while (!validPort) {
            try {
                System.out.print("Enter a port number: ");
                InputStreamReader userInputStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(userInputStream);
                // Requiring console input
                portNumber = Integer.parseInt(reader.readLine()) ;
                // Validating port number
                if (validatePortNumber(portNumber)) {
                    validPort = true;
                } else {
                    System.out.println("Port " + portNumber + " is out of range!");
                }
                // Checking if port number is listening
                ServerSocket testSocket = new ServerSocket(portNumber);
                testSocket.close();
            } catch (IOException e) {
                System.out.println("Port " + portNumber + " is not open!");
                System.out.println("Please try another one!");
            } catch (Exception e){
                System.out.println("Port number must contain number only!");
            }
        }
        return portNumber;
    }

    /**
     * TCP header is limited to 16-bits for the source/destination port field.
     * @param port port number to be checked.
     * @return returns true if the port number is in range 0-65536, otherwise false.
     */
    private static boolean validatePortNumber(int port) {
        return port >= 0 && port <= 65536;
    }
}
