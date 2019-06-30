package com.practice;

// this daemon is designed to interface between git repository db and
// the app install directory to automate git functions and
// later automate app updates

public class Main {

    public static void main(String[] args) {

        // -- Only reads first character of DBdaemon.config to monitor
        // program run status - '0' = CONTINUE_RUNNING - '1' = END_MIRCROSERVICE
        new Config().monitorProgramState();

        new PullDaemon().start();

        // -- Listens to the second character of DBdaemon.config to monitor
        // if a push command has been sent - '1' = NO_PUSH - '0' = PUSH
        new PushDaemon().start();

    }
}
