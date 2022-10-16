package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    public StudentServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student) {
        LOG.info("Was invoked method for create student with body {}", student);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        LOG.info("Was invoked method for getting  student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        LOG.info("Was invoked method for update student with body {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        LOG.info("Was invoked method for delete student by id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        LOG.info("Was invoked method for find collection of students by age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    public Avatar findAvatar(long studentId) {
        LOG.info("Was invoked method for find Avatar");
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        LOG.info("Was invoked method for upload Avatar");
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
        LOG.info("Was invoked method for getting  collection of all students");
        return studentRepository.findAll();
    }

    @Override
    public Integer getCountOfAllStudents() {
        LOG.info("Was invoked method for get count of all students");
        return studentRepository.getCountOfAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        LOG.info("Was invoked method for get Average Age Of Students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        LOG.info("Was invoked method for get Last Five Students");
        return studentRepository.getLastFiveStudents();
    }

    public Set<Student> findByAgeBetween(int minAge, int maxAge) {
        LOG.info("Was invoked method for find collection of student with age ({} - {})", minAge, maxAge);
        return (Set<Student>) studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyOfStudent(Long id) {
        LOG.info("Was invoked method for getting faculty by studentId: {}", id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            return student.getFaculty();
        }
        return null;
    }

    @Override
    public List<String> getStudentsNameWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAvgAgeStudentStream() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

//    @Override
//    public void getNameStudentsThread() {
//        Stream<Student> streamStudent = studentRepository.findAll().stream();
//        List<String> studentsName = streamStudent
//                .map(a -> a.getName())
//                .collect(Collectors.toList());
//
//        System.out.println(studentsName);
//        System.out.println("////////////////////////////////");
//
//        System.out.println(studentsName.get(0));
//        System.out.println(studentsName.get(1));
//
//        new Thread(() ->
//        {
//            System.out.println(studentsName.get(2));
//            System.out.println(studentsName.get(3));
//        }).start();
//
//        new Thread(() ->
//        {
//            System.out.println(studentsName.get(4));
//            System.out.println(studentsName.get(5));
//        }).start();
//
//    }

//    @Override
//    public void getNameStudentsThreadSynch() {
//        Stream<Student> streamStudent = studentRepository.findAll().stream();
//        List<String> studentsName = streamStudent
//                .map(Student::getName)
//                .collect(Collectors.toList());
//
//        System.out.println(studentsName);
//        System.out.println("////////////////////////////////");
//
//        synchronized (studentsName) {
//
//            printStudent(studentsName.get(0));
//            printStudent(studentsName.get(1));
//
//            new Thread(() ->
//            {
//                printStudent(studentsName.get(2));
//                printStudent(studentsName.get(3));
//            }).start();
//
//            new Thread(() ->
//            {
//                printStudent(studentsName.get(4));
//                printStudent(studentsName.get(5));
//            }).start();
//        }
//    }
//
//    private void printStudent(String name) {
//        System.out.println(name);
//    }

    public void getNameStudentsThread() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).getContent();

        getNameStudentsThread(students.subList(0, 2));
        new Thread(() -> getNameStudentsThread(students.subList(2, 4))).start();
        new Thread(() -> getNameStudentsThread(students.subList(4, 6))).start();
    }

    private void getNameStudentsThread(List<Student> students) {
        for (Student student : students) {
            LOG.info(student.getName());
        }
    }

    private synchronized void getNameStudentsThreadSynch(List<Student> students) {
        for (Student student : students) {
            LOG.info(student.getName());
        }
    }

    public void getNameStudentsThreadSynch() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).getContent();

        getNameStudentsThreadSynch(students.subList(0, 2));
        new Thread(() -> getNameStudentsThreadSynch(students.subList(2, 4))).start();
        new Thread(() -> getNameStudentsThreadSynch(students.subList(4, 6))).start();

    }


}
