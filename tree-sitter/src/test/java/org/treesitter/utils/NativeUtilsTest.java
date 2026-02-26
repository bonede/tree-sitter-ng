package org.treesitter.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NativeUtilsTest {

    public static class LoadLibRunner {
        public static void main(String[] args) {
            try {
                NativeUtils.loadLib("lib/tree-sitter");
                System.out.println("Success");
                System.exit(0);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
        }
    }

    @Test
    void loadLibInMultipleProcesses() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            javaBin += ".exe";
        }
        String classpath = System.getProperty("java.class.path");
        String className = LoadLibRunner.class.getName();

        int processCount = 5;
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < processCount; i++) {
            ProcessBuilder rb = new ProcessBuilder(javaBin, "-cp", classpath, className);
            rb.inheritIO();
            processes.add(rb.start());
        }

        for (Process process : processes) {
            int exitCode = process.waitFor();
            assertEquals(0, exitCode, "Process failed to load native library");
        }
    }

    @Test
    void loadLib() {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(() -> {
                NativeUtils.loadLib("lib/tree-sitter");
            });
            thread.start();
        }
    }
}