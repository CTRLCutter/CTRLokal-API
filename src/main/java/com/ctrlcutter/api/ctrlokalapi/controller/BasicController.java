package com.ctrlcutter.api.ctrlokalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlcutter.backend.dto.BasicHotstringDTO;
import com.ctrlcutter.backend.dto.BasicScriptDTO;
import com.ctrlcutter.backend.dto.PreDefinedScriptDTO;
import com.ctrlcutter.backend.service.BasicScriptGeneratorService;
import com.ctrlcutter.backend.service.HotstringScriptGeneratorService;
import com.ctrlcutter.backend.service.PreDefinedScriptGeneratorService;

@RestController
@RequestMapping("/script")
public class BasicController {

    private BasicScriptGeneratorService basicScriptGeneratorService;
    private HotstringScriptGeneratorService hotStringScriptGeneratorService;
    private PreDefinedScriptGeneratorService preDefinedScriptGeneratorService;

    public BasicController(@Autowired BasicScriptGeneratorService basicScriptGeneratorService,
            @Autowired HotstringScriptGeneratorService hotStringScriptGeneratorService,
            @Autowired PreDefinedScriptGeneratorService preDefinedScriptGeneratorService) {
        this.basicScriptGeneratorService = basicScriptGeneratorService;
        this.hotStringScriptGeneratorService = hotStringScriptGeneratorService;
        this.preDefinedScriptGeneratorService = preDefinedScriptGeneratorService;
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

    @PostMapping(value = "/hotstring", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateHotstringScript(@RequestBody BasicHotstringDTO basicHotstringDTO) {

        // Also here OS validation...

        String script = this.hotStringScriptGeneratorService.generateHotstringScript(basicHotstringDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>("Something went wrong. Please try again.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(script, HttpStatus.OK);
    }

    @PostMapping(value = "/predefined", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generatePreDefinedScript(@RequestBody PreDefinedScriptDTO preDefinedScriptDTO) {

        // Also here OS validation...

        String script = this.preDefinedScriptGeneratorService.generatePreDefinedScript(preDefinedScriptDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>("Something went wrong. Please try again.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(script, HttpStatus.OK);
    }
}
