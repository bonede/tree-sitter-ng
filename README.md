# Tree Sitter NG
Next generation Tree Sitter Java binding.

# Getting started
Add dependencies to your `build.gradle` or `pom.xml`.

```groovy
// Gradle
dependencies {
    // add tree sitter
    implementation 'org.tree-sitter:tree-sitter:0.20.8'
    // add json parser
    implementation 'org.tree-sitter:tree-sitter-json:0.20.1'
}
```

```xml
<!-- Maven -->
<dpendencies>
    <!-- add tree sitter -->
    <dependency>
        <groupId>org.tree-sitter</groupId>
        <artifactId>tree-sitter</artifactId>
        <version>0.20.8</version>
    </dependency>
    <!-- add json parser -->
    <dependency>
        <groupId>org.tree-sitter</groupId>
        <artifactId>tree-sitter-json</artifactId>
        <version>0.20.1</version>
    </dependency>
</dpendencies>
```
Start hacking!
```java
// imports are omitted
class Main {
    public static void main(String[] args) {
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        TSTree tree = parser.parseString(null, "[1, null]");
        TSNode rootNode = tree.getRootNode();
        TSNode arrayNode = rootNode.getNamedChild(0);
        TSNode numberNode = arrayNode.getNamedChild(0);
    }
}
```

# Features
- 100% [Tree Sitter API](https://github.com/tree-sitter/tree-sitter/blob/master/lib/include/tree_sitter/api.h) coverage.
- Easy to bootstrap cross compiling environments powered by [Zig](https://ziglang.org/)
- Include only parsers you need in your project.

# Supported parsers

See [settings.gradle](settings.gradle). Parser version in each parser subproject's `gradle.properties` file.

# API Tour
```java

class Main {
    public static void main(String[] args) {
        String jsonSource = "[1, null]";
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        // set language parser
        parser.setLanguage(json);
        // parser with string input
        parser.parseString(null, jsonSource);
        parser.reset();
        // or parser with encoding
        parser.parseStringEncoding(null, JSON_SRC, TSInputEncoding.TSInputEncodingUTF8);
        parser.reset();
        // or parser with custom reader
        byte[] buffer = new byte[1024];
        TSReader reader = (buf, offset, position) -> {
            if(offset >= jsonSource.length()){
                return 0;
            }
            ByteBuffer charBuffer = ByteBuffer.wrap(buf);
            charBuffer.put(jsonSource.getBytes());
            return jsonSource.length();
        };
        TSTree tree = parser.parse(buffer, null, reader, TSInputEncoding.TSInputEncodingUTF8);
        // traverse the AST tree with DOM like APIs
        TSNode rootNode = tree.getRootNode();
        TSNode arrayNode = rootNode.getNamedChild(0);
        // or travers the AST with cursor
        TSTreeCursor rootCursor = new TSTreeCursor(rootNode);
        rootCursor.gotoFirstChild();
        // or query the AST with S-expression
        TreeSitterQuery query = new TSQuery(json, "((document) @root)");
        TSQueryCursor cursor = new TSQueryCursor();
        cursor.exec(query, rootNode);
        SQueryMatch match = new TSQueryMatch();
        while(cursor.nextMatch(match)){
            // do something with the match
        }
        // debug the parser with a logger
        TSLogger logger = (type, message) -> {
            System.out.println(message);
        };
        parser.setLogger(logger);
        // or output the AST tree as DOT graph
        File dotFile = File.createTempFile("json", ".dot");
        parser.printDotGraphs(dotFile);
    }
}

```

# How to add new parser

Use generator script to generate a new parser subproject. 
You can edit the `build.gradle` to customize the native library build process.

```console
./gradlew gen --parser-name=<parser name> --parser-version=<parser version> --parser-zip=<parer zip download url>
# build native library
./gradlew tree-sitter-<parser name>:buildNative
# run tests
./gradlew tree-sitter-<parser name>:test
```