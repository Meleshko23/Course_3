package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyServiceImpl;

    public FacultyController(FacultyService facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyServiceImpl.getFacultyById(id);
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyServiceImpl.getAll();
    }

    @GetMapping(params = "nameOrColor")
    public ResponseEntity<Collection<Faculty>> findFacultiesNameOrColor(@RequestParam String nameOrColor) {
        Collection<Faculty> fa = facultyServiceImpl.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(nameOrColor);
        return ResponseEntity.ok(fa);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Faculty>> findByStudentsFaculty(@RequestParam(required = false) @PathVariable Long id) {
        Faculty student = facultyServiceImpl.getFacultyById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(List.of(student));
    }

    @GetMapping("/name/maxLength")
    public ResponseEntity<List<String>> getLongestNameFaculties() {
        List<String> result = facultyServiceImpl.getLongestNameFaculties();
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/sum")
    public ResponseEntity<Integer> sum() {
        Integer sum = facultyServiceImpl.sum();
        return ResponseEntity.ok(sum);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFaculty(@PathVariable Long id) {
        facultyServiceImpl.removeFaculty(id);
        return ResponseEntity.ok().build();
    }
}
