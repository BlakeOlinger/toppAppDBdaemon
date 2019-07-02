package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

class PushDaemon implements Runnable{
    private final Thread thread;

    PushDaemon() {
        thread = new Thread(this, " Push Daemon");
    }

    void start() {
        thread.start();
    }

    @Override
    public void run() {
        pushToRemote();
    }

    private void pushToRemote() {
        var random = new Random();
        var randomNumber = random.nextInt();
        var commitMessage = "\"" + randomNumber + "\"";

        try {
            var process = new ProcessBuilder("cmd.exe", "/c", "cd", "toppAppDBdaemon/",
                    "&&", "git", "commit", "-a", "-m", commitMessage).start();
            process.waitFor();
            process.destroy();

            process = new ProcessBuilder("cmd.exe", "/c", "cd", "toppAppDBdaemon/",
                    "&&", "git", "push", "origin", "master").start();
            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException ignore) {
        }

        resetPushState();
    }

    private void resetPushState() {
        var path = Paths.get("programFiles/config/DBdaemon.config");

        if (Config.programState.compareTo("0") == 0) {
            try {
                Files.writeString(path, "01");
            } catch (IOException ignore) {
            }
        } else {
            try {
                Files.writeString(path, "11");
            } catch (IOException ignore) {
            }
        }
    }
}

