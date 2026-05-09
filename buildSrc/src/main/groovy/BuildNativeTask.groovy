

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import org.treesitter.build.Utils
import java.util.Locale

class BuildNativeTask extends DefaultTask{

    static String libExt(String target){
        if(target.contains("windows")){
            return "dll"
        }else if(target.contains("linux") || target.contains("android")){
            return "so"
        }else if(target.contains("macos")){
            return "dylib"
        }else{
            throw new GradleException("Does not support $target")
        }
    }

    @InputFiles
    FileCollection additionalCFiles = project.files()

    @InputFiles
    List<Directory> additionalIncludeDirs = []

    @InputFile
    RegularFile zigExe

    @Input
    List<String> getTargets(){
        def props = (String) project.rootProject.properties.get("treeSitterTargets")
        if(props == null){
            throw new GradleException("Can't find `treeSitterTargets` in gradle.properties")
        }
        props.split(",").collect() { target ->
            target.trim()
        }
    }

    @Input
    String getLibVersion(){
        return project.property("libVersion")
    }

    @InputDirectory
    Directory getSrcDir(){
        def srcDirName = "$libName-$libVersion"
        return downloadDir.dir(srcDirName)
    }

    @Internal
    Directory getDownloadDir(){
        return project.layout.buildDirectory.dir(project.name).get()
    }

    @Input
    String getLibName(){
        return project.name
    }

    @Internal
    Directory getJniOutDir(){
        return project.layout.projectDirectory.dir("src/main/resources/lib")
    }

    @OutputFiles
    FileCollection getJniLibFiles(){
        def props = (String) project.rootProject.properties.get("treeSitterTargets")
        if(props == null){
            throw new GradleException("Can't find `treeSitterTargets` in gradle.properties")
        }
        def files = targets.collect() { target ->
            jniOutDir.file("$target-$libName.${libExt(target)}")
        }
        return project.files(files)
    }


    @Internal
    Directory getJniCDir(){
        project.layout.projectDirectory.dir("src/main/c")
    }

    @InputFiles
    FileCollection getJniSourceFiles(){
        return jniCDir.asFileTree.matching {
            include("*.c")
            include("*.h")
        }
    }

    @Internal
    FileCollection getJniCFiles(){
        return jniCDir.asFileTree.matching {
            include("*.c")
        }
    }

    @InputFiles
    FileCollection getParserSourceFiles(){
        srcDir.dir("src").asFileTree.matching {
            include("**/*.c")
            include("**/*.h")
            include("**/*.cpp")
        }
    }

    @Internal
    FileCollection getParserCFiles(){
        srcDir.dir("src").asFileTree.matching {
            include("**/*.c")
            include("**/*.cpp")
        }
    }

    @Internal
    Directory getJniIncludeDir(){
        return project.rootProject.layout.projectDirectory.dir("include/jni")
    }


    Directory getJniMdInclude(String target){
        Directory jniInclude = jniIncludeDir
        if(target.contains("windows")){
            return jniInclude.dir("win32")
        }else if(target.contains("linux") || target.contains("android")){
            return jniInclude.dir("linux")
        }else if(target.contains("macos")){
            return jniInclude.dir("darwin")
        }else{
            throw new GradleException("Does not support $target")
        }
    }

    RegularFile jniOutFile(String target, String name){
        String ext = libExt(target)
        return jniOutDir.file("$target-$name.$ext")
    }

    BuildNativeTask(){
        description = "Build parser native modules"
        group = "build"
    }

    @TaskAction
    def buildNative() {
        jniOutDir.asFile.mkdirs()
        targets.each {target ->
            def jniMdIncludeDir = getJniMdInclude(target)
            def jniOutFile = Utils.jniOutFile(project, target, libName)

            def cmd = []
            cmd.addAll(this.compilerArgsForTarget(target))
            cmd.addAll([
                    "-g0",
                    "-fno-sanitize=undefined",
                    "-fPIC",
                    "-shared",
                    "-I", srcDir,
                    "-I", srcDir.dir("lib/include"),
                    "-I", jniIncludeDir,
                    "-I", jniMdIncludeDir,
                    "-o", jniOutFile
            ])
            additionalIncludeDirs.forEach { f ->
                cmd.add("-I")
                cmd.add(f)
            }
            cmd.addAll(jniCFiles)
            cmd.addAll(parserCFiles)
            cmd.addAll(additionalCFiles)
            project.providers.exec {
                workingDir jniCDir
                commandLine(cmd)
            }.result.get()
        }
        this.removeWindowsDebugFiles()
    }

    List<Object> compilerArgsForTarget(String target) {
        if (!target.contains("android")) {
            return [zigExe, "c++", "-target", target]
        }

        def ndkDir = findAndroidNdkDir()
        def osName = System.getProperty("os.name").toLowerCase(Locale.ROOT)
        def hostTag = osName.contains("win")
                ? "windows-x86_64"
                : osName.contains("mac")
                    ? "darwin-x86_64"
                    : "linux-x86_64"
        def apiLevel = project.findProperty("androidApiLevel") ?: "21"
        def compilerName = "aarch64-linux-android${apiLevel}-clang${osName.contains("win") ? ".cmd" : ""}"
        def compiler = new File(
                ndkDir,
                "toolchains/llvm/prebuilt/${hostTag}/bin/${compilerName}"
        )
        if (!compiler.exists()) {
            throw new GradleException("Android compiler not found: ${compiler}. Set ANDROID_NDK_HOME or ANDROID_NDK_ROOT.")
        }
        return [compiler]
    }

    File findAndroidNdkDir() {
        def envNdk = System.getenv("ANDROID_NDK_HOME") ?: System.getenv("ANDROID_NDK_ROOT")
        if (envNdk) {
            return new File(envNdk)
        }

        def sdkDir = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
        if (!sdkDir) {
            def defaultSdk = new File(System.getProperty("user.home"), "Library/Android/sdk")
            if (defaultSdk.exists()) {
                sdkDir = defaultSdk.absolutePath
            }
        }
        if (sdkDir) {
            def ndkRoot = new File(sdkDir, "ndk")
            def ndks = ndkRoot.listFiles()?.findAll { it.isDirectory() }?.sort { a, b -> b.name <=> a.name }
            if (ndks) {
                return ndks.first()
            }
        }

        def downloadAndroidNdkTask = project.rootProject.tasks.named("downloadAndroidNdk").get()
        def downloadedNdk = downloadAndroidNdkTask.androidNdkHome.asFile
        if (downloadedNdk.exists()) {
            return downloadedNdk
        }

        throw new GradleException("Android NDK not found. Set ANDROID_NDK_HOME, ANDROID_NDK_ROOT, ANDROID_HOME, or ANDROID_SDK_ROOT, or run the downloadAndroidNdk task.")
    }

    void removeWindowsDebugFiles(){
        def files = jniOutDir.asFileTree.matching {
            include("**/*.pdb")
            include("**/*.lib")
        }
        project.delete(files)
    }
}
