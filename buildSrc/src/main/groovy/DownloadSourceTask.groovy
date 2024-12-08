

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.treesitter.build.Utils

class DownloadSourceTask extends DefaultTask{
    @Input
    String url = ""

    @Internal
    Directory getDownloadDir(){
        return project.layout.buildDirectory.dir(project.name).get()
    }

    @Input
    String getLibVersion(){
        return project.property("version")
    }

    @Input
    String getLibName(){
        return project.name
    }

    @OutputFile
    RegularFile getZipFile(){
        return downloadDir.file("$libName-v${libVersion}.zip")
    }

    @OutputDirectory
    Directory getSrcDir(){
        def srcDirName = "$libName-$libVersion"
        return downloadDir.dir(srcDirName)
    }

    DownloadSourceTask(){
        this.description = "Download parser source"
        this.group = "download"
    }

    @Internal
    String getDefaultUrl(){
        return "https://github.com/tree-sitter/${libName}/archive/refs/tags/v${libVersion}.zip"
    }

    @TaskAction
    def downloadSource(){
        if(!downloadDir.asFile.exists()){
            downloadDir.asFile.mkdirs()
        }
        def url = this.url == "" ? defaultUrl : url
        Utils.downloadFile(url, zipFile.asFile)
        Utils.unzipFile(zipFile.asFile, downloadDir.asFile)
    }
}
