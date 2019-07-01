package com.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

class PushDaemon implements Runnable{
    private final Thread thread;
    private static String logMessage = "";

    PushDaemon() {
        thread = new Thread(this, " Push Daemon");
    }

    void start() {
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
        logMessage = " Database Daemon - Push Daemon - Start\n";
        System.out.println(logMessage);
        do {

            try {

                if(isPushState()) {

                    // will need git-add <untracked-files> for new files
                    // before push to remote

                    pushToRemote();
                }

                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }

        } while (Config.programState.compareTo("0") == 0);

        logMessage = " Database Daemon - Push Daemon - End\n";
        System.out.println(logMessage);

        /*
        FileLog.message += logMessage;
        new FileLog().log();

         */
    }

    private void pushToRemote() {
        var random = new Random();
        var randomNumber = random.nextInt();
        var commitMessage = "\"" + randomNumber + "\"";
        logMessage = " Database Daemon - Pushing to Remote - Commit Message: " +
                commitMessage + "\n";
        System.out.println(logMessage);

        try {
            Runtime.getRuntime().exec( " cmd.exe /c cd toppAppDBdaemon && git commit -a -m " + commitMessage).waitFor();
            Runtime.getRuntime().exec(" cmd.exe /c cd toppAppDBdaemon && git push origin master");
            logMessage = " Push Daemon - End cmd.exe";
            System.out.println(logMessage);
        } catch (IOException | InterruptedException ignore) {
        }


        logMessage = " Database Daemon - Push Remote - Success\n";
        System.out.println(logMessage);


        resetPushState();
    }

    private void resetPushState() {
        logMessage = " Database Daemon - Push State - Return State = '1'\n";
        System.out.println(logMessage);
        var configPath = "programFiles/config/DBdaemon.config";

        try (var configFile = new FileOutputStream(configPath)){
            int programState = Config.programState.compareTo("0") == 0 ? (int) '0':
                    (int) '1';

            var pushState = (int) '1';

            configFile.write(programState);
            configFile.write(pushState);

            logMessage = " Database Daemon - Push State - Reset Push State - Success\n";
            System.out.println(logMessage);
        } catch (IOException ignore) {
            logMessage = " ERROR: Could Not Write to Database Config File\n";
            System.out.println(logMessage);
        }
    }

    private boolean isPushState() {
        var configPath = "programFiles/config/DBdaemon.config";

        logMessage = " Reading Database Config - Push State\n";
        System.out.println(logMessage);

        try (var configFile = new FileInputStream(configPath)) {

            configFile.read();

            var byteRead = (char) configFile.read();

            logMessage = " Database Config - Push State - " + byteRead + "\n";
            System.out.println(logMessage);

            return String.valueOf(byteRead).compareTo("0") == 0;

        } catch (IOException ignore) {
            logMessage = " ERROR: Could Not Read Database Config File\n";
            System.out.println(logMessage);

            return false;
        }
    }
}

