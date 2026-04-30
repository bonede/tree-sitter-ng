import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.nativeplatform.platform.internal.DefaultOperatingSystem
import org.treesitter.build.Utils

class DownloadAndroidNdkTask extends DefaultTask {

    @Internal
    DefaultOperatingSystem os

    DownloadAndroidNdkTask() {
        group = "download"
        description = "Download Android NDK required for Android native targets"
        os = DefaultNativePlatform.currentOperatingSystem
    }

    @Input
    String getAndroidNdkVersion() {
        return project.rootProject.property("androidNdkVersion")
    }

    @Internal
    Directory getAndroidNdkDir() {
        return project.rootProject.layout.buildDirectory.dir("android-ndk").get()
    }

    @OutputDirectory
    Directory getAndroidNdkHome() {
        return androidNdkDir.dir("android-ndk-${androidNdkVersion}")
    }

    @Internal
    String getOsName() {
        if (os.windows) {
            return "windows"
        } else if (os.macOsX) {
            return "darwin"
        } else if (os.linux) {
            return "linux"
        } else {
            throw new GradleException("Unsupported OS: " + os.name)
        }
    }

    @OutputFile
    RegularFile getAndroidNdkArchive() {
        return androidNdkDir.file("android-ndk-${androidNdkVersion}-${osName}.zip")
    }

    @Internal
    String getAndroidNdkUrl() {
        return "https://dl.google.com/android/repository/${androidNdkArchive.asFile.name}"
    }

    @TaskAction
    void downloadAndroidNdk() {
        def installedNdk = findInstalledAndroidNdkDir()
        if (installedNdk != null) {
            logger.lifecycle("Using installed Android NDK: {}", installedNdk)
            return
        }

        if (androidNdkHome.asFile.exists()) {
            logger.lifecycle("Using downloaded Android NDK: {}", androidNdkHome.asFile)
            return
        }

        logger.lifecycle("Downloading Android NDK from {}", androidNdkUrl)
        Utils.downloadFile(androidNdkUrl, androidNdkArchive.asFile)
        Utils.unzipArchive(androidNdkArchive.asFile, androidNdkDir.asFile)
        androidCompiler.asFile.setExecutable(true, true)
    }

    @Internal
    RegularFile getAndroidCompiler() {
        return androidNdkHome.file("toolchains/llvm/prebuilt/${hostTag}/bin/aarch64-linux-android21-clang${os.windows ? '.cmd' : ''}")
    }

    @Internal
    String getHostTag() {
        if (os.windows) {
            return "windows-x86_64"
        } else if (os.macOsX) {
            return "darwin-x86_64"
        } else if (os.linux) {
            return "linux-x86_64"
        } else {
            throw new GradleException("Unsupported OS: " + os.name)
        }
    }

    File findInstalledAndroidNdkDir() {
        def envNdk = System.getenv("ANDROID_NDK_HOME") ?: System.getenv("ANDROID_NDK_ROOT")
        if (envNdk) {
            return new File(envNdk)
        }

        def sdkDir = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
        if (sdkDir) {
            def ndkRoot = new File(sdkDir, "ndk")
            def ndks = ndkRoot.listFiles()?.findAll { it.isDirectory() }?.sort { a, b -> b.name <=> a.name }
            if (ndks) {
                return ndks.first()
            }
        }

        return null
    }
}
