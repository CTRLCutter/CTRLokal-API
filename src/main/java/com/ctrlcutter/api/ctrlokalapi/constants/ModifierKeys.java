package com.ctrlcutter.api.ctrlokalapi.constants;

public enum ModifierKeys {

    CTRL("^"),
    SHIFT("+"),
    ALT("!"),
    WINDOWS("#");

    private String symbol;

    public String getSymbol() {
        return this.symbol;
    }

    ModifierKeys(String symbol) {
        this.symbol = symbol;
    }
}
