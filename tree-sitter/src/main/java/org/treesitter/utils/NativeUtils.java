package org.treesitter.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

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
            os = "linux-gnu";
        }else if(osName.contains("mac")){
            ext = "dylib";
            os = "macos";
        }else{
            throw new RuntimeException(String.format("Does not support OS: %s", osName));
        }
        if(archName.contains("amd64") || archName.contains("x86_64")){
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

    static private Path getLibStorePath(){
        String userDefinedPath = System.getProperty("tree-sitter-lib");
        if(userDefinedPath == null){
            return Path.of(System.getProperty("user.home") ,".tree-sitter");
        }
        return Path.of(userDefinedPath);
    }


    private static long crc32(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    private static byte[] readFile(File file){
        try(
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ){
            inputStream.transferTo(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readInputStream(InputStream inputStream){
        try(
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ){
            inputStream.transferTo(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readLib(String libName){
        String fullLibName = getFullLibName(libName);
        InputStream inputStream = NativeUtils.class.getClassLoader().getResourceAsStream(fullLibName);
        if(inputStream == null){
            throw new RuntimeException(String.format("Can't open %s", fullLibName));
        }
        return readInputStream(inputStream);
    }

    /**
     * Load native lib from class path by name convention. <br>
     *
     * This action is process safe.
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
        Path filePath = getLibStorePath().resolve(fullLibName);
        File file = filePath.toFile();
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + parentDir);
        }
        boolean shouldOverwrite = false;
        byte[] newFileBytes = null;
        if(file.exists()){
            byte[] oldFileBytes = readFile(file);
            newFileBytes = readLib(libName);
            if(crc32(oldFileBytes) != crc32(newFileBytes)){
                shouldOverwrite = true;
            }
        }else{
           shouldOverwrite = true;
        }
        if(!shouldOverwrite){
            System.load(file.getAbsolutePath());
            return;
        }
        if(newFileBytes == null){
            newFileBytes = readLib(libName);
        }
        File tempLibFile = null;
        try {
            tempLibFile = File.createTempFile(
                    "lib_" + System.currentTimeMillis() + "_",
                    ".tmp",
                    parentDir
            );
            Files.write(tempLibFile.toPath(), newFileBytes);
            try {
                Files.move(
                    tempLibFile.toPath(),
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                );
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(
                    tempLibFile.toPath(),
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );
            }
        }catch (IOException e){
            throw new RuntimeException("Failed to write library file: " + file, e);
        }finally {
            if (tempLibFile != null && tempLibFile.exists()) {
                tempLibFile.delete();
            }
        }
        System.load(file.getAbsolutePath());
    }
}
