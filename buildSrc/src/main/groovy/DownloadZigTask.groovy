import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.nativeplatform.platform.Architecture
import org.gradle.nativeplatform.platform.internal.ArchitectureInternal
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.nativeplatform.platform.internal.DefaultOperatingSystem
import org.treesitter.build.Utils

class DownloadZigTask extends DefaultTask{
    @Internal
    DefaultOperatingSystem os

    @InputFile
    RegularFile miniSignExe

    DownloadZigTask(){
        group = "download"
        description = "Download Zig compiler"
        os = DefaultNativePlatform.currentOperatingSystem
    }

    @Internal
    String getOsName(){
        if (os.windows) {
            return "windows"
        } else if(os.macOsX) {
            return "macos"
        } else if(os.linux) {
            return "linux"
        } else {
            throw new GradleException("Unsupported OS: " + os.name)
        }
    }

    @Internal
    String getArchiveExt(){
        if (os.windows) {
            return ".zip"
        } else if(os.macOsX || os.linux) {
            return ".tar.xz"
        } else {
            throw new GradleException("Unsupported OS: " + os.name)
        }
    }

    @OutputFile
    RegularFile getZigExe(){
        if (os.windows) {
            return zigDir.file("zig-$osName-$archName-${zigVersion}/zig.exe")
        }else if(os.macOsX || os.linux) {
            return zigDir.file("zig-$osName-$archName-${zigVersion}/zig")
        }else{
            throw new GradleException("Unsupported build OS: " + os.name)
        }
    }

    static String getArchName(){
        def arch = DefaultNativePlatform.currentArchitecture
        if (arch.amd64) {
            return  "x86_64"
        } else if(arch.arm64) {
            return "aarch64"
        } else {
            throw new GradleException("Unsupported arch: " + arch.name)
        }
    }

    @Internal
    Directory getZigDir(){
        return project.rootProject.layout.buildDirectory.dir("zig").get()
    }

    @Input
    String getZigVersion(){
        project.rootProject.property("zigVersion")
    }

    @OutputFile
    RegularFile getZigZipFile(){
        zigDir.file("zig-$osName-$archName-${zigVersion}${archiveExt}")
    }

    @OutputFile
    RegularFile getZigSignatureFile(){
        zigDir.file("${zigZipFile.asFile.name}.minisig")
    }

    @Internal
    String getZigZipUrl(){
        "https://ziglang.org/download/$zigVersion/zig-$osName-$archName-${zigVersion}${archiveExt}"
    }

    @Internal
    String getZigSignatureUrl(){
        "${zigZipUrl}.minisig"
    }

    @Input
    String getZigPubKey(){
        project.rootProject.property("zigPubKey")
    }

    @TaskAction
    downloadZig(){
        Utils.downloadFile(zigZipUrl, zigZipFile.asFile)
        Utils.downloadFile(zigSignatureUrl, zigSignatureFile.asFile)
        miniSignExe.asFile.setExecutable(true, true)
        def zipVerified = project.exec {
            ignoreExitValue = true
            workingDir zigDir.asFile
            commandLine miniSignExe,
                           "-qVm",
                           zigZipFile,
                           "-P",
                           zigPubKey
        }
        if(!zipVerified) {
            throw new GradleException("$zigZipFile signature does not match!")
        }
        Utils.unzipArchive(zigZipFile.asFile, zigDir.asFile)
        zigExe.asFile.setExecutable(true, true)
    }
}
