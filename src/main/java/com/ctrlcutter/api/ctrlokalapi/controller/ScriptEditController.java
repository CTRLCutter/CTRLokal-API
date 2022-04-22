package com.ctrlcutter.api.ctrlokalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ctrlcutter.backend.dto.BasicHotstringDTO;
import com.ctrlcutter.backend.dto.BasicScriptDTO;
import com.ctrlcutter.backend.dto.PreDefinedScriptDTO;
import com.ctrlcutter.backend.persistence.service.ScriptEditService;

@RestController
@RequestMapping("/edit")
public class ScriptEditController {

    private final ScriptEditService scriptEditService;

    @Autowired
    public ScriptEditController(ScriptEditService scriptEditService) {
        this.scriptEditService = scriptEditService;
    }

    @PutMapping(value = "/basicScript", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicScriptDTO> editBasicScript(@RequestParam() int id, @RequestBody BasicScriptDTO basicScriptDTO) {
        boolean editSucceed = this.scriptEditService.editBasicScript(id, basicScriptDTO);

        if (editSucceed) {
            return new ResponseEntity<>(basicScriptDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(basicScriptDTO, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/hotstringScript", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicHotstringDTO> editHotstringScript(@RequestParam() int id, @RequestBody BasicHotstringDTO basicHotstringDTO) {
        boolean editSucceed = this.scriptEditService.editHotstringScript(id, basicHotstringDTO);

        if (editSucceed) {
            return new ResponseEntity<>(basicHotstringDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(basicHotstringDTO, HttpStatus.NOT_FOUND);
        }
    }

    //fix bug with duplicate entrys of default script and dto
    @PutMapping(value = "/preDefinedScript", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreDefinedScriptDTO> editPreDefinedScript(@RequestParam() int id, @RequestBody PreDefinedScriptDTO preDefinedScriptDTO) {
        boolean editSucceed = this.scriptEditService.editPreDefinedScript(id, preDefinedScriptDTO);

        if (editSucceed) {
            return new ResponseEntity<>(preDefinedScriptDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(preDefinedScriptDTO, HttpStatus.NOT_FOUND);
        }
    }

}
