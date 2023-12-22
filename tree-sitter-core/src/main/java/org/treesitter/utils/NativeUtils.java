package org.treesitter.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class NativeUtils {
    private static String getFullLibName(String libName){
        String osName = System.getProperty("os.name").toLowerCase();
        String archName = System.getProperty("os.arch").toLowerCase();
        String ext;
        String os;
        String arch;
        if(osName.contains("windows")){
            ext = "dll";
            os = "windows";
        }else if(osName.contains("linux")){
            ext = "so";
            os = "linux";
        }else if(osName.contains("mac")){
            ext = "dylib";
            os = "macos";
        }else{
            throw new RuntimeException(String.format("Does not support OS: %s", osName));
        }
        if(archName.contains("amd64")){
            arch = "x86_64";
        }else if(archName.contains("aarch64")){
            arch = "aarch64";
        }else{
            throw new RuntimeException(String.format("Does not support arch: %s", archName));
        }
        String[] parts = libName.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < parts.length; i++){
            if(i == parts.length - 1){
                stringBuilder.append(String.format("%s-%s-%s.%s", arch, os, parts[i], ext));
            }else{
                stringBuilder.append(parts[i]);
                stringBuilder.append("/");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Load native lib from class path by name convention.
     *
     * <p>Name convention: <code>arch-os-name.ext</code>
     * <p><code>arch</code>
     * <ol>
     *     <li>x64: <code>x86_64</code></li>
     *     <li>arm64: <code>aarch64</code></li>
     * </ol>
     * @param libName Canonical name of the library. e.g. 'lib/foo', 'bar'
     */
    public static void loadLib(String libName){
        String fullLibName = getFullLibName(libName);
        File file;
        try {
            Path tempFilePath = Files.createTempDirectory("lib").resolve(fullLibName);
            file = tempFilePath.toFile();
            if(!file.getParentFile().mkdirs()){
                throw new RuntimeException(String.format("Can't make dir: %s", file.getParentFile()));
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        try(
            InputStream inputStream = NativeUtils.class.getClassLoader().getResourceAsStream(fullLibName);
            FileOutputStream outputStream =  new FileOutputStream(file)
        ){
            if(inputStream == null){
                throw new RuntimeException(String.format("Can't open %s", fullLibName));
            }
            byte[] buf = new byte[8192];
            int length;
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.load(file.getAbsolutePath());
    }
}
