package com.example.fishop.anonimous;

import com.example.fishop.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.fishop.anonimous.DefaultControllerTests.TEST_LOGIN_PAGE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void productEditTest() throws Exception{
        this.mockMvc.perform(get("/all/406/edit"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        this.mockMvc.perform(get("/all/999/edit"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void removeTest() throws Exception {
        mockMvc.perform(
            post("/api/all/remove")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productId","999")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        mockMvc.perform(
            post("/api/all/remove")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productId","402")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void postProductEditTest() throws Exception {
        mockMvc.perform(
            post("/api/all/editProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        mockMvc.perform(
            post("/api/all/editProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productDTO",
                    new ProductDTO(
                        999L,
                        "TestProdName",
                        "TestProdDesc",
                        "TestSpecie",
                        99,
                        99,
                        99)
                    .toString()
                )
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        mockMvc.perform(
            post("/api/all/editProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productDTO",
                    new ProductDTO(
                        402L,
                        "TestProdName",
                        "TestProdDesc",
                        "TestSpecie",
                        99,
                        99,
                        99)
                        .toString()
                )
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

    }

    @Test
    void postProductAddTest() throws Exception {
        mockMvc.perform(
            post("/api/all/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        mockMvc.perform(
            post("/api/all/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productDTO",
                    new ProductDTO(
                        999L,
                        "TestProdName",
                        "TestProdDesc",
                        "TestSpecie",
                        99,
                        99,
                        99)
                        .toString()
                )
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        mockMvc.perform(
            post("/api/all/addProduct")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("productDTO",
                    new ProductDTO(
                        402L,
                        "TestProdName",
                        "TestProdDesc",
                        "TestSpecie",
                        99,
                        99,
                        99)
                        .toString()
                )
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

    }


}
