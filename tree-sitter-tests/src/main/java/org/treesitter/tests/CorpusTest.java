package org.treesitter.tests;

import org.treesitter.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.treesitter.tests.SExpressionUtils.stripFieldNames;
import static org.treesitter.tests.SExpressionUtils.stripSExpressionWhitespace;


public class CorpusTest {
    private List<TestExample> examples;
    private String suffix = "";
    private static final Pattern HEADER_DELIM_PTN = Pattern.compile("^=+.*$");
    private static final Pattern DIVIDER_DELIM_PTN = Pattern.compile("^-{3,}.*$");
    private static final Pattern LANG_PTN = Pattern.compile(":language\\(([^)]+)\\)");
    private static final Pattern PLATFORM_PTN = Pattern.compile(":platform\\(([^)]+)\\)");
    private BufferedReader reader;
    private String line = null;
    private TestExample example = null;
    private static final int STATE_CREATE = 0;
    private static final int STATE_NAME = 1;
    private static final int STATE_ATTRIBUTE = 2;
    private static final int STATE_INPUT = 3;
    private static final int STATE_OUTPUT = 4;
    private static final int STATE_STOP = 5;
    private int state = STATE_CREATE;
    private StringJoiner stringBuffer = new StringJoiner("\n");
    private int row = 1;
    private final String filename;

    public CorpusTest(InputStream inputStream, String filename) throws IOException {
        this.filename = filename;
        parseTest(inputStream);
    }

    public List<TestExample> getExamples(){
        return this.examples;
    }


    public CorpusTest(File file) throws IOException {
        this.filename = file.getName();
        try (InputStream inputStream = new FileInputStream(file)){
            parseTest(inputStream);
        }
    }

    private void stateCreate(){
        if(isHeaderDelim()){
            this.example = new TestExample();
            this.state = STATE_NAME;
        }else {
            if (!isEmptyLine()) {
                expect("spaces or header delimiter");
            }
        }
    }

    private void appendBuffer(){
        stringBuffer.add(this.line);
    }

    private void stateName(){
        if(isEmptyLine()){
            return;
        }
        if(isDeviderDelim() || isHeaderDelim()){
            expect("test name");
            return;
        }
        this.example.setName(this.line);
        this.state = STATE_ATTRIBUTE;
    }

    private void stateAttribute(){
        if(isHeaderDelim()){
            this.state = STATE_INPUT;
            return;
        }
        parseAttributes();
    }

    private String flushBuffer(){
        String str = stringBuffer.toString();
        stringBuffer = new StringJoiner("\n");
        return str;
    }

    private void stateInput(){
        if(isHeaderDelim()){
            expect("divider delimiter");
            return;
        }
        if(isDeviderDelim()){
            this.example.setInput(flushBuffer());
            this.state = STATE_OUTPUT;
            return;
        }
        appendBuffer();
    }

    private void stateOutput(){
        if(isEOF()){
            this.example.setOutput(flushBuffer());
            this.examples.add(this.example);
            this.state = STATE_STOP;
            return;
        }
        if(isDeviderDelim()){
            expect("header delimiter");
            return;
        }
        if(isHeaderDelim()){
            this.example.setOutput(flushBuffer());
            this.examples.add(this.example);
            this.example = new TestExample();
            this.state = STATE_NAME;
            return;
        }
        appendBuffer();
    }

    private void parseTest(InputStream inputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        this.examples = new ArrayList<>();
        while(this.state != STATE_STOP){
            readLine();
            switch (state){
                case STATE_CREATE: stateCreate(); break;
                case STATE_NAME: stateName(); break;
                case STATE_ATTRIBUTE: stateAttribute(); break;
                case STATE_INPUT: stateInput(); break;
                case STATE_OUTPUT: stateOutput(); break;
                default:
                    throw new CorpusParseException("Unexpected line: " + this.line);
            }
            row++;
        }
    }

    private void parseAttributes(){
        if(this.example.getAttributes() == null){
            this.example.setAttributes(new TestAttributes());
        }
        TestAttributes attributes = this.example.getAttributes();
        String matches;
        if(line.equals(":skip")){
            attributes.setSkip(true);
        }else if(line.equals(":error")){
            attributes.setError(true);
        }else if(line.equals(":fail-fast")){
            attributes.setError(true);
        }else if((matches = isPlatformAttribute()) != null){
            attributes.addPlatform(matches);
        }else if((matches = isLangAttribute()) != null){
            attributes.addLanguage(matches);
        }else{
            expect("attribute of :skip, :error, :fail-fast, :language(LANG), :platform(PLATFORM)");
        }
    }

    private String isLangAttribute(){
        Matcher matcher = LANG_PTN.matcher(this.line);
        if(matcher.matches()){
            return matcher.group(1);
        }else{
            return null;
        }
    }

    private String isPlatformAttribute(){
        Matcher matcher = PLATFORM_PTN.matcher(this.line);
        if(matcher.matches()){
            return matcher.group(1);
        }else{
            return null;
        }
    }

    private void expect(String name){
        throw new CorpusParseException("Excepting " + name + " got: " + this.line + " (" + this.filename + ":" + this.row+ ")");
    }

    private void readLine() throws IOException {
        this.line = reader.readLine();
    }

    private boolean isEmptyLine(){
        return this.line.isEmpty();
    }

    private boolean isHeaderDelim(){
        if(this.suffix.isEmpty()){
            if(!HEADER_DELIM_PTN.matcher(this.line).matches()){
                return false;
            }
            this.suffix = headerSuffix(this.line);
            return true;
        }else{
            boolean matches = HEADER_DELIM_PTN.matcher(this.line).matches();
            String suffix = headerSuffix(this.line);
            return matches && this.suffix.equals(suffix);
        }
    }

    private boolean isDeviderDelim(){
        if(this.suffix.isEmpty()){
            return DIVIDER_DELIM_PTN.matcher(this.line).matches();
        }else{
            boolean matches = DIVIDER_DELIM_PTN.matcher(this.line).matches();
            String suffix = dividerSuffix(this.line);
            return matches && this.suffix.equals(suffix);
        }

    }

    private boolean isEOF(){
        return this.line == null;
    }

    private String headerSuffix(String headerDelim){
        return headerDelim.replace("=", "");
    }

    private String dividerSuffix(String dividerDelim){
        return dividerDelim.replace("-", "");
    }

    public void runTest(TSLanguage language){
        TSParser parser = new TSParser();
        parser.setLanguage(language);
        examples.forEach(example -> {
            parser.reset();
            TSTree tree = parser.parseString(null, example.getInput());
            TSNode node = tree.getRootNode();
            String expect = stripFieldNames(stripSExpressionWhitespace(example.getOutput()));
            String actual = stripFieldNames(node.toString());
            if(!expect.equals(actual)){
                throw new TreeSitterTestException(example.getName() + " test error: " + "\n" + expect + "\nNot equal to:\n" + actual + "\nWith input:\n" + example.getInput());
            }

        });
    }

    public static void runAllTestsInFolder(String folder, TSLanguage language) throws IOException {
        File folderPath = new File(folder);
        if(!folderPath.exists() || !folderPath.isDirectory()){
            throw new TreeSitterTestException(folder + " does not exist or not a folder.");
        }
        File[] files = folderPath.listFiles();
        if(files == null){
            return;
        }
        for(File file : files){
            CorpusTest corpusTest = new CorpusTest(file);
            corpusTest.runTest(language);
        }
    }

    public static void runAllTestsInDefaultFolder(TSLanguage language, String projectName) throws IOException {
        try (FileInputStream input = new FileInputStream("gradle.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String libVersion = (String) properties.get("libVersion");
            String corpusFolder = "build/tree-sitter-scala/tree-sitter-" + projectName + "-" + libVersion + "/test/corpus";
            CorpusTest.runAllTestsInFolder(corpusFolder, language);
        }
    }
}
