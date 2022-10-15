package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {

    private final InfoService infoServiceImpl;

    public InfoController(InfoService infoServiceImpl) {
        this.infoServiceImpl = infoServiceImpl;
    }

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPortNumber() {
        Integer port = infoServiceImpl.getPort();
        return ResponseEntity.ok(port);
    }
}
