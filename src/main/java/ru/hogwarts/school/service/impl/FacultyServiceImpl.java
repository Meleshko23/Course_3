package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Was created {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method to find faculty by id={}", id);
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isEmpty()) {
            logger.error("There is no faculty with id={}", id);
            return facultyRepository.findById(id).orElse(null);
        }
        logger.debug("Faculty was founder by id={}", id);
        return byId.get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            logger.error("There is no faculty with your id");
            return facultyRepository.findById(faculty.getId()).orElse(null);
        }
        logger.debug("{} was updated", faculty);
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.info("Was invoked method to delete faculty");
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }

//    @Override
//    public String getFacultyNameWithMaxLength() {
//        return null;
//    }
}



