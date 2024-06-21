package edu.exam.participant;

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
public class ParticipantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateParticipant() throws Exception {
        String participantJson = "{\"name\":\"John Doe\",\"gender\":\"MALE\",\"birthdate\":\"2000-01-01\",\"team\":\"Team A\",\"disciplines\":[]}";

        mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.birthdate").value("2000-01-01"))
                .andExpect(jsonPath("$.team").value("Team A"))
                .andExpect(jsonPath("$.disciplines").isArray());
    }

    @Test
    public void testGetParticipantById() throws Exception {
        String participantJson = "{\"name\":\"Jane Doe\",\"gender\":\"FEMALE\",\"birthdate\":\"1995-05-15\",\"team\":\"Team B\",\"disciplines\":[]}";

        mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/participants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.gender").value("FEMALE"))
                .andExpect(jsonPath("$.birthdate").value("1995-05-15"))
                .andExpect(jsonPath("$.team").value("Team B"))
                .andExpect(jsonPath("$.disciplines").isArray());
    }

    @Test
    public void testUpdateParticipant() throws Exception {
        String participantJson = "{\"name\":\"John Doe\",\"gender\":\"MALE\",\"birthdate\":\"2000-01-01\",\"team\":\"Team A\",\"disciplines\":[]}";
        String updatedParticipantJson = "{\"name\":\"John Smith\",\"gender\":\"MALE\",\"birthdate\":\"2000-01-01\",\"team\":\"Team A\",\"disciplines\":[]}";

        mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/participants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedParticipantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.birthdate").value("2000-01-01"))
                .andExpect(jsonPath("$.team").value("Team A"))
                .andExpect(jsonPath("$.disciplines").isArray());
    }

    @Test
    public void testDeleteParticipant() throws Exception {
        String participantJson = "{\"name\":\"John Doe\",\"gender\":\"MALE\",\"birthdate\":\"2000-01-01\",\"team\":\"Team A\",\"disciplines\":[]}";

        mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/participants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllParticipants() throws Exception {
        mockMvc.perform(get("/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}