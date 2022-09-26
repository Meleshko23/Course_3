package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {

    ResponseEntity<Faculty> createFaculty(Faculty faculty);

    ResponseEntity<Faculty> getFacultyById(Long id);

    ResponseEntity<Faculty> updateFaculty(Faculty faculty);

    ResponseEntity<Faculty> removeFaculty(Long id);

    ResponseEntity<List<Faculty>> getFacultiesByColor(String color);

    ResponseEntity<Collection<Faculty>> getAll();

    ResponseEntity<List<Faculty>> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String str);

    ResponseEntity<String> getFacultyNameWithMaxLength();

}
