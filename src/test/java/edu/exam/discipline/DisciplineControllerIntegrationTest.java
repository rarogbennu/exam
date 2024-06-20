package edu.exam.discipline;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DisciplineControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateDiscipline() throws Exception {
        String disciplineJson = "{\"name\":\"100m\",\"resultType\":\"Time\"}";

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(disciplineJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("100m"))
                .andExpect(jsonPath("$.resultType").value("Time"));
    }

    @Test
    public void testGetDisciplineById() throws Exception {
        String disciplineJson = "{\"name\":\"Long Jump\",\"resultType\":\"Distance\"}";

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(disciplineJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Long Jump"))
                .andExpect(jsonPath("$.resultType").value("Distance"));
    }

    @Test
    public void testUpdateDiscipline() throws Exception {
        String disciplineJson = "{\"name\":\"High Jump\",\"resultType\":\"Height\"}";
        String updatedDisciplineJson = "{\"name\":\"High Jump\",\"resultType\":\"Distance\"}";

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(disciplineJson))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDisciplineJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("High Jump"))
                .andExpect(jsonPath("$.resultType").value("Distance"));
    }

    @Test
    public void testDeleteDiscipline() throws Exception {
        String disciplineJson = "{\"name\":\"Shot Put\",\"resultType\":\"Distance\"}";

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(disciplineJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllDisciplines() throws Exception {
        mockMvc.perform(get("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}