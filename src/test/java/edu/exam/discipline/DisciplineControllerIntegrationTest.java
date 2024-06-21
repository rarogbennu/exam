package edu.exam.discipline;

import edu.exam.result.ResultType;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private DisciplineRepository disciplineRepository;

    @BeforeEach
    public void setup() {
        disciplineRepository.deleteAll();
    }

    @Test
    public void testCreateDiscipline() throws Exception {
        String disciplineJson = "{\"disciplineName\":\"100 meter\",\"resultType\":\"TIME\"}";

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(disciplineJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.disciplineName").value("100 meter"))
                .andExpect(jsonPath("$.resultType").value("TIME"));
    }

    @Test
    public void testGetDisciplineById() throws Exception {
        Discipline discipline = new Discipline("Længdespring", ResultType.DISTANCE);
        discipline = disciplineRepository.save(discipline);

        mockMvc.perform(get("/disciplines/" + discipline.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disciplineName").value("Længdespring"))
                .andExpect(jsonPath("$.resultType").value("DISTANCE"));
    }

    @Test
    public void testUpdateDiscipline() throws Exception {
        Discipline discipline = new Discipline("Højdespring", ResultType.POINTS);
        discipline = disciplineRepository.save(discipline);

        String updatedDisciplineJson = "{\"disciplineName\":\"Højdespring\",\"resultType\":\"DISTANCE\"}";

        mockMvc.perform(put("/disciplines/" + discipline.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDisciplineJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disciplineName").value("Højdespring"))
                .andExpect(jsonPath("$.resultType").value("DISTANCE"));
    }

    @Test
    public void testDeleteDiscipline() throws Exception {
        Discipline discipline = new Discipline("Kuglestød", ResultType.DISTANCE);
        discipline = disciplineRepository.save(discipline);

        mockMvc.perform(delete("/disciplines/" + discipline.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllDisciplines() throws Exception {
        Discipline discipline1 = new Discipline("100 meter", ResultType.TIME);
        Discipline discipline2 = new Discipline("Længdespring", ResultType.DISTANCE);
        disciplineRepository.save(discipline1);
        disciplineRepository.save(discipline2);

        mockMvc.perform(get("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].disciplineName").value("100 meter"))
                .andExpect(jsonPath("$[0].resultType").value("TIME"))
                .andExpect(jsonPath("$[1].disciplineName").value("Længdespring"))
                .andExpect(jsonPath("$[1].resultType").value("DISTANCE"));
    }
}