package com.practice;

// this daemon is designed to interface between git repository db and
// the app install directory to automate git functions and
// later automate app updates

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Process process;
        process = Runtime.getRuntime().exec("");
        process.waitFor();
        System.out.println("End program");
    }
}
