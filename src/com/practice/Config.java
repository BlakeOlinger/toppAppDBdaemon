package com.practice;

import java.io.FileInputStream;
import java.io.IOException;

public class Config implements Runnable{
    private final Thread thread;
    static String programState = "1";
    static String pushState = "1";
    private static String logMessage = "";

    Config() {
        thread = new Thread(this, "Monitor Program State");
    }

    void monitorProgramState() {
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
        var programStatePath = "programFiles/config/DBdaemon.config";
        int readByte;
        var index = 0;

         logMessage += " Database Daemon - Start\n";

        do {
            logMessage += " Reading Database.config...\n";
                try (var DBconfig = new FileInputStream(programStatePath)){
                    do {
                        readByte = DBconfig.read();
                      for(;index < 1; ++index) {
                          programState = String.valueOf((char) readByte);
                      }

                        logMessage += " Database Program State - " + programState + "\n";
                    } while (readByte != -1);
                } catch (IOException ignore) {

                }
                index = 0;
            try {

                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }
        } while (programState.compareTo("0") == 0);

        logMessage += " Database Daemon - End\n";
        /*
        FileLog.message += logMessage;
        new FileLog().log();

         */
    }
}
