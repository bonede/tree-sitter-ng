package org.treesitter;

/**
 * The metadata associated with a language.
 * Currently, this metadata can be used to check the [Semantic Version](https://semver.org/)
 * of the language. This version information should be used to signal if a given parser might
 * be incompatible with existing queries when upgrading between major versions, or minor versions
 * if it's in zerover.
 */
public class TSLanguageMetadata {
    private int majorVersion;
    private int minorVersion;
    private int patchVersion;

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getPatchVersion() {
        return patchVersion;
    }
}
