package com.ctrlcutter.api.ctrlokalapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlcutter.backend.dto.BasicPreDefinedDTO;
import com.ctrlcutter.backend.persistence.model.BasicHotstringScript;
import com.ctrlcutter.backend.persistence.model.BasicScript;
import com.ctrlcutter.backend.persistence.model.PreDefinedScript;
import com.ctrlcutter.backend.persistence.service.PersistenceReceiveService;

@RestController
@RequestMapping("/storage")
public class PersistenceController {

    private PersistenceReceiveService persistenceReceiveService;

    @Autowired
    public PersistenceController(PersistenceReceiveService persistenceReceiveService) {
        this.persistenceReceiveService = persistenceReceiveService;
    }

    @GetMapping(value = "/allBasic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasicScript>> getAllBasicScripts() {
        List<BasicScript> basicScripts = this.persistenceReceiveService.getAllBasicScripts();

        if (basicScripts.isEmpty()) {
            return new ResponseEntity<>(basicScripts, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(basicScripts, HttpStatus.OK);
    }

    @GetMapping(value = "/allHotstring", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasicHotstringScript>> getAllHotstringScripts() {
        List<BasicHotstringScript> basicHotstringScripts = this.persistenceReceiveService.getAllHotstringScripts();

        if (basicHotstringScripts.isEmpty()) {
            return new ResponseEntity<>(basicHotstringScripts, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(basicHotstringScripts, HttpStatus.OK);
    }

    @GetMapping(value = "/allPreDefined", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreDefinedScript>> getAllPreDefinedScripts() {
        List<PreDefinedScript> preDefinedScripts = this.persistenceReceiveService.getAllPreDefinedScripts();

        if (preDefinedScripts.isEmpty()) {
            return new ResponseEntity<>(preDefinedScripts, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(preDefinedScripts, HttpStatus.OK);
    }

    @GetMapping(value = "/allBasicPreDefined", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicPreDefinedDTO> getAllBasicPreDefinedScripts() {
        BasicPreDefinedDTO basicPreDefinedDTO = new BasicPreDefinedDTO();
        basicPreDefinedDTO.setBasicScripts(this.persistenceReceiveService.getAllBasicScripts());
        basicPreDefinedDTO.setPreDefinedScripts(this.persistenceReceiveService.getAllPreDefinedScripts());

        if (basicPreDefinedDTO.getBasicScripts().isEmpty() && basicPreDefinedDTO.getPreDefinedScripts().isEmpty()) {
            return new ResponseEntity<>(basicPreDefinedDTO, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(basicPreDefinedDTO, HttpStatus.OK);
    }
}
