package com.example.fishop.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void adminOrdersTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel"))
                .andDo(print()).andExpect(status().isForbidden());

        this.mockMvc.perform(get("/adminpanel/orders"))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminProductNewTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/products/new"))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminUsersTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/users"))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminProductsTest() throws Exception{
        this.mockMvc.perform(get("/adminpanel/products"))
            .andExpect(status().isForbidden());
    }

}
