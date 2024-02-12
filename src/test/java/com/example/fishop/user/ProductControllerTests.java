package com.example.fishop.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void productEditTest() throws Exception{
        this.mockMvc.perform(get("/all/406/edit"))
            .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/all/999/edit"))
            .andExpect(status().isForbidden());
    }

    @Test
    void removeTest() throws Exception {
        mockMvc.perform(
            post("/api/all/remove")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productId","999")
        ).andExpect(status().isForbidden());

        mockMvc.perform(
            post("/api/all/remove")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productId","402")
        ).andExpect(status().isForbidden());
    }

    @Test
    void postProductEditTest() throws Exception {
        mockMvc.perform(
            post("/api/all/editProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                    .param("id","999")
                    .param("name","TestProdName")
                    .param("description","TestProdDesc")
                    .param("specie","TestSpecie")
                    .param("defaultprice","99")
                    .param("quantity","99")
                    .param("discount","99")
        ).andExpect(status().isForbidden());

        mockMvc.perform(
            post("/api/all/editProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                    .param("id","999")
                    .param("name","TestProdName")
                    .param("description","TestProdDesc")
                    .param("specie","TestSpecie")
                    .param("defaultprice","99")
                    .param("quantity","99")
                    .param("discount","99")
        ).andExpect(status().isForbidden());

    }

    @Test
    void postProductAddTest() throws Exception {
        mockMvc.perform(
            post("/api/all/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                    .param("id","999")
                    .param("name","TestProdName")
                    .param("description","TestProdDesc")
                    .param("specie","TestSpecie")
                    .param("defaultprice","99")
                    .param("quantity","99")
                    .param("discount","99")
        ).andExpect(status().isForbidden());
    }
}
