package com.example.fishop.anonimous;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.fishop.anonimous.DefaultControllerTests.TEST_LOGIN_PAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void adminOrdersTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        this.mockMvc.perform(get("/adminpanel/orders"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void adminProductNewTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/products/new"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void adminUsersTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/users"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void adminProductsTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/products"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

}
