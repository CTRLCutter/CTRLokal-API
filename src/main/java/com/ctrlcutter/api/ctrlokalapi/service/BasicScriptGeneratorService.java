package com.ctrlcutter.api.ctrlokalapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ctrlcutter.api.ctrlokalapi.constants.DefaultCommands;
import com.ctrlcutter.api.ctrlokalapi.constants.DefaultKeywords;
import com.ctrlcutter.api.ctrlokalapi.constants.ModifierKeys;
import com.ctrlcutter.api.ctrlokalapi.dto.BasicScriptDTO;

@Service
public class BasicScriptGeneratorService {

    public String generateBasicScript(BasicScriptDTO basicScriptDTO) {
        String script = "";

        List<String> translatedModifierKeys = this.translateModifierKeys(basicScriptDTO.getModifierKeys());
        String command = this.translateCommand(basicScriptDTO.getCommand());

        if (translatedModifierKeys.isEmpty() || command == null) {
            return null;
        }

        String hotKeyLine = this.generateHotKeyLine(translatedModifierKeys, basicScriptDTO.getKey());

        String commandLine = this.generateCommandLine(command, basicScriptDTO.getParameters());

        return hotKeyLine + commandLine + DefaultKeywords.RETURN.getKeyword();
    }

    private List<String> translateModifierKeys(String[] modifierKeys) {
        List<String> translatedModifierKeys = new ArrayList<>(modifierKeys.length);

        for (String modifierKey : modifierKeys) {
            for (ModifierKeys keyConstants : ModifierKeys.values()) {
                if (modifierKey.equals(keyConstants.name())) {
                    translatedModifierKeys.add(keyConstants.getSymbol());
                }
            }
        }

        return translatedModifierKeys;
    }

    private String translateCommand(String command) {
        for (DefaultCommands commandConstant : DefaultCommands.values()) {
            if (command.equals(commandConstant.name())) {
                return commandConstant.getCommand();
            }
        }
        return null;
    }

    private String generateHotKeyLine(List<String> translatedModifierKeys, String key) {
        String keyLine = "";

        for (String modifierKey : translatedModifierKeys) {
            keyLine = keyLine.concat(modifierKey);
        }

        return keyLine + key + DefaultKeywords.START.getKeyword() + System.lineSeparator();
    }

    private String generateCommandLine(String command, String[] parameters) {
        String commandLine = command;

        for (String parameter : parameters) {
            commandLine = commandLine.concat(parameter + " ");
        }

        return commandLine + System.lineSeparator();
    }
}
