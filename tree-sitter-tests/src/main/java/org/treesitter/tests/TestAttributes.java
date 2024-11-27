package org.treesitter.tests;

import java.util.ArrayList;
import java.util.List;

public class TestAttributes {
    private boolean skip;
    private String platform;
    private boolean failFast;
    private boolean error;
    private List<String> languages = new ArrayList<>();
    private List<String> platforms = new ArrayList<>();

    public TestAttributes() {

    }

    public TestAttributes(boolean skip, String platform, boolean failFast, boolean error, List<String> languages, List<String> platforms) {
        this.skip = skip;
        this.platform = platform;
        this.failFast = failFast;
        this.error = error;
        this.languages = languages;
        this.platforms = platforms;
    }

    public void addLanguage(String lang){
        this.languages.add(lang);
    }

    public void addPlatform(String platform){
        this.platforms.add(platform);
    }

    public boolean isSkip() {
        return skip;
    }

    public String getPlatform() {
        return platform;
    }

    public boolean isFailFast() {
        return failFast;
    }

    public boolean isError() {
        return error;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
