package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty with body {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method to find faculty by id={}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.info("Was invoked method to delete faculty");
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for getting all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String nameOrColor) {
        logger.info("Was invoked method for find faculties by color or name: {}", nameOrColor);
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(nameOrColor, nameOrColor);
    }

//    @Override
//    public String getFacultyNameWithMaxLength() {
//        return null;
//    }
}



