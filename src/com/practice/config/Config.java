package com.practice.config;

import com.practice.pull.PullDaemon;

import java.io.FileInputStream;
import java.io.IOException;

public class Config implements Runnable{
    private final Thread thread;

    public Config() {
        thread = new Thread(this, "Read Config");
    }

    public void checkProgramState() {
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

        System.out.println("Update Program State Start...");


        FileInputStream configFile;
            try {
                  configFile = new FileInputStream("DBDaemon.config");

                    int readByte;
                    int index = 0;
                do {
                    readByte = configFile.read();

                    System.out.println("input value: " + (char) readByte);

                    if(readByte != -1) {
                        for(;index < 1; ++index) {
                            PullDaemon.programStop = String.valueOf((char) readByte);
                        }
                    }

                } while (readByte != -1);

                configFile.close();
            } catch (IOException ignore) {

            }
        System.out.println("Update Program State End");
    }
}
