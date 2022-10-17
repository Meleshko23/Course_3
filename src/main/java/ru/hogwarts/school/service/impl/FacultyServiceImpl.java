package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

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

    @Override
    public List<String> getLongestNameFaculties() {
        logger.info("Was invoked method for find faculty name with max length");

        Optional<String> maxFacultyName = facultyRepository
                .findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length));

        if (maxFacultyName.isEmpty()) {
            logger.error("There is no faculties at all");
            return (List<String>) ResponseEntity.notFound().build();
        } else {
            logger.debug("Faculty name with max length: {}", maxFacultyName.get());
            return (List<String>) ResponseEntity.ok(maxFacultyName.get());
        }
    }

    @Override
    public Integer sum() {
        return Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }

}



