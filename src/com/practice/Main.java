package com.practice;

// this daemon is designed to interface between git repository
// and the app install directory to automate git functions and
// automate app updates

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
This Microservice does nothing until updater is functional
 */

public class Main {
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static final String userRoot = "C:/Users/bolinger/Desktop/test install/";

    public static void main(String[] args) {
        logger.log(Level.INFO, "Main Thread - Start");

//        liveUpdateTest();

        var configPath = Paths.get(userRoot + "programFiles/config/DBdaemon.config");

           var daemon = new Daemon(configPath);

        daemon.start();

        logger.log(Level.INFO, "Main Thread - Exit");
    }

    private static void liveUpdateTest() {
        try {
            var path = Paths.get(userRoot + "test.txt");

            Files.createFile(path);
        } catch (IOException ignore) {
        }
    }
}
