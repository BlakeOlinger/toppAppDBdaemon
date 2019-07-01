package com.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

class PushDaemon implements Runnable{
    private final Thread thread;

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
        System.out.println(" Database Daemon - Push Daemon - Start");

        do {

            try {

                if(isPushState()) {

                    // will need git-add <untracked-files> for new files
                    // before push to remote

                    pushToRemote();
                }


                System.out.println( " Database Daemon - Sleep 2,000 ms");
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }

        } while (Config.programState.compareTo("0") == 0);

        System.out.println(" Database Daemon - Push Daemon - End");
    }

    private void pushToRemote() {
        var random = new Random();
        var randomNumber = random.nextInt();
        var commitMessage = "\"" + randomNumber + "\"";
        System.out.println(" Database Daemon - Pushing to Remote - Commit Message: " + commitMessage);

        try {

            Runtime.getRuntime().exec( " cmd.exe /c git commit -a -m " + commitMessage);

             Runtime.getRuntime().exec(" cmd.exe /c git push origin master").waitFor();

            System.out.println(" Database Daemon - Push Remote - Success");

        } catch (IOException | InterruptedException ignore) {
        }


        resetPushState();
    }

    private void resetPushState() {
        System.out.println(" Database Daemon - Push State - Return State = '1'");
        var configPath = "programFiles/config/DBdaemon.config";

        try (var configFile = new FileOutputStream(configPath)){
            int programState = Config.programState.compareTo("0") == 0 ? (int) '0':
                    (int) '1';

            var pushState = (int) '1';

            configFile.write(programState);
            configFile.write(pushState);

            System.out.println(" Database Daemon - Push State - Reset Push State - Success");
        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Write to Database Config File");
        }
    }

    private boolean isPushState() {
        var configPath = "programFiles/config/DBdaemon.config";

        System.out.println(" Reading Database Config - Push State");

        try (var configFile = new FileInputStream(configPath)) {

            configFile.read();

            var byteRead = (char) configFile.read();

            System.out.println(" Database Config - Push State - " + byteRead);

            FileLog.message = String.valueOf(byteRead);
            new FileLog().log();

            return String.valueOf(byteRead).compareTo("0") == 0;

        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Read Database Config File");

            return false;
        }
    }
}
