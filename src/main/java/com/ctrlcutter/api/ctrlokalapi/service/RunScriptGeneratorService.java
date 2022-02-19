package com.ctrlcutter.api.ctrlokalapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ctrlcutter.api.ctrlokalapi.commands.DefaultCommands;
import com.ctrlcutter.api.ctrlokalapi.dto.RunDTO;
import com.ctrlcutter.api.ctrlokalapi.keys.ModifierKeys;
import com.ctrlcutter.api.ctrlokalapi.keywords.DefaultKeywords;

@Service
public class RunScriptGeneratorService {

    public String generateRunScript(RunDTO runDTO) {

        if (runDTO.getModifierKeys().length <= 1) {
            return this.generateScriptOneModifier(runDTO);
        } else {
            return this.generateScriptMultipleModifiers(runDTO);
        }
    }

    private String generateScriptOneModifier(RunDTO runDTO) {
        String modifierKey = "";

        for (ModifierKeys keyConstants : ModifierKeys.values()) {
            if (keyConstants.name().equals(runDTO.getModifierKeys()[0])) {
                modifierKey = keyConstants.getSymbol();
            }
        }

        if (runDTO.getParameter() == null || runDTO.getParameter().isEmpty()) {
            return modifierKey + runDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.RUN.getCommand()
                    + this.inParantheses(runDTO.getValue()) + System.lineSeparator() + DefaultKeywords.RETURN.getKeyword();
        } else {
            return modifierKey + runDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.RUN.getCommand()
                    + this.inParantheses(runDTO.getValue()) + " " + this.inParantheses(runDTO.getParameter()) + System.lineSeparator()
                    + DefaultKeywords.RETURN.getKeyword();
        }
    }

    private String generateScriptMultipleModifiers(RunDTO runDTO) {
        List<String> modifierKeys = new ArrayList<>(runDTO.getModifierKeys().length);

        for (String modifierKey : runDTO.getModifierKeys()) {
            for (ModifierKeys keyConstants : ModifierKeys.values()) {
                if (modifierKey.equals(keyConstants.name())) {
                    modifierKeys.add(keyConstants.getSymbol());
                }
            }
        }

        String script = "";

        for (String modifierKey : modifierKeys) {
            script = script.concat(modifierKey);
        }

        if (runDTO.getParameter() == null || runDTO.getParameter().isEmpty()) {
            return script + runDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.RUN.getCommand()
                    + this.inParantheses(runDTO.getValue()) + System.lineSeparator() + DefaultKeywords.RETURN.getKeyword();
        } else {
            return script + runDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.RUN.getCommand()
                    + this.inParantheses(runDTO.getValue()) + " " + this.inParantheses(runDTO.getParameter()) + System.lineSeparator()
                    + DefaultKeywords.RETURN.getKeyword();
        }
    }

    private String inParantheses(String value) {
        return "\"" + value + "\"";
    }
}
