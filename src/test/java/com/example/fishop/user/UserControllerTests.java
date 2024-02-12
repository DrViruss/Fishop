package com.example.fishop.user;

import com.example.fishop.dto.OrderDTO;
import com.example.fishop.dto.OrderItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
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
        ).andExpect(redirectedUrlTemplate("/user?emailNotValid"));

        mockMvc.perform( //doesn't work cause of sender is null
            post("/api/user/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf().asHeader())
                .param("email","validemail@email.com")
                .param("username","validname")
                .param("password","validPassword123")
                .param("zip","12345")
        ).andExpect(redirectedUrlTemplate("/"));
    }

    @Test
    void profileTest() throws Exception {
        this.mockMvc.perform(get("/user"))  //doesn't work cause of sender is null
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/user/defaultuser@admin.com")) //doesn't work cause of sender is null
            .andExpect(status().isForbidden());
    }

    @Test
    void cartTest() throws Exception {
        this.mockMvc.perform(get("/user/cart"))
            .andExpect(status().isOk());
    }
    
    @Test
    void postCartTest() throws Exception {
        List<Long> list = new ArrayList<>();
        list.add(-1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(list);

        mockMvc.perform(
            post("/api/user/getCartItems")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader())
                    .content(requestJson)
        ).andExpect(status().isOk()).andExpect(content().string("[]"));

        list.remove(-1L);
        list.add(402L);
        requestJson=ow.writeValueAsString(list);
        mockMvc.perform(
                post("/api/user/getCartItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader())
                        .content(requestJson)
        ).andExpect(status().isOk()).andExpect(content().string("[{\"id\":402,\"productname\":\"Yellow Sakura Shrimp\",\"speciename\":\"Shrimp\",\"productprice\":75.0,\"quantity\":10}]"));
    }

    @Test
    void postCreateOrderTest() throws Exception {
        List<OrderItemDTO> list = new ArrayList<>();
        list.add(new OrderItemDTO(99L,99));
        OrderDTO dto = new OrderDTO("validname@email.com", list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        mockMvc.perform(
            post("/api/user/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader())
                    .content(requestJson)
        ).andExpect(status().isForbidden());
    }

    @Test
    void OrderTest() throws Exception {
        this.mockMvc.perform(
            get("/user/order").param("id","99")
        ).andExpect(redirectedUrlTemplate("/"));
    }

    @Test
    void newUserTest() throws Exception {
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
        ).andExpect(status().isForbidden());
    }

}
