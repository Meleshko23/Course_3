package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {

        return facultyServiceImpl.getFacultyById(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll() {
        return facultyServiceImpl.getAll();
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFaculty(@PathVariable Long id) {
        facultyServiceImpl.removeFaculty(id);
        return ResponseEntity.ok().build();
    }
}
