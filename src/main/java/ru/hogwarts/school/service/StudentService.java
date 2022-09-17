package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public ArrayList<Student> findByAgeBetween(int minAge, int maxAge) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() >= minAge && student.getAge() <= maxAge) {
                result.add(student);
            }
        }
        return result;
    }

    public Faculty getFacultyOfStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        if (student != null) {
            return student.getFaculty();
        }
        return null;
    }
}
