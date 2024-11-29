import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.nativeplatform.platform.internal.DefaultOperatingSystem
import org.treesitter.build.Utils

class DownloadMiniSignTask extends DefaultTask {

    @Internal
    DefaultOperatingSystem os

    DownloadMiniSignTask(){
        group = "download"
        description = "Download MiniSign signature tool required by Zig"
        os = DefaultNativePlatform.currentOperatingSystem
    }

    @Input
    String getMiniSignVersion(){
        return project.rootProject.property("miniSignVersion")
    }

    @Internal
    Directory getMiniSignDir(){
        return project.rootProject.layout.buildDirectory.dir("mini-sign").get()
    }

    @Internal
    RegularFile getMiniSignArchive(){
        if (os.windows) {
            return miniSignDir.file("minisign-$miniSignVersion-win64.zip")
        } else if(os.macOsX) {
            return miniSignDir.file("minisign-$miniSignVersion-macos.zip")
        } else if(os.linux) {
            return miniSignDir.file("minisign-$miniSignVersion-linux.tar.gz")
        } else {
            throw new GradleException("Download miniSig error: Unsupported OS: " + System.getProperty("os.name"))
        }
    }

    @OutputFile
    RegularFile getMiniSignExe(){
        if (os.windows) {
            return miniSignDir.file("minisign-win64/minisign.exe")
        } else if(os.macOsX) {
            return miniSignDir.file("minisign")
        } else if(os.linux) {
            return miniSignDir.file("minisign-linux/x86_64/minisign")
        } else {
            throw new GradleException("Download miniSig error: Unsupported OS: " + System.getProperty("os.name"))
        }
    }

    @TaskAction
    void downloadMiniSign(){
        def url = "https://github.com/jedisct1/minisign/releases/download/$miniSignVersion/${miniSignArchive.asFile.name}"
        Utils.downloadFile(url, miniSignArchive.asFile)
        def archive = miniSignArchive.asFile
        if(archive.name.endsWith(".zip")){
            Utils.unzipFile(archive, miniSignDir.asFile)
        }else if(archive.name.endsWith(".tar.gz") || archive.name.endsWith(".tgz")){
            Utils.unzipTar(archive, miniSignDir.asFile)
        }else{
            throw new GradleException("Unsupported archive: " + archive.name)
        }

    }
}
