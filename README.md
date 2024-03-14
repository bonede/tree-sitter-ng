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
    implementation 'io.github.bonede:tree-sitter:0.22.1'
    // add json parser
    implementation 'io.github.bonede:tree-sitter-json:0.20.1'
}
```

```xml
<!-- Maven -->
<dpendencies>
    <!-- add tree sitter -->
    <dependency>
        <groupId>io.github.bonede</groupId>
        <artifactId>tree-sitter</artifactId>
        <version>0.22.1</version>
    </dependency>
    <!-- add json parser -->
    <dependency>
        <groupId>io.github.bonede</groupId>
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
- Easy to bootstrap cross compiling environments powered by [Zig](https://ziglang.org/).
- Include only parsers you need in your project.

# Supported CPUs and OSes
- x86_64-windows
- x86_64-macos
- aarch64-macos
- x86_64-linux
- aarch64-linux

# Supported parsers

| Name                                        | Veriosn |
|:--------------------------------------------|:--------| 
| `io.github.bonede:tree-sitter-ada:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ada) |
| `io.github.bonede:tree-sitter-agda:1.2.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-agda) |
| `io.github.bonede:tree-sitter-apex:0.0.10`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-apex) |
| `io.github.bonede:tree-sitter-bash:0.20.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-bash) |
| `io.github.bonede:tree-sitter-beancount:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-beancount) |
| `io.github.bonede:tree-sitter-c:treeSitterCVersion=0.20.5`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c) |
| `io.github.bonede:tree-sitter-c-sharp:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c-sharp) |
| `io.github.bonede:tree-sitter-capnp:1.5.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-capnp) |
| `io.github.bonede:tree-sitter-clojure:0.0.12`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-clojure) |
| `io.github.bonede:tree-sitter-cmake:0.4.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cmake) |
| `io.github.bonede:tree-sitter-comment:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-comment) |
| `io.github.bonede:tree-sitter-commonlisp:0.3.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-commonlisp) |
| `io.github.bonede:tree-sitter-cpp:0.20.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cpp) |
| `io.github.bonede:tree-sitter-css:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-css) |
| `io.github.bonede:tree-sitter-cuda:0.20.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cuda) |
| `io.github.bonede:tree-sitter-d:0.3.8`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-d) |
| `io.github.bonede:tree-sitter-dart:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dart) |
| `io.github.bonede:tree-sitter-dockerfile:0.1.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dockerfile) |
| `io.github.bonede:tree-sitter-dot:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dot) |
| `io.github.bonede:tree-sitter-elisp:1.3.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elisp) |
| `io.github.bonede:tree-sitter-elixir:0.1.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elixir) |
| `io.github.bonede:tree-sitter-elm:5.7.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elm) |
| `io.github.bonede:tree-sitter-embedded-template:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-embedded-template) |
| `io.github.bonede:tree-sitter-eno:0.1.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-eno) |
| `io.github.bonede:tree-sitter-erlang:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-erlang) |
| `io.github.bonede:tree-sitter-fennel:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fennel) |
| `io.github.bonede:tree-sitter-fish:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fish) |
| `io.github.bonede:tree-sitter-formula:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-formula) |
| `io.github.bonede:tree-sitter-fortran:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fortran) |
| `io.github.bonede:tree-sitter-gitattributes:0.1.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gitattributes) |
| `io.github.bonede:tree-sitter-gitignore:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gitignore) |
| `io.github.bonede:tree-sitter-gleam:0.33.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gleam) |
| `io.github.bonede:tree-sitter-glsl:0.1.6`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-glsl) |
| `io.github.bonede:tree-sitter-go:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go) |
| `io.github.bonede:tree-sitter-go-mod:1.0.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go-mod) |
| `io.github.bonede:tree-sitter-go-work:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go-work) |
| `io.github.bonede:tree-sitter-graphql:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-graphql) |
| `io.github.bonede:tree-sitter-hack:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-hack) |
| `io.github.bonede:tree-sitter-haskell:0.13.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-haskell) |
| `io.github.bonede:tree-sitter-hcl:1.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-hcl) |
| `io.github.bonede:tree-sitter-html:0.19.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-html) |
| `io.github.bonede:tree-sitter-java:0.20.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-java) |
| `io.github.bonede:tree-sitter-javascript:0.20.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-javascript) |
| `io.github.bonede:tree-sitter-jq:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-jq) |
| `io.github.bonede:tree-sitter-json:0.20.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-json) |
| `io.github.bonede:tree-sitter-json5:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-json5) |
| `io.github.bonede:tree-sitter-julia:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-julia) |
| `io.github.bonede:tree-sitter-kotlin:0.3.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-kotlin) |
| `io.github.bonede:tree-sitter-lalrpop:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lalrpop) |
| `io.github.bonede:tree-sitter-latex:0.3.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-latex) |
| `io.github.bonede:tree-sitter-lean:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lean) |
| `io.github.bonede:tree-sitter-llvm:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-llvm) |
| `io.github.bonede:tree-sitter-llvm-mir:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-llvm-mir) |
| `io.github.bonede:tree-sitter-lua:2.1.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lua) |
| `io.github.bonede:tree-sitter-m68k:0.2.7`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-m68k) |
| `io.github.bonede:tree-sitter-make:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-make) |
| `io.github.bonede:tree-sitter-markdown:0.7.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-markdown) |
| `io.github.bonede:tree-sitter-meson:1.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-meson) |
| `io.github.bonede:tree-sitter-nix:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-nix) |
| `io.github.bonede:tree-sitter-objc:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-objc) |
| `io.github.bonede:tree-sitter-ocaml:0.20.4`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ocaml) |
| `io.github.bonede:tree-sitter-org:1.3.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-org) |
| `io.github.bonede:tree-sitter-pascal:0.9.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pascal) |
| `io.github.bonede:tree-sitter-perl:0.4.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-perl) |
| `io.github.bonede:tree-sitter-pgn:1.0.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pgn) |
| `io.github.bonede:tree-sitter-php:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-php) |
| `io.github.bonede:tree-sitter-pod:release`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pod) |
| `io.github.bonede:tree-sitter-proto:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-proto) |
| `io.github.bonede:tree-sitter-python:0.20.4`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-python) |
| `io.github.bonede:tree-sitter-qmljs:0.1.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-qmljs) |
| `io.github.bonede:tree-sitter-query:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-query) |
| `io.github.bonede:tree-sitter-r:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-r) |
| `io.github.bonede:tree-sitter-racket:0.3.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-racket) |
| `io.github.bonede:tree-sitter-rasi:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rasi) |
| `io.github.bonede:tree-sitter-re2c:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-re2c) |
| `io.github.bonede:tree-sitter-regex:0.20.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-regex) |
| `io.github.bonede:tree-sitter-rego:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rego) |
| `io.github.bonede:tree-sitter-rst:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rst) |
| `io.github.bonede:tree-sitter-ruby:0.19.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ruby) |
| `io.github.bonede:tree-sitter-rust:0.20.4`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rust) |
| `io.github.bonede:tree-sitter-scala:0.20.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scala) |
| `io.github.bonede:tree-sitter-scheme:0.6.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scheme) |
| `io.github.bonede:tree-sitter-scss:1.0.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scss) |
| `io.github.bonede:tree-sitter-sexp:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sexp) |
| `io.github.bonede:tree-sitter-smali:1.0.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-smali) |
| `io.github.bonede:tree-sitter-sourcepawn:0.6.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sourcepawn) |
| `io.github.bonede:tree-sitter-sparql:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sparql) |
| `io.github.bonede:tree-sitter-sql:gh-pages`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sql) |
| `io.github.bonede:tree-sitter-sql-bigquery:0.6.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sql-bigquery) |
| `io.github.bonede:tree-sitter-sqlite:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sqlite) |
| `io.github.bonede:tree-sitter-ssh-client-config:2023.12.21`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ssh-client-config) |
| `io.github.bonede:tree-sitter-svelte:0.11.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-svelte) |
| `io.github.bonede:tree-sitter-swift:0.3.6`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-swift) |
| `io.github.bonede:tree-sitter-tablegen:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-tablegen) |
| `io.github.bonede:tree-sitter-thrift:0.5.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-thrift) |
| `io.github.bonede:tree-sitter-toml:0.5.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-toml) |
| `io.github.bonede:tree-sitter-turtle:0.1.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-turtle) |
| `io.github.bonede:tree-sitter-twig:1.0.2`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-twig) |
| `io.github.bonede:tree-sitter-typescript:0.20.3`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-typescript) |
| `io.github.bonede:tree-sitter-verilog:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-verilog) |
| `io.github.bonede:tree-sitter-vhdl:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-vhdl) |
| `io.github.bonede:tree-sitter-vue:0.2.1`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-vue) |
| `io.github.bonede:tree-sitter-wast:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wast) |
| `io.github.bonede:tree-sitter-wat:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wat) |
| `io.github.bonede:tree-sitter-wgsl:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wgsl) |
| `io.github.bonede:tree-sitter-yaml:0.5.0`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-yaml) |
| `io.github.bonede:tree-sitter-yang:master`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-yang) |
| `io.github.bonede:tree-sitter-zig:main`  | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-zig) |
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