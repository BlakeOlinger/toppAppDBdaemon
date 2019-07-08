package com.practice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaemonTest {
    private static final Path path = Paths.get("test.txt");

    @BeforeAll
    static void testFile() throws IOException {
        var testString = "01";

        Files.writeString(path, testString);
    }

    @AfterAll
    static void cleanUpTestFile() throws IOException {
        if(Files.exists(path))
            Files.delete(path);
    }

    @Test
    void show_1_for_01() {
        var daemon = new Daemon(path);

        daemon.checkPushState();

        assertEquals("1", daemon.getPushState());
    }

    @Test
    void show_0_for_01() {
        var daemon = new Daemon(path);

        daemon.checkProgramState();

        assertEquals("0", daemon.getProgramState());
    }
}