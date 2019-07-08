package com.practice;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PullDaemon implements Runnable{
    private final Thread thread;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Daemon daemon;

    PullDaemon(Daemon daemon) {
        this.daemon = daemon;
        thread = new Thread(this, "Pull Daemon");
    }

    void start() {
        thread.start();
    }

    @Override
    public void run() {
        var countDown = 1;

        logger.log(Level.INFO, "Pull Daemon - Start");

        do {
            try {
                // cmd.exe /c cd toppAppDBdaemon/ && git pull origin master
                if(--countDown == 0) {
                    logger.log(Level.INFO, "Pull Daemon - Sync Start");

                    Config.isDatabaseSyncing = true;

                    var process = new ProcessBuilder("cmd.exe", "/c", "cd",
                            "toppAppDBdaemon/", "&&", "git", "pull", "origin", "master").start();

                    process.waitFor();

                    process.destroy();

                    Config.isDatabaseSyncing = false;

                    countDown = 6;

                    logger.log(Level.INFO, "Pull Daemon - Sync Exit");

                } else
                    Thread.sleep(2000);

            } catch (InterruptedException | IOException e) {
                logger.log(Level.SEVERE, "Error Pull Daemon", e);
            }

        } while(daemon.getProgramState().compareTo("0") == 0);

        logger.log(Level.INFO, "Pull Daemon - Exit");
    }
}
