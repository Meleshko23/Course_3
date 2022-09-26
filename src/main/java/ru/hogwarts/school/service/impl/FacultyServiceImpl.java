package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        logger.info("Was invoked method to create faculty");
        if (faculty == null) {
            logger.error("Can't create faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        logger.debug("Was created {}", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> getFacultyById(Long id) {
        logger.info("Was invoked method to find faculty by id={}", id);
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isEmpty()) {
            logger.error("There is no faculty with id={}", id);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Faculty was founder by id={}", id);
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        if (faculty == null) {
            logger.error("Can't update faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            logger.error("There is no faculty with your id");
            return ResponseEntity.notFound().build();
        }
        logger.debug("{} was updated", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> removeFaculty(Long id) {
        logger.info("Was invoked method to delete faculty");
        facultyRepository.deleteById(id);
        logger.debug("Faculty was deleted by id={}", id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Faculty>> getFacultiesByColor(String color) {
        logger.info("Was invoked method to find faculty by color");
        List<Faculty> facultyList = facultyRepository.findAllByColor(color);
        if (facultyList.isEmpty()) {
            logger.error("There is no faculty with color", color);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Faculty was founder by color", color);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Collection<Faculty>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<Faculty>> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String str) {
        return null;
    }

    @Override
    public ResponseEntity<String> getFacultyNameWithMaxLength() {
        return null;
    }
}



