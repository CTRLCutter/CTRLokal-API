package com.ctrlcutter.api.ctrlokalapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ctrlcutter.api.ctrlokalapi.commands.DefaultCommands;
import com.ctrlcutter.api.ctrlokalapi.dto.SendDTO;
import com.ctrlcutter.api.ctrlokalapi.keys.ModifierKeys;
import com.ctrlcutter.api.ctrlokalapi.keywords.DefaultKeywords;

@Service
public class SendScriptGeneratorService {

    public String generateSendScript(SendDTO sendDTO) {

        if (sendDTO.getModifierKeys().length <= 1) {
            return this.generateScriptOneModifier(sendDTO);
        } else {
            return this.generateScriptMultipleModifiers(sendDTO);
        }

    }

    private String generateScriptOneModifier(SendDTO sendDTO) {
        String modifierKey = "";

        for (ModifierKeys keyConstants : ModifierKeys.values()) {
            if (keyConstants.name().equals(sendDTO.getModifierKeys()[0])) {
                modifierKey = keyConstants.getSymbol();
            }
        }

        return modifierKey + sendDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.SEND.getCommand()
                + sendDTO.getValue() + System.lineSeparator() + DefaultKeywords.RETURN.getKeyword();
    }

    private String generateScriptMultipleModifiers(SendDTO sendDTO) {
        List<String> modifierKeys = new ArrayList<>(sendDTO.getModifierKeys().length);

        for (String modifierKey : sendDTO.getModifierKeys()) {
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

        return script + sendDTO.getKey() + DefaultKeywords.START.getKeyword() + System.lineSeparator() + DefaultCommands.SEND.getCommand() + sendDTO.getValue()
                + System.lineSeparator() + DefaultKeywords.RETURN.getKeyword();

    }
}
