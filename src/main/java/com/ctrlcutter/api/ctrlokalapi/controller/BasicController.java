package com.ctrlcutter.api.ctrlokalapi.controller;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlcutter.api.ctrlokalapi.dto.RunDTO;
import com.ctrlcutter.api.ctrlokalapi.dto.SendDTO;
import com.ctrlcutter.api.ctrlokalapi.service.RunScriptGeneratorService;
import com.ctrlcutter.api.ctrlokalapi.service.SendScriptGeneratorService;

@RestController
@RequestMapping("/script")
public class BasicController {

    private SendScriptGeneratorService sendScriptGeneratorService;
    private RunScriptGeneratorService runScriptGeneratorService;

    public BasicController(@Autowired SendScriptGeneratorService sendScriptGeneratorService, @Autowired RunScriptGeneratorService runScriptGeneratorService) {
        this.sendScriptGeneratorService = sendScriptGeneratorService;
        this.runScriptGeneratorService = runScriptGeneratorService;
    }

    @PostMapping(value = "/run", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> runCommand(@RequestBody RunDTO runDTO) {
        String script = this.runScriptGeneratorService.generateRunScript(runDTO);

        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8))), HttpStatus.OK);
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> sendCommand(@RequestBody SendDTO sendDTO) {
        String script = this.sendScriptGeneratorService.generateSendScript(sendDTO);

        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8))), HttpStatus.OK);
    }
}
