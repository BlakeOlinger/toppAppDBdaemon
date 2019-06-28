package com.practice;

import java.io.FileInputStream;
import java.io.IOException;

public class Config implements Runnable{
    private final Thread thread;
    static String programState = "0";

    Config() {
        thread = new Thread(this, "Read Config");
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
            FileInputStream configFile;

            do {
                try {
                    var programStatePath = "/programFiles/config/DBDaemon.config";
                    configFile = new FileInputStream(programStatePath);

                    int readByte;
                    int index = 0;
                    do {
                        readByte = configFile.read();


                        if (readByte != -1) {
                            for (; index < 1; ++index) {
                                Config.programState = String.valueOf((char) readByte);
                            }
                        }

                    } while (readByte != -1);

                    configFile.close();
                } catch (IOException ignore) {

                }
            } while (programState.compareTo("0") == 0);
    }
}
