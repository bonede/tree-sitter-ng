package org.treesitter.tests;

public class TestExample {
    private String name;
    private String input;
    private String output;
    private TestAttributes attributes;

    public TestExample() {

    }

    public TestExample(String name, String input, String output, TestAttributes attributes) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.attributes = attributes;
    }

    public TestExample(String name, String input, String output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setAttributes(TestAttributes attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public TestAttributes getAttributes() {
        return attributes;
    }

    public boolean isExampleFor(String lang){
        return attributes == null ||
                attributes.getLanguages() == null ||
                attributes.getLanguages().contains(lang);
    }
}
