package ru.hogwarts.school.service;


import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentService {

    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> findByAge(int age);

    Avatar findAvatar(long studentId);

    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Collection<Student> getAll();

    Integer getCountOfAllStudents();

    Double getAverageAgeOfStudents();

    Collection<Student> getLastFiveStudents();

    Set<Student> findByAgeBetween(int minAge, int maxAge);

    Faculty getFacultyOfStudent(Long id);

    List<String> getStudentsNameWithA();

    Double getAvgAgeStudentStream();

    void getNameStudentsThread();

    void getNameStudentsThreadSynch();

//    Faculty getFacultyOfStudent(Long id);
}
