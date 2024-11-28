package org.treesitter.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.treesitter.TSLanguage;
import org.treesitter.TreeSitterScala;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorpusTestTest {

    private static List<TestExample> examples;

    @BeforeAll
    public static void setup(){
        examples = List.of(
            new TestExample(
            "Classes",
            "\n@deprecated(\"Use D\", \"1.0\") class C {}\n" ,
            "\n(compilation_unit\n" +
                    "  (class_definition\n" +
                    "    (annotation (type_identifier) (arguments (string) (string)))\n" +
                    "    (identifier)\n" +
                    "    (template_body)))\n"
            ),
            new TestExample(
            "Declarations and definitions",
            "\nclass A(x: String) {\n" +
                    "  @transient @volatile var y: Int\n" +
                    "  @transient @volatile val z = x\n" +
                    "\n" +
                    "  @throws(Error)\n" +
                    "  @deprecated(message = \"Don't use this\", since = \"1.0\")\n" +
                    "  def foo() {}\n" +
                    "}\n" ,
            "\n(compilation_unit\n" +
                    "  (class_definition (identifier)\n" +
                    "    (class_parameters (class_parameter (identifier) (type_identifier)))\n" +
                    "    (template_body\n" +
                    "      (var_declaration\n" +
                    "        (annotation (type_identifier))\n" +
                    "        (annotation (type_identifier))\n" +
                    "        (identifier) (type_identifier))\n" +
                    "      (val_definition\n" +
                    "        (annotation (type_identifier))\n" +
                    "        (annotation (type_identifier))\n" +
                    "        (identifier) (identifier))\n" +
                    "      (function_definition\n" +
                    "        (annotation (type_identifier) (arguments (identifier)))\n" +
                    "        (annotation (type_identifier)\n" +
                    "          (arguments\n" +
                    "            (assignment_expression (identifier) (string))\n" +
                    "            (assignment_expression (identifier) (string))))\n" +
                    "        (identifier) (parameters) (block)))))\n"
            ),
            new TestExample(
                    "Parameters",
                    "\nclass A(@one x: String) {\n" +
                            "  def foo(@another x: Int) {}\n" +
                            "}\n" ,
                    "\n(compilation_unit\n" +
                            "  (class_definition (identifier)\n" +
                            "    (class_parameters\n" +
                            "      (class_parameter\n" +
                            "        (annotation (type_identifier)) (identifier) (type_identifier)))\n" +
                            "    (template_body\n" +
                            "      (function_definition\n" +
                            "        (identifier)\n" +
                            "        (parameters (parameter\n" +
                            "                      (annotation (type_identifier))\n" +
                            "                      (identifier) (type_identifier)))\n" +
                            "        (block)))))\n"
            ),
            new TestExample(
                    "Types",
                    "\ntrait Function0[@specialized(Unit, Int, Double) T] {\n" +
                            "  def apply: T\n" +
                            "}\n" ,
                    "\n(compilation_unit\n" +
                            "  (trait_definition (identifier)\n" +
                            "    (type_parameters\n" +
                            "      (annotation (type_identifier) (arguments (identifier) (identifier) (identifier))) (identifier))\n" +
                            "    (template_body (function_declaration (identifier) (type_identifier)))))"
            )
        );
    }

    private CorpusTest loadTest(String filename) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return new CorpusTest(inputStream, filename);
        }
    }

    private void testParseFile(String filename) throws IOException {
        CorpusTest corpusTest = loadTest(filename);
        assertEquals(4, corpusTest.getExamples().size());
        for(int i = 0; i < corpusTest.getExamples().size(); i++){
            assertEquals(examples.get(i).getName(), corpusTest.getExamples().get(i).getName());
            assertEquals(examples.get(i).getInput(), corpusTest.getExamples().get(i).getInput());
            assertEquals(examples.get(i).getOutput(), corpusTest.getExamples().get(i).getOutput());
        }
    }

    @Test
    void parse() throws IOException {
        testParseFile("test/corpus/annotations.txt");
        testParseFile("test/corpus/annotations_extra_delim.txt");
    }

    @Test
    void runTest() throws IOException {
        CorpusTest corpusTest = loadTest("test/corpus/annotations.txt");
        TSLanguage lang = new TreeSitterScala();
        corpusTest.runTest(lang, Assertions::assertEquals);
    }

    @Test
    void runAllTestInFolder() throws IOException {
        TSLanguage lang = new TreeSitterScala();
        CorpusTest.runAllTestsInFolder("src/test/resources/test/corpus", lang, Assertions::assertEquals);
    }

}