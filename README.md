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
    implementation 'io.github.bonede:tree-sitter:0.25.3'
    // add json parser
    implementation 'io.github.bonede:tree-sitter-json:0.24.8'
}
```

```xml
<!-- Maven -->
<dpendencies>
    <!-- add tree sitter -->
    <dependency>
        <groupId>io.github.bonede</groupId>
        <artifactId>tree-sitter</artifactId>
        <version>0.25.3</version>
    </dependency>
    <!-- add json parser -->
    <dependency>
        <groupId>io.github.bonede</groupId>
        <artifactId>tree-sitter-json</artifactId>
        <version>0.24.8</version>
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
| Name                            | Version                                                                                                 |
|---------------------------------|---------------------------------------------------------------------------------------------------------|
| `tree-sitter-ada`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ada)               |
| `tree-sitter-agda`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-agda)              |
| `tree-sitter-apex`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-apex)              |
| `tree-sitter-bash`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-bash)              |
| `tree-sitter-beancount`         | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-beancount)         |
| `tree-sitter-c`                 | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c)                 |
| `tree-sitter-c-sharp`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-c-sharp)           |
| `tree-sitter-capnp`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-capnp)             |
| `tree-sitter-clojure`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-clojure)           |
| `tree-sitter-cmake`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cmake)             |
| `tree-sitter-comment`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-comment)           |
| `tree-sitter-commonlisp`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-commonlisp)        |
| `tree-sitter-cpp`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cpp)               |
| `tree-sitter-css`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-css)               |
| `tree-sitter-cuda`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-cuda)              |
| `tree-sitter-d`                 | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-d)                 |
| `tree-sitter-dart`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dart)              |
| `tree-sitter-dockerfile`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dockerfile)        |
| `tree-sitter-dot`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-dot)               |
| `tree-sitter-elisp`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elisp)             |
| `tree-sitter-elixir`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elixir)            |
| `tree-sitter-elm`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-elm)               |
| `tree-sitter-embedded-template` | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-embedded-template) |
| `tree-sitter-eno`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-eno)               |
| `tree-sitter-erlang`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-erlang)            |
| `tree-sitter-fennel`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fennel)            |
| `tree-sitter-fish`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fish)              |
| `tree-sitter-formula`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-formula)           |
| `tree-sitter-fortran`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-fortran)           |
| `tree-sitter-gitattributes`     | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gitattributes)     |
| `tree-sitter-gitignore`         | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gitignore)         |
| `tree-sitter-gleam`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-gleam)             |
| `tree-sitter-glsl`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-glsl)              |
| `tree-sitter-go`                | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go)                |
| `tree-sitter-go-mod`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go-mod)            |
| `tree-sitter-go-work`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-go-work)           |
| `tree-sitter-graphql`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-graphql)           |
| `tree-sitter-hack`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-hack)              |
| `tree-sitter-haskell`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-haskell)           |
| `tree-sitter-hcl`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-hcl)               |
| `tree-sitter-hocon`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-hocon)             |
| `tree-sitter-html`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-html)              |
| `tree-sitter-java`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-java)              |
| `tree-sitter-javascript`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-javascript)        |
| `tree-sitter-jq`                | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-jq)                |
| `tree-sitter-json`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-json)              |
| `tree-sitter-json5`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-json5)             |
| `tree-sitter-julia`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-julia)             |
| `tree-sitter-kotlin`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-kotlin)            |
| `tree-sitter-lalrpop`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lalrpop)           |
| `tree-sitter-latex`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-latex)             |
| `tree-sitter-lean`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lean)              |
| `tree-sitter-llvm`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-llvm)              |
| `tree-sitter-llvm-mir`          | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-llvm-mir)          |
| `tree-sitter-lua`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-lua)               |
| `tree-sitter-m68k`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-m68k)              |
| `tree-sitter-make`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-make)              |
| `tree-sitter-markdown`          | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-markdown)          |
| `tree-sitter-meson`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-meson)             |
| `tree-sitter-nix`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-nix)               |
| `tree-sitter-nginx`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-nginx)             |
| `tree-sitter-nim`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-nim)               |
| `tree-sitter-objc`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-objc)              |
| `tree-sitter-ocaml`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ocaml)             |
| `tree-sitter-ohm`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ohm)               |
| `tree-sitter-org`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-org)               |
| `tree-sitter-p4`                | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-p4)                |
| `tree-sitter-pascal`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pascal)            |
| `tree-sitter-perl`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-perl)              |
| `tree-sitter-pgn`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pgn)               |
| `tree-sitter-php`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-php)               |
| `tree-sitter-pod`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-pod)               |
| `tree-sitter-proto`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-proto)             |
| `tree-sitter-python`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-python)            |
| `tree-sitter-qmljs`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-qmljs)             |
| `tree-sitter-query`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-query)             |
| `tree-sitter-r`                 | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-r)                 |
| `tree-sitter-racket`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-racket)            |
| `tree-sitter-rasi`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rasi)              |
| `tree-sitter-re2c`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-re2c)              |
| `tree-sitter-regex`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-regex)             |
| `tree-sitter-rego`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rego)              |
| `tree-sitter-rst`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rst)               |
| `tree-sitter-ruby`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ruby)              |
| `tree-sitter-rust`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-rust)              |
| `tree-sitter-tact`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-tact)              |
| `tree-sitter-scala`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scala)             |
| `tree-sitter-scheme`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scheme)            |
| `tree-sitter-scss`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-scss)              |
| `tree-sitter-sexp`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sexp)              |
| `tree-sitter-smali`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-smali)             |
| `tree-sitter-sourcepawn`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sourcepawn)        |
| `tree-sitter-sparql`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sparql)            |
| `tree-sitter-sql`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sql)               |
| `tree-sitter-sql-bigquery`      | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sql-bigquery)      |
| `tree-sitter-sqlite`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-sqlite)            |
| `tree-sitter-ssh-client-config` | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-ssh-client-config) |
| `tree-sitter-svelte`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-svelte)            |
| `tree-sitter-swift`             | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-swift)             |
| `tree-sitter-tablegen`          | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-tablegen)          |
| `tree-sitter-thrift`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-thrift)            |
| `tree-sitter-toml`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-toml)              |
| `tree-sitter-turtle`            | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-turtle)            |
| `tree-sitter-twig`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-twig)              |
| `tree-sitter-typescript`        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-typescript)        |
| `tree-sitter-verilog`           | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-verilog)           |
| `tree-sitter-vhdl`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-vhdl)              |
| `tree-sitter-vue`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-vue)               |
| `tree-sitter-wast`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wast)              |
| `tree-sitter-wat`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wat)               |
| `tree-sitter-wgsl`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-wgsl)              |
| `tree-sitter-yaml`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-yaml)              |
| `tree-sitter-yang`              | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-yang)              |
| `tree-sitter-zig`               | ![Maven Central](https://img.shields.io/maven-central/v/io.github.bonede/tree-sitter-zig)               |

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

# TODO
- [ ] Utilize test cases in parser's repository
