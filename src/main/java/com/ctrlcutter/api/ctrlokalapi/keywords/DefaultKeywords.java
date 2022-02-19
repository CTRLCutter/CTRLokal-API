package com.ctrlcutter.api.ctrlokalapi.keywords;

public enum DefaultKeywords {

    START("::"),
    RETURN("return");

    private String keyword;

    public String getKeyword() {
        return this.keyword;
    }

    DefaultKeywords(String keyword) {
        this.keyword = keyword;
    }
}
