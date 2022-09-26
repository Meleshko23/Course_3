package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentServiceImpl.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudent(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentServiceImpl.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

//    @GetMapping
//    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) int age,
//                                                                @RequestParam(required = false) int minAge,
//                                                                @RequestParam(required = false) int maxAge) {
//
//        if (age >= minAge) {
//            return ResponseEntity.ok(studentServiceImpl.findByAge(age));
//        }
//        if (age <= maxAge) {
//            return ResponseEntity.ok(studentServiceImpl.findByAge(age));
//        }
//        return ResponseEntity.ok(Collections.emptyList());
//    }

//    @GetMapping("faculty")
//    public ResponseEntity<Faculty> findFacultyByStudent(@RequestParam(required = false) Long id) {
//        Faculty faculty = studentServiceImpl.getFacultyOfStudent(id);
//        if (faculty == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(faculty);
//    }


    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentServiceImpl.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}