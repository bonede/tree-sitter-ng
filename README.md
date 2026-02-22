# Tree Sitter NG
Next generation Tree Sitter Java binding.

[![Maven Central](https://img.shields.io/github/actions/workflow/status/bonede/tree-sitter-ng/main.yml)](https://github.com/bonede/tree-sitter-ng/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter)](https://central.sonatype.com/artifact/io.github.bonede/tree-sitter)

# Getting started
Add dependencies to your `build.gradle` or `pom.xml`.

```groovy
// Gradle
dependencies {
    // add tree sitter
    implementation 'io.github.bonede:tree-sitter:$VERSION'
    // add json parser
    implementation 'io.github.bonede:tree-sitter-json:$VERSION'
}
```

```xml
<!-- Maven -->
<dpendencies>
    <!-- add tree sitter -->
    <dependency>
        <groupId>io.github.bonede</groupId>
        <artifactId>tree-sitter</artifactId>
        <version>$VERSION</version>
    </dependency>
    <!-- add json parser -->
    <dependency>
        <groupId>io.github.bonede</groupId>
        <artifactId>tree-sitter-json</artifactId>
        <version>$VERSION</version>
    </dependency>
</dpendencies>
```
Start hacking!
```java
// imports are omitted
class Main {
    public static void main(String[] args) {
        TSParser parser = new TSParser();
        // Use `TSLanguage.load` instead if you would like to load parsers as shared object(.so, .dylib, or .dll).
        // TSLanguage.load("path/to/languane/shared/object", "tree_sitter_some_lang");
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
- Easy to bootstrap cross compiling environments powered by [Zig](https://ziglang.org/).
- Built-in official parsers.
- Load parsers as [shared object](https://tree-sitter.github.io/tree-sitter/cli/build.html) from disk.

# Supported CPUs and OSes
- x86_64-windows
- x86_64-macos
- aarch64-macos
- x86_64-linux
- aarch64-linux

# Built-in official parsers
| Name                            | Version                                                                                                 |
|---------------------------------|---------------------------------------------------------------------------------------------------------|
| `tree-sitter-agda`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-agda)              |
| `tree-sitter-bash`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-bash)              |
| `tree-sitter-c`                 | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c)                 |
| `tree-sitter-c-sharp`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c-sharp)           |
| `tree-sitter-cpp`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cpp)               |
| `tree-sitter-css`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-css)               |
| `tree-sitter-embedded-template` | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-embedded-template) |
| `tree-sitter-go`                | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go)                |
| `tree-sitter-haskell`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-haskell)           |
| `tree-sitter-html`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-html)              |
| `tree-sitter-java`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-java)              |
| `tree-sitter-javascript`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-javascript)        |
| `tree-sitter-json`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-json)              |
| `tree-sitter-julia`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-julia)             |
| `tree-sitter-ocaml`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ocaml)             |
| `tree-sitter-php`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-php)               |
| `tree-sitter-python`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-python)            |
| `tree-sitter-regex`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-regex)             |
| `tree-sitter-ruby`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ruby)              |
| `tree-sitter-rust`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rust)              |
| `tree-sitter-scala`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scala)             |
| `tree-sitter-tsx`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-tsx)               |
| `tree-sitter-typescript`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-typescript)        |
| `tree-sitter-verilog`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-verilog)           |

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
