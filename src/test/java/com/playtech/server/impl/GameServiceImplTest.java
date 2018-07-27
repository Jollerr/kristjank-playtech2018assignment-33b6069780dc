package com.playtech.server.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class GameServiceImplTest {

    @BeforeAll
    public static void setup() {
        Thread server = new Thread() {
            @Override
            public void run() {
                Server.main(null);
            }
        };
    }

    @AfterAll
    public static void close() {
        System.exit(0);
    }

    @Test
    public void test() {

    }

}
