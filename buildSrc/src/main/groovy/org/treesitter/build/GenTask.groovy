package org.treesitter.build

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

class GenTask extends DefaultTask {

    static String subProjectName(String libShortName){
        return "tree-sitter-$libShortName"
    }

    static String capitalizedLibName(String libShortName){
        return libShortName.split("-").collect {it.capitalize()}.join("")
    }

    static String libIdentifierName(String libShortName){
        return libShortName.replace("-", "_")
    }


    static void genJavaFile(File projectDir, String libShortName){
        def capitalized = capitalizedLibName(libShortName)
        def className = "TreeSitter$capitalized"
        def idName = libIdentifierName(libShortName)
        def classFile = new File(projectDir, "src/main/java/org/treesitter/${className}.java")
        def content = """
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class $className implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-$libShortName");
    }
    private native static long tree_sitter_$idName();

    private final long ptr;

    public $className() {
        ptr = tree_sitter_$idName();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
"""
        classFile.getParentFile().mkdirs()
        try (OutputStream outputStream = new FileOutputStream(classFile)){
            outputStream.withPrintWriter {writer -> writer.write(content)}
        }
    }

    static void genJavaTestFile(File projectDir, String libShortName){
        def capitalized = capitalizedLibName(libShortName)
        def classFile = new File(projectDir, "src/test/java/org/treesitter/TreeSitter${capitalized}Test.java")
        def content = """
package org.treesitter;

import org.junit.jupiter.api.Test;

class TreeSitter${capitalized}Test {
    @Test
    void init() {
        new TreeSitter$capitalized();
    }
}
"""
        classFile.getParentFile().mkdirs()
        try (OutputStream outputStream = new FileOutputStream(classFile)){
            outputStream.withPrintWriter {writer -> writer.write(content)}
        }
    }

    static void genProperties(File projectDir, String version){
        def content = """libVersion=${version}"""
        try(OutputStream outputStream = new FileOutputStream(new File(projectDir, "gradle.properties"))){
            outputStream.withPrintWriter {it.write(content)}
        }
    }


    static void genBuildGradle(File projectDir, String libShortName, String url){
        def capitalized = capitalizedLibName(libShortName)
        def gradleFile = new File(projectDir, "build.gradle")
        def content = """

import org.treesitter.build.Utils

plugins {
    id 'java'
}

group = 'org.treesitter'
version = libVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    implementation project(":tree-sitter-core")
}

test {
    useJUnitPlatform()
}
def libName = "tree-sitter-$libShortName"
tasks.register('downloadSource') {
    group = "build setup"
    description = "Download parser source"
    def zipUrl = "$url"
    def downloadDir = Utils.libDownloadDir(project, libName)
    def zip = Utils.libZipFile(project, libName, libVersion)
    def parserCFile = Utils.libParserCFile(project, libName, libVersion)
    inputs.files(layout.projectDirectory.file("gradle.properties"))
    outputs.files(parserCFile)
    doLast {
        download.run {
            src zipUrl
            dest zip
            overwrite false
        }
        copy {
            from zipTree(zip)
            into downloadDir
        }
    }

}

tasks.register("buildNative") {
    group = "build"
    description = "Build parser native modules"
    dependsOn "downloadSource", rootProject.bootstrap
    def jniSrcDir = Utils.jniSrcDir(project)
    def outDir = Utils.jniOutDir(project)
    def jniCFile = Utils.jniCFile(project, "org_treesitter_TreeSitter${capitalized}.c")
    def parserCFile = Utils.libParserCFile(project, libName, libVersion)
    def scannerCFile = Utils.libScannerCFile(project, libName, libVersion)
    def libSrcDir = Utils.libSrcDir(project, libName, libVersion)
    def jniInclude = Utils.jniIncludeDir(project)

    def targets = Utils.treeSitterTargets(project)
    def outputFiles = targets.collect()
            { t -> Utils.jniOutFile(project, t, libName)}
    def srcFiles = project.fileTree(libSrcDir) {
        include(Utils.libFiles())
    }.toList()
    outputs.files(outputFiles)
    def inputFiles = srcFiles + [parserCFile, rootProject.layout.projectDirectory.file("gradle.properties")]
    inputs.files(inputFiles)
    doLast{
        mkdir(outDir)
        targets.each {target ->
            def jniMdInclude = Utils.jniMdInclude(project, target)
            def jniOutFile = Utils.jniOutFile(project, target, libName)
            def files = project.fileTree(libSrcDir) {
                include(Utils.libFiles())
            }.toList()
            def cmd = [
                        rootProject.downloadZig.zigExe, "c++",
                        "-g0",
                        "-shared",
                        "-target", target,
                        "-I", libSrcDir,
                        "-I", jniInclude,
                        "-I", jniMdInclude,
                        "-o", jniOutFile,
                        jniCFile,
            ]

            cmd.addAll(files)
            exec{
                workingDir jniSrcDir
                commandLine(cmd)
            }
        }
        Utils.removeWindowsDebugFiles(project)
    }
}
"""
        try (OutputStream outputStream = new FileOutputStream(gradleFile)){
            outputStream.withPrintWriter {writer -> writer.write(content)}
        }
    }
    static String jniMethodName(String idName){
        return idName.replace("_", "_1")
    }
    static void genJniCFile(File projectDir, String libShortName){
        def capitalized = capitalizedLibName(libShortName)
        def cFile = new File(projectDir, "src/main/c/org_treesitter_TreeSitter${capitalized}.c")
        def idName = libIdentifierName(libShortName)
        def jniMethodName = jniMethodName(idName)
        def content = """
#include <jni.h>
void *tree_sitter_$idName();
/*
 * Class:     org_treesitter_TreeSitter$capitalized
 * Method:    tree_sitter_$idName
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitter${capitalized}_tree_1sitter_1$jniMethodName
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_$idName();
}
"""
        cFile.getParentFile().mkdirs()
        try (OutputStream outputStream = new FileOutputStream(cFile)){
            outputStream.withPrintWriter {writer -> writer.write(content)}
        }
    }

    static void updateSettingsGradle(Project project, String libShortName){
        def projectLine = "include 'tree-sitter-$libShortName'"
        def settingsFile = project.rootProject.file("settings.gradle")
        def shouldUpdate = true

        try(InputStream inputStream = new FileInputStream(settingsFile)){
            inputStream.withReader {reader ->
                reader.eachLine {line -> {
                    if(line == projectLine){
                        shouldUpdate = false
                    }
                }}
            }
        }
        if(shouldUpdate){
            try(OutputStream outputStream = new FileOutputStream(settingsFile, true)){
                outputStream.withPrintWriter {writer -> writer.println(projectLine)}
            }
        }
    }

    static void genAll(Project project, String libShortName, String version, String url){
        def subProjectName = subProjectName(libShortName)
        def projectDir = project.rootProject.layout.projectDirectory.dir(subProjectName).asFile
        if(projectDir.exists()){
            throw new GradleException("Can't generate sub project. $projectDir existed!")
        }
        project.rootProject.mkdir(projectDir)

        genProperties(projectDir, version)
        genJavaFile(projectDir, libShortName)
        genJniCFile(projectDir, libShortName)
        genBuildGradle(projectDir, libShortName, url)
        genJavaTestFile(projectDir, libShortName)
        updateSettingsGradle(project, libShortName)
    }

    private String url
    private String libShortName
    private String version

    @Option(option = "parser-zip", description = "Parser zip url")
     void setUrl(String url) {
        this.url = url
    }

    @Option(option = "parser-name", description = "Parser name. e.g., json")
    void setShortName(String libShortName) {
        this.libShortName = libShortName
    }


    @Option(option = "parser-version", description = "Parser version. e.g., 1.0.0, master")
    void setVersion(String version) {
        this.version = version
    }

    @TaskAction
    void gen() {
        if(url == null || libShortName == null || version == null){
            throw new GradleException("Require options missing! \nExample:\n  ./gradlew gen --parser-name=bash --parser-version=0.1.1 --parser-zip=https://exmaple.org/bash.zip")
        }
        genAll(project, libShortName, version, url)
    }

}
