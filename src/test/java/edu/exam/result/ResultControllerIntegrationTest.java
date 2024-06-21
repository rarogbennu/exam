package edu.exam.result;

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
public class ResultControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateResult() throws Exception {
        String resultJson = "{\"participant\":{\"id\":1},\"discipline\":{\"id\":1},\"resultValue\":9.58,\"date\":\"2023-06-20\",\"resultType\":\"TIME\"}";

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultValue").value(9.58))
                .andExpect(jsonPath("$.resultType").value("TIME"));
    }

    @Test
    public void testGetResultById() throws Exception {
        String resultJson = "{\"participant\":{\"id\":1},\"discipline\":{\"id\":1},\"resultValue\":8.95,\"date\":\"2023-06-20\",\"resultType\":\"DISTANCE\"}";

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/results/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(8.95))
                .andExpect(jsonPath("$.resultType").value("DISTANCE"));
    }

    @Test
    public void testUpdateResult() throws Exception {
        String resultJson = "{\"participant\":{\"id\":1},\"discipline\":{\"id\":1},\"resultValue\":9.58,\"date\":\"2023-06-20\",\"resultType\":\"TIME\"}";
        String updatedResultJson = "{\"participant\":{\"id\":1},\"discipline\":{\"id\":1},\"resultValue\":9.63,\"date\":\"2023-06-20\",\"resultType\":\"TIME\"}";

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/results/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedResultJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(9.63));
    }

    @Test
    public void testDeleteResult() throws Exception {
        String resultJson = "{\"participant\":{\"id\":1},\"discipline\":{\"id\":1},\"resultValue\":9.58,\"date\":\"2023-06-20\",\"resultType\":\"TIME\"}";

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/results/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllResults() throws Exception {
        mockMvc.perform(get("/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}