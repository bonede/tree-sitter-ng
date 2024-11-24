
package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class TreeSitterScalaTest {

    private TSParser parser;

    @BeforeEach
    public void setup() {
        TreeSitterScala scala = new TreeSitterScala();
        parser = new TSParser();
        parser.setLanguage(scala);
    }

    @Test
    void examples() throws IOException {
        String ext = ".scala";
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("gradle.properties")) {
            properties.load(input);
            String examplesPath = "src/test/resources/examples";
            Path dir = Paths.get(examplesPath);
            Files.walk(dir)
                    .filter(path -> path.toString().endsWith(ext))
                    .forEach(this::parse);
        }
    }

    private void parse(Path file){
        try {
            String source = new String(Files.readAllBytes(file));
            parser.reset();
            parser.parseString(null, source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
