package org.treesitter.demo;

import org.treesitter.TSNode;
import org.treesitter.TSParser;
import org.treesitter.TSTree;
import org.treesitter.TreeSitterJson;

public class Main {
    public static void main(String[] args) {
        // Example from https://tree-sitter.github.io/tree-sitter/using-parsers
        TSParser tsParser = new TSParser();
        tsParser.setLanguage(new TreeSitterJson());
        TSTree tree = tsParser.parseString(null, "[1, null]");
        TSNode rootNode = tree.getRootNode();
        TSNode arrayNode = rootNode.getNamedChild(0);
        TSNode numberNode = arrayNode.getNamedChild(0);
        assert rootNode.getType().equals("document");
        assert arrayNode.getType().equals("array");
        assert numberNode.getType().equals("number");
        assert rootNode.getChildCount() == 1;
        assert arrayNode.getChildCount() == 5;
        assert arrayNode.getNamedChildCount() == 2;
        assert numberNode.getChildCount() == 0;
        String string = rootNode.toString();
        System.out.printf("Syntax tree: %s\n%n", string);
    }
}