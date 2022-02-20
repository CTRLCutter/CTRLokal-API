package com.ctrlcutter.api.ctrlokalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlcutter.api.ctrlokalapi.dto.BasicScriptDTO;
import com.ctrlcutter.api.ctrlokalapi.service.BasicScriptGeneratorService;

@RestController
@RequestMapping("/script")
public class BasicController {

    private BasicScriptGeneratorService basicScriptGeneratorService;

    public BasicController(@Autowired BasicScriptGeneratorService basicScriptGeneratorService) {
        this.basicScriptGeneratorService = basicScriptGeneratorService;
    }

    @PostMapping(value = "/basic", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateBasicScript(@RequestBody BasicScriptDTO basicScriptDTO) {

        //possible validation for different scripts based on os basicScriptDTO.getOs()

        String script = this.basicScriptGeneratorService.generateBasicScript(basicScriptDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>("Something went wrong. Please try again.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(script, HttpStatus.OK);
    }
}
