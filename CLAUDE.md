# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java binding for [Tree Sitter](https://github.com/tree-sitter/tree-sitter) - a parser generator tool and an incremental parsing library. The project provides 100% API coverage for the Tree Sitter C library.

## Commands

```bash
# Build all subprojects
./gradlew build

# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "org.treesitter.TSParserTest"

# Run a specific test method
./gradlew test --tests "org.treesitter.TSParserTest.testParseString"

# Run tests for a specific language parser (e.g., tree-sitter-json)
./gradlew :tree-sitter-json:test

# Clean and rebuild
./gradlew clean build

# Build native libraries only
./gradlew buildNative

# Release to Maven Central Staging
./gradlew :tree-sitter-<packageA>:publish :tree-sitter-<package2>:publish
# Publish
./gradlew postPublish
# Then publish manually in https://central.sonatype.com/publishing 
```

## Architecture

### Multi-Project Structure
- **`tree-sitter`** - Core library containing JNI bindings to the native Tree Sitter library
- **`tree-sitter-{lang}`** - Individual language parser bindings (json, c, cpp, rust, javascript, typescript, etc.)
- **`tree-sitter-tests`** - Shared test utilities (CorpusTest for tree-sitter corpus format)
- **`tree-sitter-demo`** - Demo application

### Core Components

**JNI Layer** (`tree-sitter/src/main/java/org/treesitter/`)
- `TSParser` - Main parser class with native methods
- `TSLanguage` - Abstract base for language bindings
- `TSNode` - AST node representation
- `TSTree` - Parse tree
- `TSTreeCursor` - Cursor for tree traversal
- `TSQuery` / `TSQueryCursor` - Pattern matching on AST
- `NativeUtils` - Loads native libraries from classpath or filesystem

**Language Bindings** (e.g., `tree-sitter-json/src/main/java/org/treesitter/TreeSitterJson.java`)
- Each language has a single class extending `TSLanguage`
- Contains embedded native library via static initializer
- Generated during build via `GenTask`

**Memory Management**
- Uses `CleanerRunner` for phantom-reachable cleanup of native resources
- Each `TSLanguage` subclass registers a cleanup action

### Native Library Build
- Uses **Zig** for cross-compilation to multiple platforms
- Native sources downloaded during build (`downloadSource` task)
- Built as part of `buildNative` task

### Supported Platforms
- x86_64 Windows/macOS/Linux
- aarch64 macOS/Linux

### Language parser versioning
- Only contains official tree sitter parser.
- Version is specified in sub-projects' gradle.properties file.
- New version can be obtained from Github's release page, e.g. https://github.com/tree-sitter/tree-sitter-python/releases
