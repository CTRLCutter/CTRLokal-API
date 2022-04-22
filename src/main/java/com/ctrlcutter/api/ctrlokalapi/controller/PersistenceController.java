package com.ctrlcutter.api.ctrlokalapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ctrlcutter.backend.dto.AnonymizedScriptDTO;
import com.ctrlcutter.backend.dto.BasicAndPreDefinedDTO;
import com.ctrlcutter.backend.persistence.model.BasicHotstringScript;
import com.ctrlcutter.backend.persistence.model.BasicScript;
import com.ctrlcutter.backend.persistence.model.PreDefinedScript;
import com.ctrlcutter.backend.persistence.service.PersistenceReceiveService;
import com.ctrlcutter.backend.persistence.service.PersistenceSaveService;

@RestController
@RequestMapping("/storage")
public class PersistenceController {

    private final PersistenceReceiveService persistenceReceiveService;
    private final PersistenceSaveService persistenceSaveService;

    @Autowired
    public PersistenceController(PersistenceReceiveService persistenceReceiveService, PersistenceSaveService persistenceSaveService) {
        this.persistenceReceiveService = persistenceReceiveService;
        this.persistenceSaveService = persistenceSaveService;
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
    public ResponseEntity<BasicAndPreDefinedDTO> getAllBasicPreDefinedScripts() {
        BasicAndPreDefinedDTO basicAndPreDefinedDTO = new BasicAndPreDefinedDTO();
        basicAndPreDefinedDTO.setBasicScripts(this.persistenceReceiveService.getAllBasicScripts());
        basicAndPreDefinedDTO.setPreDefinedScripts(this.persistenceReceiveService.getAllPreDefinedScripts());

        if (basicAndPreDefinedDTO.getBasicScripts().isEmpty() && basicAndPreDefinedDTO.getPreDefinedScripts().isEmpty()) {
            return new ResponseEntity<>(basicAndPreDefinedDTO, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(basicAndPreDefinedDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/backupToWeb", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> backupScripts(@RequestHeader Map<String, String> header) {
        String sessionKey = header.get("sessionkey");

        if (sessionKey == null || sessionKey.isEmpty()) {
            return new ResponseEntity<>("Missing session key", HttpStatus.UNAUTHORIZED);
        }

        List<AnonymizedScriptDTO> anonymizedScripts = this.persistenceSaveService.anonymizeScriptsForBackup();

        if (!anonymizedScripts.isEmpty()) {
            ResponseEntity<String> response = this.persistenceSaveService.saveScriptsToWeb(sessionKey, anonymizedScripts);

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } else {
            return new ResponseEntity<>("No scripts to backup", HttpStatus.NO_CONTENT);
        }
    }
}
