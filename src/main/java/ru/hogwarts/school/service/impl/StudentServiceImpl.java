package ru.hogwarts.school.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    public StudentServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student with body {}", student);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for getting  student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for update student with body {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student by id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find collection of students by age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    public Avatar findAvatar(long studentId) {
        logger.info("Was invoked method for find Avatar");
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for upload Avatar");
        Student student = findStudent(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method for getting  collection of all students");
        return studentRepository.findAll();
    }

    @Override
    public Integer getCountOfAllStudents() {
        logger.info("Was invoked method for get count of all students");
        return studentRepository.getCountOfAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for get Average Age Of Students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get Last Five Students");
        return studentRepository.getLastFiveStudents();
    }

    public Set<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find collection of student with age ({} - {})", minAge, maxAge);
        return (Set<Student>) studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyOfStudent(Long id) {
        logger.info("Was invoked method for getting faculty by studentId: {}", id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            return student.getFaculty();
        }
        return null;
    }

    @Override
    public List<String> getStudentsNameWithA() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        return streamStudent
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toLowerCase)
                .map(StringUtils::capitalize)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAvgAgeStudentStream() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        Optional<Double> result = Optional.of(streamStudent
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble());
        return result.orElse(null);
    }
}
