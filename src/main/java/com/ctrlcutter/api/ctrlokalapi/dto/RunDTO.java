package com.ctrlcutter.api.ctrlokalapi.dto;

public class RunDTO extends DefaultDTO {

    private String value;
    private String parameter;


    public RunDTO(String value, String parameter, String key, String... modifierKeys) {
        super(key, modifierKeys);
        this.value = value;
        this.parameter = parameter;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
