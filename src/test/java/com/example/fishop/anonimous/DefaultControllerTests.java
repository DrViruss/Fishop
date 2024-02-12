package com.example.fishop.anonimous;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultControllerTests {
    
    public static final String TEST_LOGIN_PAGE = "http://localhost/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
            .andExpect(content().string(containsString("Glowlight Tetra")));
    }

    @Test
    void errorTest() throws Exception {
        this.mockMvc.perform(get("/nonexistpage")).andExpect(status().is(302))
                .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE)); //.andExpect(content().string(containsString("Oops!")));
    }

    @Test
    void allTest() throws Exception {
        this.mockMvc.perform(get("/all")).andExpect(status().isOk())
            .andExpect(content().string(containsString("Yellow Sakura Shrimp")));
    }

    @Test
    void aboutTest() throws Exception {
        this.mockMvc.perform(get("/about")).andExpect(status().isOk())
            .andExpect(content().string(containsString("Who we are?")));
    }


    @Test
    void searchTest() throws Exception{
        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of csrf
        ).andExpect(status().isForbidden());

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of csrf
                .with(csrf().asHeader().useInvalidToken())
        ).andExpect(status().isForbidden());

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of params
                .with(csrf().asHeader())
        ).andExpect(status().isBadRequest());

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of params
                .with(csrf().asHeader())
                .requestAttr("text","smth")
        ).andDo(print()).andExpect(content().string(""));

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of absence in the database
                .with(csrf().asHeader())
                .param("text","smth")
                .param("maxPrice","9999")
        ).andExpect(content().string(not(containsString("card h-100"))));

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of absence maxPrice
                .with(csrf().asHeader())
                .param("text","Thailand blue guppy")
        ).andExpect(status().isBadRequest());

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("minPrice","Thailand blue guppy") //cause of validation
        ).andExpect(status().isBadRequest());

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of presence in the database
                .with(csrf().asHeader())
                .param("text","Thailand blue guppy")
                .param("maxPrice","9999")
        ).andExpect(content().string(containsString("Thailand blue guppy")));

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of presence in the database
                .with(csrf().asHeader())
                .param("speciename","Shrimp")
                .param("maxPrice","9999")
        ).andExpect(content().string(containsString("Yellow Sakura Shrimp")))
        .andExpect(content().string(containsString("Orange Sakura Shrimp")));

        mockMvc.perform(
            post("/all/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //cause of presence in the database
                .with(csrf().asHeader())
                .param("minPrice","120")
                .param("maxPrice","9999")
        ).andExpect(content().string(containsString("Thailand blue guppy")))
        .andExpect(content().string(containsString("Emperor Tetra")));

    }

    @Test
    void productTest() throws Exception {
        this.mockMvc.perform(get("/all/406")).andExpect(status().isOk())
            .andExpect(content().string(containsString("Thailand blue guppy")))
            .andExpect(content().string(not(containsString("Remove"))))
            .andExpect(content().string(not(containsString("Add to cart"))));

        this.mockMvc.perform(get("/all/999"))
            .andExpect(redirectedUrlTemplate("/"));
    }

}
