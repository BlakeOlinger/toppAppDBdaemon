package com.practice;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

class Daemon {
    private Path configPath;
    private String programState = "1";
    private String pushState = "1";
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    String getProgramState() {
        return programState;
    }

    String getPushState() {
        return pushState;
    }

    @Contract(pure = true)
    Daemon(Path configPath) {
        this.configPath = configPath;
    }

    void start() {
        logger.log(Level.INFO, "Daemon - Start");

        checkProgramState();

         new PullDaemon(this).start();

        do {
//            checkPushState();
//
//            if(pushState.compareTo("0") == 0)
//                new PushDaemon().start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Error Daemon Thread Interrupted", e);
            }

            checkProgramState();

        } while (programState.compareTo("0") == 0);

        logger.log(Level.INFO, "Daemon - Exit");
    }

     void checkPushState() {
        try {
            pushState = Files.readString(configPath).substring(1, 2);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Reading File " +
                    configPath, e);
        }
    }

     void checkProgramState() {
        try {
            programState = Files.readString(configPath).substring(0,1);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Reading File " +
                    configPath, e);
        }
    }
}
