package com.example.fishop.anonimous;

import com.example.fishop.dto.OrderDTO;
import com.example.fishop.dto.OrderItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.fishop.anonimous.DefaultControllerTests.TEST_LOGIN_PAGE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateTest() throws Exception {
        mockMvc.perform(
            post("/api/user/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                    .param("email","unvalidemail")
                    .param("username","validusername")
                    .param("password","unvalidpassword")
                    .param("zip","unvalidzip")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE)); //.andExpect(status().isBadRequest());
    }

    @Test
    void profileTest() throws Exception {
        this.mockMvc.perform(get("/user"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));

        this.mockMvc.perform(get("/user/defaultuser@admin.com"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void cartTest() throws Exception {
        this.mockMvc.perform(get("/user/cart"))
            .andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }
    
    @Test
    void postCartTest() throws Exception {
        mockMvc.perform(
            post("/api/user/getCartItems")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("items","[1]")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void postCreateOrderTest() throws Exception {
        List<OrderItemDTO> list = new ArrayList<>();
        list.add(new OrderItemDTO(99L,99));
        mockMvc.perform(
            post("/api/user/createOrder")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("items",new OrderDTO("validname@email.com", list)
                    .toString())
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void OrderTest() throws Exception {
        this.mockMvc.perform(
            get("/user/order").param("id","99")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void newUserTest() throws Exception {
        mockMvc.perform(
            post("/api/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                    .param("email","unvalidemail")
                    .param("username","validusername")
                    .param("password","unvalidpassword")
                    .param("zip","unvalidzip")
                    .param("country","Country")
                    .param("state","State")
        ).andExpect(redirectedUrl("/register?emailNotValid"));

        mockMvc.perform(
            post("/api/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("email","validemail@email.com")
                .param("username","validname")
                .param("password","validPassword123")
                .param("zip","unvalidzip")
                .param("country","Country")
                .param("state","State")
        ).andExpect(redirectedUrl("/register?zipNotValid"));

        mockMvc.perform(
            post("/api/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("email","validemail@email.com")
                .param("username","validname")
                .param("password","validPassword123")
                .param("zip","12345")
                .param("country","Country")
                .param("state","State")
        ).andExpect(redirectedUrl("/login"));


        mockMvc.perform(
            post("/api/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("email","validemail@email.com")
                .param("username","validname")
                .param("password","validPassword123")
                .param("zip","12345")
                .param("country","Country")
                .param("state","State")
        ).andExpect(redirectedUrl("/register?emailexist"));
    }

}
