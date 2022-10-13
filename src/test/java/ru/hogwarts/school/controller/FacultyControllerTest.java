package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testStudents() throws Exception {
        final String name = "Ivanov Ivan";
        final String color = "green";
        final Long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testFindFacultiesByColor() throws Exception {
        final Long id1 = 1L;
        final String name1 = "Huffleup";

        final Long id2 = 2L;
        final String name2 = "Griffindor";

        final String color = "Yellow";

        Faculty faculty1 = new Faculty();
        faculty1.setId(id1);
        faculty1.setName(name1);
        faculty1.setColor(color);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color);

        when(facultyRepository.findAllByColor(color)).thenReturn((List<Faculty>) Set.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .queryParam("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void testFindFacultiesByColorOrNameIgnoreCase() throws Exception {
        final Long id1 = 1L;
        final String name1 = "Huffleup";
        final String color1 = "Yellow";
        final String color1IgnoreCase = "YeLLow";

        final Long id2 = 2L;
        final String name2 = "Griffindor";
        final String name2IgnoreCase = "GrIFFindor";
        final String color2 = "Red";

        Faculty faculty1 = new Faculty();
        faculty1.setId(id1);
        faculty1.setName(name1);
        faculty1.setColor(color1);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color2);

        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name2IgnoreCase, color1IgnoreCase)).thenReturn((List<Faculty>) Set.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .queryParam("name", name2IgnoreCase)
                        .queryParam("color", color1IgnoreCase)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void testAddFaculty() throws Exception {
        final String name = "Huffleup";
        final String color = "green";
        final Long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        final String oldName = "Huffleup";
        final String oldColor = "green";
        final Long id = 1L;

        final String newName = "Griffindor";
        final String newColor = "Blue";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", newName);
        facultyObject.put("color", newColor);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(oldName);
        faculty.setColor(oldColor);

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(id);
        updatedFaculty.setName(newName);
        updatedFaculty.setColor(newColor);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(newName))
                .andExpect(jsonPath("$[0].color").value(newColor));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        final String name = "Huffleup";
        final String color = "green";
        final Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.getById(id)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        verify(facultyRepository, atLeastOnce()).deleteById(id);
    }
}
