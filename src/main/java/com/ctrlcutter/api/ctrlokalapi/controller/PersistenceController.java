package com.ctrlcutter.api.ctrlokalapi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlcutter.backend.dto.AnonymizedScriptDTO;
import com.ctrlcutter.backend.dto.BasicAndPreDefinedDTO;
import com.ctrlcutter.backend.dto.BasicScriptDTO;
import com.ctrlcutter.backend.dto.ShortcutDTO;
import com.ctrlcutter.backend.persistence.model.BasicHotstringScript;
import com.ctrlcutter.backend.persistence.model.BasicScript;
import com.ctrlcutter.backend.persistence.model.PreDefinedScript;
import com.ctrlcutter.backend.persistence.service.PersistenceReceiveService;
import com.ctrlcutter.backend.persistence.service.PersistenceSaveService;
import com.ctrlcutter.backend.util.ScriptToDTOMapper;

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
    public ResponseEntity<String> backupScripts(@RequestHeader Map<String, String> header, @RequestParam() boolean saveAll) {
        String sessionKey = header.get("sessionkey");

        if (sessionKey == null || sessionKey.isEmpty()) {
            return new ResponseEntity<>("Missing session key", HttpStatus.UNAUTHORIZED);
        }

        List<AnonymizedScriptDTO> anonymizedScripts = this.persistenceSaveService.anonymizeScriptsForBackup(saveAll);

        if (!anonymizedScripts.isEmpty()) {
            ResponseEntity<String> response = this.persistenceSaveService.saveScriptsToWeb(sessionKey, anonymizedScripts);

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } else {
            return new ResponseEntity<>("No scripts to backup", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/retrieveFromWeb", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> retrieveScripts(@RequestHeader Map<String, String> header) {
        String sessionKey = header.get("sessionkey");

        if (sessionKey == null || sessionKey.isEmpty()) {
            return new ResponseEntity<>("Missing session key", HttpStatus.UNAUTHORIZED);
        }

        List<ShortcutDTO> anonymizedScripts = this.persistenceSaveService.retrieveScriptsFromWeb(sessionKey);
        ScriptToDTOMapper scriptToDTOMapper = new ScriptToDTOMapper();
        List<BasicScriptDTO> basicScriptDTOs = anonymizedScripts.stream().map(scriptToDTOMapper::mapBasicScriptDTO).collect(Collectors.toList());

        this.persistenceSaveService.deleteAll();
        basicScriptDTOs.forEach(this.persistenceSaveService::saveBasicScript);

        return new ResponseEntity<>("Backup Successful!", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/deleteBasic")
    public void deleteBasicScript(@RequestParam() long id) {
        this.persistenceSaveService.deleteBasicScript(id);
    }

    @DeleteMapping(value = "/deleteHotstring")
    public void deleteHotstring(@RequestParam() long id) {
        this.persistenceSaveService.deleteHotstring(id);
    }

    @DeleteMapping(value = "/deletePredefined")
    public void deletePredefined(@RequestParam() long id) {
        this.persistenceSaveService.deletePredefinedScript(id);
    }
}
