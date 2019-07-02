package com.practice;

import java.io.IOException;

public class PullDaemon implements Runnable{
    private final Thread thread;

    PullDaemon() {
        thread = new Thread(this, "Pull Daemon");
    }

    void start() {
        thread.start();
    }

    @Override
    public void run() {
        var countDown = 1;

        do {
            try {
                // cmd.exe /c cd toppAppDBdaemon/ && git pull origin master
                if(--countDown == 0) {

                    Config.isDatabaseSyncing = true;

                    var process = new ProcessBuilder("cmd.exe", "/c", "cd",
                            "toppAppDBdaemon/", "&&", "git", "pull", "origin", "master").start();

                    process.waitFor();

                    process.destroy();

                    Config.isDatabaseSyncing = false;

                    countDown = 6;
                } else
                    Thread.sleep(2000);

            } catch (InterruptedException | IOException ignore) {

            }

        } while(Config.programState.compareTo("0") == 0);
    }
}
