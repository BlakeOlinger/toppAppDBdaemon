package com.practice;

// this daemon is designed to interface between git repository
// and the app install directory to automate git functions and
// automate app updates

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        logger.log(Level.INFO, "Main Thread - Start");

        var configPath = Paths.get("programFiles/config/DBdaemon.config");

        var daemon = new Daemon(configPath);

        daemon.start();

        logger.log(Level.INFO, "Main Thread - Exit");
    }
}
