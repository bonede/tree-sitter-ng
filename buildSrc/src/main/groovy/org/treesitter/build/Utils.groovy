package org.treesitter.build

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
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

    static void downloadFile(URL url, File dest){
        url.openConnection().with { conn ->
            dest.withOutputStream { output ->
                conn.inputStream.with {input ->
                    output << input
                    input.close()
                }
            }
        }
    }

    static void downloadFile(String url, File dest){
        downloadFile(new URL(url), dest)
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

    static unzipTar(File tgzFile, File outputDir){
        FileInputStream fileInputStream = new FileInputStream(tgzFile)
        GzipCompressorInputStream gzipInputStream = new GzipCompressorInputStream(fileInputStream)
        TarArchiveInputStream tarInputStream = new TarArchiveInputStream(gzipInputStream)
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
}
