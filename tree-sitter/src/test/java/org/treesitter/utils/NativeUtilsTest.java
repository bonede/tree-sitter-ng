package org.treesitter.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
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

    @Test
    void fullLibNameUsesAndroidTarget() throws Exception {
        String oldOsName = System.getProperty("os.name");
        String oldOsArch = System.getProperty("os.arch");
        String oldVmName = System.getProperty("java.vm.name");
        String oldRuntimeName = System.getProperty("java.runtime.name");
        try {
            System.setProperty("os.name", "Linux");
            System.setProperty("os.arch", "aarch64");
            System.setProperty("java.vm.name", "Dalvik");
            System.setProperty("java.runtime.name", "Android Runtime");

            Method getFullLibName = NativeUtils.class.getDeclaredMethod("getFullLibName", String.class);
            getFullLibName.setAccessible(true);
            String fullName = (String) getFullLibName.invoke(null, "lib/tree-sitter");

            assertEquals("lib/aarch64-linux-android-tree-sitter.so", fullName);
        } finally {
            restoreProperty("os.name", oldOsName);
            restoreProperty("os.arch", oldOsArch);
            restoreProperty("java.vm.name", oldVmName);
            restoreProperty("java.runtime.name", oldRuntimeName);
        }
    }

    private static void restoreProperty(String key, String value) {
        if (value == null) {
            System.clearProperty(key);
        } else {
            System.setProperty(key, value);
        }
    }
}
