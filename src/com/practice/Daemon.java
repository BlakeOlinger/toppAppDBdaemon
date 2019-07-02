package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Daemon {
    static void start() {

         new PullDaemon().start();

        do {
            // monitor push state

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }

            checkProgramState();
        } while (Config.programState.compareTo("0") == 0);
    }

    private static void checkProgramState() {
        var path = Paths.get("programFiles/config/DBdaemon.config");

        try {
            Config.programState = Files.readString(path).substring(0,1);
        } catch (IOException ignore) {
        }
    }
}
