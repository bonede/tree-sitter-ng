package org.treesitter.tests;

import org.treesitter.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.treesitter.tests.SExpressionUtils.stripFieldNames;
import static org.treesitter.tests.SExpressionUtils.stripSExpressionWhitespace;


public class CorpusTest {
    private List<TestExample> examples;
    private final String filename;

    private static final Pattern HEADER_REGEX = Pattern.compile(
        "^(={3,})([^=\\n]*)\\n([\\s\\S]*?)\\n(={3,})([^=\\n]*)$",
        Pattern.MULTILINE
    );
    private static final Pattern DIVIDER_REGEX = Pattern.compile(
        "^(-{3,})([^-\\n]*)$",
        Pattern.MULTILINE
    );
    private static final Pattern LANG_PTN = Pattern.compile(":language\\(([^)]+)\\)");
    private static final Pattern PLATFORM_PTN = Pattern.compile(":platform\\(([^)]+)\\)");

    public CorpusTest(InputStream inputStream, String filename) throws IOException {
        this.filename = filename;
        parseTest(inputStream);
    }

    public List<TestExample> getExamples() {
        return this.examples;
    }

    public CorpusTest(File file) throws IOException {
        this.filename = file.getName();
        try (InputStream inputStream = new FileInputStream(file)) {
            parseTest(inputStream);
        }
    }

    private void parseTest(InputStream inputStream) throws IOException {
        String content = readAll(inputStream);
        this.examples = new ArrayList<>();

        String firstSuffix = null;
        Matcher firstHeaderMatcher = HEADER_REGEX.matcher(content);
        if (firstHeaderMatcher.find()) {
            firstSuffix = firstHeaderMatcher.group(2);
        }
        if (firstSuffix == null) {
            return;
        }

        List<int[]> headerPositions = new ArrayList<>();
        List<String> testNames = new ArrayList<>();
        List<TestAttributes> testAttributesList = new ArrayList<>();

        Matcher headerMatcher = HEADER_REGEX.matcher(content);
        while (headerMatcher.find()) {
            String suffix1 = headerMatcher.group(2);
            String suffix2 = headerMatcher.group(5);
            if (!firstSuffix.equals(suffix1) || !firstSuffix.equals(suffix2)) {
                continue;
            }
            headerPositions.add(new int[]{headerMatcher.start(), headerMatcher.end()});
            String nameAndAttrs = headerMatcher.group(3).trim();
            String[] lines = nameAndAttrs.split("\\n");
            String name = lines.length > 0 ? lines[0].trim() : "";
            TestAttributes attrs = new TestAttributes();
            for (int i = 1; i < lines.length; i++) {
                String attrLine = lines[i].trim();
                if (attrLine.isEmpty()) continue;
                parseAttributeLine(attrLine, attrs);
            }
            testNames.add(name);
            testAttributesList.add(attrs);
        }

        for (int i = 0; i < headerPositions.size(); i++) {
            int bodyStart = headerPositions.get(i)[1];
            int bodyEnd = (i + 1 < headerPositions.size())
                ? headerPositions.get(i + 1)[0]
                : content.length();

            TestAttributes attrs = testAttributesList.get(i);
            if (attrs.isSkip()) {
                continue;
            }

            String body = content.substring(bodyStart, bodyEnd);

            // Find the longest divider line with matching suffix (same as tree-sitter CLI)
            Matcher dividerMatcher = DIVIDER_REGEX.matcher(body);
            int bestDividerStart = -1;
            int bestDividerEnd = -1;
            int bestLen = 0;
            while (dividerMatcher.find()) {
                String divSuffix = dividerMatcher.group(2);
                if (!firstSuffix.equals(divSuffix)) continue;
                int len = dividerMatcher.end() - dividerMatcher.start();
                if (len > bestLen) {
                    bestLen = len;
                    bestDividerStart = dividerMatcher.start();
                    bestDividerEnd = dividerMatcher.end();
                }
            }

            if (bestDividerStart < 0) {
                continue;
            }

            String input = body.substring(0, bestDividerStart);
            String output = body.substring(bestDividerEnd);

            input = stripTrailingNewline(input);
            input = stripLeadingNewline(input);
            output = output.trim();

            TestExample example = new TestExample();
            example.setName(testNames.get(i));
            example.setAttributes(attrs);
            example.setInput(input);
            example.setOutput(output);
            examples.add(example);
        }
    }

    private void parseAttributeLine(String line, TestAttributes attrs) {
        Matcher langMatcher = LANG_PTN.matcher(line);
        Matcher platformMatcher = PLATFORM_PTN.matcher(line);
        if (line.equals(":skip")) {
            attrs.setSkip(true);
        } else if (line.equals(":error")) {
            attrs.setError(true);
        } else if (line.equals(":fail-fast")) {
            attrs.setError(true);
        } else if (platformMatcher.matches()) {
            attrs.addPlatform(platformMatcher.group(1));
        } else if (langMatcher.matches()) {
            attrs.addLanguage(langMatcher.group(1));
        }
    }

    private static String readAll(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[8192];
        int n;
        while ((n = reader.read(buf)) != -1) {
            sb.append(buf, 0, n);
        }
        return sb.toString();
    }

    private static String stripTrailingNewline(String s) {
        if (s.endsWith("\r\n")) return s.substring(0, s.length() - 2);
        if (s.endsWith("\n")) return s.substring(0, s.length() - 1);
        return s;
    }

    private static String stripLeadingNewline(String s) {
        if (s.startsWith("\r\n")) return s.substring(2);
        if (s.startsWith("\n")) return s.substring(1);
        return s;
    }

    public void runTest(TSLanguage language, String langName) {
        TSParser parser = new TSParser();
        parser.setLanguage(language);
        examples.stream()
            .filter(example -> example.isExampleFor(langName))
            .forEach(example -> {
                parser.reset();
                TSTree tree = parser.parseString(null, example.getInput());
                TSNode node = tree.getRootNode();
                String expect = stripFieldNames(stripSExpressionWhitespace(example.getOutput()));
                String actual = stripFieldNames(stripSExpressionWhitespace(node.toString()));
                if (!expect.equals(actual)) {
                    throw new TreeSitterTestException(example.getName() + " test error: " + "\n" + expect + "\nNot equal to:\n" + actual + "\nWith input:\n" + example.getInput());
                }
            });
    }

    public static void runAllTestsInFolder(String folder, TSLanguage language, String langName) throws IOException {
        runAllTestsInFolderRecursive(new File(folder), language, langName);
    }

    private static void runAllTestsInFolderRecursive(File folderPath, TSLanguage language, String langName) throws IOException {
        if (!folderPath.exists() || !folderPath.isDirectory()) {
            throw new TreeSitterTestException(folderPath.getPath() + " does not exist or not a folder.");
        }
        File[] files = folderPath.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                runAllTestsInFolderRecursive(file, language, langName);
            } else if (file.getName().endsWith(".txt")) {
                CorpusTest corpusTest = new CorpusTest(file);
                corpusTest.runTest(language, langName);
            }
        }
    }

    public static void runAllTestsInDefaultFolder(TSLanguage language, String langName) throws IOException {
        try (FileInputStream input = new FileInputStream("gradle.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String libVersion = (String) properties.get("libVersion");
            String corpusFolder = "build/tree-sitter-" + langName + "/tree-sitter-" + langName + "-" + libVersion + "/test/corpus";
            CorpusTest.runAllTestsInFolder(corpusFolder, language, langName);
        }
    }

    public static void runAllTestsInDefaultFolderSecondaryLang(TSLanguage language, String langName, String secondaryLang) throws IOException {
        try (FileInputStream input = new FileInputStream("gradle.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String libVersion = (String) properties.get("libVersion");
            String corpusFolder = "build/tree-sitter-" + secondaryLang + "/tree-sitter-" + langName + "-" + libVersion + "/test/corpus";
            CorpusTest.runAllTestsInFolder(corpusFolder, language, secondaryLang);
        }
    }
}
