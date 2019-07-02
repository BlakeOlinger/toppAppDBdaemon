package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Daemon {
    private static final Path path = Paths.get("programFiles/config/DBdaemon.config");
    private static String pushState = "1";

    static void start() {

         new PullDaemon().start();

        do {
            checkPushState();
            if(pushState.compareTo("0") == 0)
                new PushDaemon().start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }

            checkProgramState();
        } while (Config.programState.compareTo("0") == 0);
    }

    private static void checkPushState() {
        try {
            pushState = Files.readString(path).substring(1,2);
        } catch (IOException ignore) {
        }
    }

    private static void checkProgramState() {
        try {
            Config.programState = Files.readString(path).substring(0,1);
        } catch (IOException ignore) {
        }
    }
}
