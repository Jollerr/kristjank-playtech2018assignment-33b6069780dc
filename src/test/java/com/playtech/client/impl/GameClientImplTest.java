package com.playtech.client.impl;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Server(package com.playtech.server.impl) must be running to test units
 */
public class GameClientImplTest {
    int port = 9090;

    /**
     * GameClientImplTest.createCountdownTimer();
     * Parameter 2 => ~2000 ms
     */
    @Test
    public void createCountdownTimer() {
        long start;
        long end;
        long result;
        start = System.currentTimeMillis();
        try {
            GameClientImpl gameClientImpl = new GameClientImpl(new Socket(InetAddress.getByName("127.0.0.1"), port));
            gameClientImpl.createCountdownTimer(2).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        result = (end - start) / 1000;
        // Check that result is 2
        assertEquals(2, result);
    }
}
