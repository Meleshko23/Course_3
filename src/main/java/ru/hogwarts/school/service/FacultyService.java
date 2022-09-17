package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }

    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color) || Objects.equals(faculty.getName(), name)) {
                result.add(faculty);
            }
        }
        return result;
    }

//    public Collection<Faculty> findByStudentInFaculty(Long id) {
//        ArrayList<Faculty> result = new ArrayList<>();
//        for (Faculty faculty : facultyRepository.findAll()) {
//            if (Objects.equals(faculty.getColor(), color)) {
//                result.add(faculty);
//            }
//        }
//        return result;
//    }

    public Faculty getStudentInFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).get();
        if (faculty != null) {
            return faculty.getStudents();
        }
        return null;
    }

}
