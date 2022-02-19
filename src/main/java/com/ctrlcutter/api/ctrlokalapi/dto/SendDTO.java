package com.ctrlcutter.api.ctrlokalapi.dto;

public class SendDTO extends DefaultDTO {

    private String value;

    public SendDTO(String value, String key, String... modifierKeys) {
        super(key, modifierKeys);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
