package com.ctrlcutter.api.ctrlokalapi.dto;

public class DefaultDTO {

    private String key;
    private String[] modifierKeys;

    public DefaultDTO(String key, String... modifierKeys) {
        this.key = key;
        this.modifierKeys = modifierKeys;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getModifierKeys() {
        return this.modifierKeys;
    }

    public void setModifierKeys(String[] modifierKeys) {
        this.modifierKeys = modifierKeys;
    }
}
