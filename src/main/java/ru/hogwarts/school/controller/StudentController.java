package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentServiceImpl;

    public StudentController(StudentService studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentServiceImpl.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping(params = {"age"})
    public ResponseEntity<Collection<Student>> findStudent(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentServiceImpl.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public Set<Student> findByAgeBetween(
            @RequestParam(required = false) int minAge,
            @RequestParam(required = false) int maxAge) {
        return studentServiceImpl.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> findFacultyByStudent(@PathVariable Long id) {
        Faculty faculty = studentServiceImpl.getFacultyOfStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/names/WithA")
    public ResponseEntity<List<String>> getStudentsNameWithA() {
        List<String> result = studentServiceImpl.getStudentsNameWithA();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/avgAgeStream")
    public ResponseEntity<Double> getAvgAgeStudentStream() {
        Double avgAge = studentServiceImpl.getAvgAgeStudentStream();
        if (avgAge == null) {
            ResponseEntity.notFound();
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/nameThread")
    public void getNameStudentsThread() {
        studentServiceImpl.getNameStudentsThread();
    }

    @GetMapping("/nameThreadSynch")
    public void getNameStudentsThreadSynch() {
        studentServiceImpl.getNameStudentsThreadSynch();
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentServiceImpl.editStudent(student);
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

}