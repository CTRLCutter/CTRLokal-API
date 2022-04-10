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
import com.ctrlcutter.backend.persistence.service.PersistenceSaveService;
import com.ctrlcutter.backend.service.BasicScriptGeneratorService;
import com.ctrlcutter.backend.service.HotstringScriptGeneratorService;
import com.ctrlcutter.backend.service.PreDefinedScriptGeneratorService;

@RestController
@RequestMapping("/script")
public class BasicController {

    private BasicScriptGeneratorService basicScriptGeneratorService;
    private HotstringScriptGeneratorService hotStringScriptGeneratorService;
    private PreDefinedScriptGeneratorService preDefinedScriptGeneratorService;
    private PersistenceSaveService persistenceSaveService;

    @Autowired
    public BasicController(BasicScriptGeneratorService basicScriptGeneratorService, HotstringScriptGeneratorService hotStringScriptGeneratorService,
            PreDefinedScriptGeneratorService preDefinedScriptGeneratorService, PersistenceSaveService persistenceSaveService) {
        this.basicScriptGeneratorService = basicScriptGeneratorService;
        this.hotStringScriptGeneratorService = hotStringScriptGeneratorService;
        this.preDefinedScriptGeneratorService = preDefinedScriptGeneratorService;
        this.persistenceSaveService = persistenceSaveService;
    }

    @PostMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicScriptDTO> generateBasicScript(@RequestBody BasicScriptDTO basicScriptDTO) {

        //possible validation for different scripts based on os basicScriptDTO.getOs()

        String script = this.basicScriptGeneratorService.generateBasicScript(basicScriptDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>(basicScriptDTO, HttpStatus.BAD_REQUEST);
        }

        this.persistenceSaveService.saveBasicScript(basicScriptDTO);

        return new ResponseEntity<>(basicScriptDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/hotstring", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicHotstringDTO> generateHotstringScript(@RequestBody BasicHotstringDTO basicHotstringDTO) {

        // Also here OS validation...

        String script = this.hotStringScriptGeneratorService.generateHotstringScript(basicHotstringDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>(basicHotstringDTO, HttpStatus.BAD_REQUEST);
        }

        this.persistenceSaveService.saveBasicHotstringScript(basicHotstringDTO);

        return new ResponseEntity<>(basicHotstringDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/predefined", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreDefinedScriptDTO> generatePreDefinedScript(@RequestBody PreDefinedScriptDTO preDefinedScriptDTO) {

        // Also here OS validation...

        String script = this.preDefinedScriptGeneratorService.generatePreDefinedScript(preDefinedScriptDTO);

        if (script == null || script.isEmpty()) {
            return new ResponseEntity<>(preDefinedScriptDTO, HttpStatus.BAD_REQUEST);
        }

        this.persistenceSaveService.savePreDefinedScript(preDefinedScriptDTO);

        return new ResponseEntity<>(preDefinedScriptDTO, HttpStatus.OK);
    }
}
