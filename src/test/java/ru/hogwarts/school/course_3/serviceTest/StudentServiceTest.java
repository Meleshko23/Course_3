//package ru.hogwarts.school.course_3.serviceTest;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.service.StudentService;
//
//public class StudentServiceTest {
//
//    private final StudentService studentService = new StudentService();
//
//    @Test
//    public void addEditStudentTest() {
//        Student student1 = new Student(0, "Petr", 15);
//        Student actual = studentService.addStudent(student1);
//        Student expected = new Student(0, "Petr", 15);
//        Assertions.assertEquals(actual, expected);
//
//        studentService.editStudent(1, student1);
//        Assertions.assertEquals(actual, expected);
//
////        studentService.findStudent(1);
//    }
//
//    @Test
//    public void deleteStudentTest() {
//        studentService.deleteStudent(1);
//    }
//
//    @Test
//    public void findByAgeTest() {
//        studentService.findByAge(20);
//    }
//
//}
