package com.playtech.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        InputStreamReader userInputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(userInputStream);
        String ipAddress = "";
        boolean isValid = false;
        //// Client has to choose a valid IP address
        while (!isValid) {
            System.out.print("Please enter a valid server IP address (press enter to continue with localhost): ");
            try {
                ipAddress = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ipAddress.equals("")) { // if no input received from the user, IP address will be chosen
                ipAddress = "127.0.0.1";
            }
            isValid = validateIpAddress(ipAddress); // if the IP address was incorrect, the user has to try again
        }

        // Client has to choose a valid port number
        int portNumber = 0;
        isValid = false;
        System.out.println("Select server port number! The valid range of ports is 0 - 65536.");
        while (!isValid) {
            try {
                System.out.print("Please enter a port number: ");
                portNumber = Integer.parseInt(reader.readLine());
                if (validatePortNumber(portNumber)) {
                    isValid = true;
                } else {
                    System.out.println("Port " + portNumber + " is out of range!");
                }
            } catch (Exception e) {
                System.out.println("Port number must contain number only!");
            }
        }
        //// Connecting to the server
        System.out.println("Trying to connect to the server...");
        InetAddress serverAddress;
        Socket socket;
        int timeOut = 15000;
            try {
                serverAddress = InetAddress.getByName(ipAddress);
                socket = new Socket(serverAddress, portNumber);
                socket.setSoTimeout(timeOut);
                //Starting the game implementation
                GameClientImpl gameClient = new GameClientImpl(socket);
                gameClient.run();
            } catch (IOException e) {
                System.out.println("Server not found, please try again!");
                main(null); // if server was not found, main will loop again
            }
    }

    /**
     * Checks whether IP address has 4 numbers separated with dots and each number is 8bit
     * @param ipAddress String representation of an IP address
     * @return returns true if IP address if formatted correctly, otherwise false
     */
    private static boolean validateIpAddress(String ipAddress) {
        String[] numbers = ipAddress.split("\\.");
        if (numbers.length != 4) {
            return false;
        }
        for(String str: numbers) {
            int i;
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return false;
            }
            if((i<0) || (i>255)) {
                return false;
            }
        }
        return true;
    }

    /**
     * TCP header is limited to 16-bits for the source/destination port field.
     * @param port port number to be checked
     * @return returns true if the port number is in range 0-65536, otherwise false
     */
    private static boolean validatePortNumber(int port) {
        return port >= 0 && port <= 65536;
    }
}

