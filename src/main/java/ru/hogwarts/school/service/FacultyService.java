package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAll();

    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);


//    String getFacultyNameWithMaxLength();

}
