package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.Stream;

@RestController
public class InfoController {

    private final InfoService infoServiceImpl;
    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private Integer port;

    public InfoController(InfoService infoServiceImpl) {
        this.infoServiceImpl = infoServiceImpl;
    }

    @GetMapping("/port")
    public Integer getPortNumber() {
        return port;
    }

    @GetMapping("/sum")
    public Integer sum() {

        long time = System.currentTimeMillis();

        Stream.iterate(1, a -> a + 1).limit(1_000_000)
//                .parallel()
                .reduce(0, Integer::sum);
        time = System.currentTimeMillis() - time;
        logger.debug("time={}", time);
        return (int) time;
    }
}
