
package org.treesitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeSitterTsxTest {
    private TSParser parser;

    @BeforeEach
    public void setup() {
        TreeSitterTsx lang = new TreeSitterTsx();
        parser = new TSParser();
        parser.setLanguage(lang);
    }

    @Test
    void examples() throws IOException {
        String ext = ".tsx";
        String examplesPath = "src/test/resources/examples";
        Path dir = Paths.get(examplesPath);
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.filter(path -> path.toString().endsWith(ext))
                    .forEach(this::parse);
        }
    }

    private void parse(Path file) {
        try {
            String source = new String(Files.readAllBytes(file));
            parser.reset();
            parser.parseString(null, source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
