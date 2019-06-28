package com.practice;

import com.practice.config.Config;

import java.io.FileInputStream;
import java.io.IOException;

public class Clone implements Runnable {
    private final Thread thread;

    Clone() {
        thread = new Thread(this, "Initialize DB");
    }

    void checkAndInitializeDB() {
        thread.start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            var gitConfig = new FileInputStream(Config.installDirectory +
                    "/.git");
            if(gitConfig.available() == 0)
                throw new IOException();
        } catch (IOException ignore) {
            Config.isDBUninitialized = true;
        }

        try {
            if (Config.isDBUninitialized) {
                var process = Runtime.getRuntime().exec(
                        "cmd.exe /c git clone https://github.com/BlakeOlinger/toppAppDBdaemon.git");

                process.waitFor();

                Config.isDBUninitialized = false;
            }
        } catch (InterruptedException | IOException ignore) {

        }
    }
}
