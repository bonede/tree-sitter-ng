package org.treesitter.build

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.internal.impldep.org.apache.tools.tar.TarEntry

import java.nio.file.FileSystems
import java.nio.file.FileVisitOption
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

abstract class Utils {
    /**
     * JNI header files directory for project. Default to $rootDir/jni-include/include/jni
     * @param project
     * @return
     */
    static Directory jniIncludeDir(Project project){
        return project.rootProject.layout.projectDirectory.dir("include/jni")
    }

    /**
     * JNI source files directory for project. Default to src/main/c
     * @param project
     * @return
     */
    static Directory jniSrcDir(Project project){
        return project.layout.projectDirectory.dir("src/main/c")
    }

    static RegularFile jniCFile(Project project, String fileName){
        return jniSrcDir(project).file(fileName)
    }

    /**
     * Download directory for project. Default to $buildDir/$libName
     *
     * @param project
     * @param libName
     * @return
     */
    static Directory libDownloadDir(Project project, String libName){
        return project.layout.buildDirectory.dir(libName).get()
    }

    /**
     * Lib zip file for project. Default to $buildDir/$libName-v$version.zip
     * @param project
     * @param libName
     * @param version
     * @return
     */
    static RegularFile libZipFile(Project project, String libName, String version){
        return libDownloadDir(project, libName).file("$libName-v${version}.zip")
    }

    /**
     * Lib source directory for project. Default to $buildDir/$libName-$version/src
     * @param project
     * @param libName
     * @param version
     * @return
     */
    static Directory libSrcDir(Project project, String libName, String version, String dir = "src"){
        return libDownloadDir(project, libName).dir("$libName-$version/$dir")
    }

    static RegularFile libParserCFile(Project project, String libName, String version){
        return libSrcDir(project, libName, version).file("parser.c")
    }

    static RegularFile libScannerCFile(Project project, String libName, String version){
        return  libSrcDir(project, libName, version).file("scanner.c")
    }


    static Directory jniMdInclude(Project project, String target){
        Directory jniInclude = jniIncludeDir(project)
        if(target.contains("windows")){
            return jniInclude.dir("win32")
        }else if(target.contains("linux")){
            return jniInclude.dir("linux")
        }else if(target.contains("macos")){
            return jniInclude.dir("darwin")
        }else{
            throw new GradleException("Does not support $target")
        }
    }

    static String libExt(String target){
        if(target.contains("windows")){
            return "dll"
        }else if(target.contains("linux")){
            return "so"
        }else if(target.contains("macos")){
            return "dylib"
        }else{
            throw new GradleException("Does not support $target")
        }
    }

    static Directory jniOutDir(Project project){
        return project.layout.projectDirectory.dir("src/main/resources/lib")
    }

    static RegularFile jniOutFile(Project project, String target, String name){
        String ext = libExt(target)
        return jniOutDir(project).file("$target-$name.$ext")
    }

    static String[] treeSitterTargets(Project project){
        def props = (String) project.rootProject.properties.get("treeSitterTargets")
        if(props == null){
            throw new GradleException("Can't find `treeSitterTargets` in gradle.properties")
        }
        return props.split(",")
    }

    static void removeWindowsDebugFiles(Project project){
        def files = project.fileTree(jniOutDir(project)) {
            include("**/*.pdb")
            include("**/*.lib")
        }
        project.delete(files)
    }

    static List<String> libFiles(){
        return [
                "parser.c",
                "scanner.c",
                "parser.cc",
                "scanner.cc",
        ]
    }

    static void downloadFile(URL url, File dest, String accept = "application/zip"){
        url.openConnection().with { conn ->
            conn.setRequestProperty("User-Agent", "Mozilla/5.0")
            conn.setRequestProperty("Accept", accept)
            conn.setConnectTimeout(30000)
            conn.setReadTimeout(30000)
            dest.withOutputStream { output ->
                conn.inputStream.withCloseable { input ->
                    output << input
                }
            }
        }
    }

    static String fetchUrl(URL url){
        return url.openConnection().with { conn ->
            conn.setRequestProperty("User-Agent", "Mozilla/5.0")
            conn.setConnectTimeout(30000)
            conn.setReadTimeout(30000)
            conn.inputStream.withCloseable { input ->
                return input.text
            }
        }
    }

    static void downloadFile(String url, File dest, String accept = "application/zip"){
        downloadFile(new URL(url), dest, accept)
    }

    static unzipFile(File zipFile, File outputDir) {
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))
        ZipEntry entry
        while ((entry = zipInputStream.nextEntry) != null) {
            File outputFile = new File(outputDir, entry.name)
            if (entry.isDirectory()) {
                outputFile.mkdirs()
            } else {
                outputFile.withOutputStream {output -> {
                    output << zipInputStream
                }}
            }
            zipInputStream.closeEntry()
        }
        zipInputStream.close()
    }

    static unzipTar(File tarFile, File outputDir){
        String name = tarFile.name.toLowerCase()
        FileInputStream fileInputStream = new FileInputStream(tarFile)
        InputStream compressorInputStream
        if (name.endsWith(".tar.gz") || name.endsWith(".tgz")) {
            compressorInputStream = new GzipCompressorInputStream(fileInputStream)
        } else if (name.endsWith(".tar.xz")) {
            compressorInputStream = new XZCompressorInputStream(fileInputStream)
        } else {
            fileInputStream.close()
            throw new GradleException("Only .tar.gz, .tgz, and .tar.xz files are supported")
        }
        TarArchiveInputStream tarInputStream = new TarArchiveInputStream(compressorInputStream)
        TarArchiveEntry entry
        while ((entry = tarInputStream.nextEntry) != null) {
            File outputFile = new File(outputDir, entry.name)
            if (entry.isDirectory()) {
                outputFile.mkdirs()
            } else {
                outputFile.withOutputStream {output -> {
                    output << tarInputStream
                }}
            }
        }
        tarInputStream.close()
    }

    static unzipArchive(File archive, File outputDir) {
        if (archive.name.endsWith(".zip")) {
            unzipFile(archive, outputDir)
        } else if (archive.name.endsWith(".tar.gz") || archive.name.endsWith(".tgz") || archive.name.endsWith(".tar.xz")) {
            unzipTar(archive, outputDir)
        } else {
            throw new GradleException("Unsupported archive format: ${archive.name}")
        }
    }
}
