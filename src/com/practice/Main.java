package com.practice;

// this daemon is designed to interface between git repository
// and the app install directory to automate git functions and
// automate app updates

public class Main {

    public static void main(String[] args) {

        Daemon.start();
    }
}
