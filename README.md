# Tree Sitter NG
Next generation Tree Sitter Java binding.

# Getting started
Add dependencies to your build.gradle or pom.xml.
```groovy
// Gradle
dependencies {
    implementation 'org.tree-sitter:tree-sitter:0.20.8'
    // add json parser
    implementation 'org.tree-sitter:tree-sitter-json:0.20.1'
}
```

```xml
<!-- Maven -->
<dpendencies>
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
- 100% Tree Sitter API coverage.
- Easy to bootstrap cross compiling environments.
- Easy to add new Languages.

# Supported languages
TODO

# API Tour
```java
// TODO

```

# How to add new language
```console
./gradlew gen --parser-name=<parser name> --parser-version=<parser version> --parser-zip=<parer zip download url>

```