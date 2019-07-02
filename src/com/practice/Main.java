package com.practice;

// this daemon is designed to interface between git repository db and
// the app install directory to automate git functions and
// later automate app updates

public class Main {

    public static void main(String[] args) {

        Daemon.start();
        /*
        new PushDaemon().start();

         */
    }
}
