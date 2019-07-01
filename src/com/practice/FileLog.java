package com.practice;

import java.io.FileOutputStream;
import java.io.IOException;

class FileLog implements Runnable{
    private final Thread thread;
    static String message = "";

    FileLog() {
        thread = new Thread(this, " File Log");
    }

    void log() {
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
        var path = "Database Daemon.log";

        try (var logFile = new FileOutputStream(path)) {
            char[] chars = message.toCharArray();

            for (char aChar : chars) {
                logFile.write((int) aChar);
            }

        } catch (IOException ignore) {
            System.out.println(" ERROR: Cannot write to file");
        }
    }
}
