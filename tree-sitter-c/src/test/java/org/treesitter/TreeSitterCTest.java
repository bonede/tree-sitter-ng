package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class TreeSitterCTest {
    private TSParser parser;

    @BeforeEach
    public void setup() {
        TreeSitterC lang = new TreeSitterC();
        parser = new TSParser();
        parser.setLanguage(lang);
    }

    @Test
    void examples() throws IOException {
        String ext = ".c";
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