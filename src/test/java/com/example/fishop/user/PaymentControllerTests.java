package com.example.fishop.user;

import com.example.fishop.dto.PaymentDTO;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class PaymentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void postNewIntentTest() throws Exception {

        PaymentDTO dto = new PaymentDTO(-1L,99,"valimail@email.com","validproductName");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        mockMvc.perform(
            post("/create-payment-intent")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader())
                .content(requestJson)
        ).andExpect(status().isForbidden()).andExpect(content().string("")); //.andExpect(status().isBadRequest());
    }

    @Test
    void postPaymentTest() throws Exception {
        mockMvc.perform(
            post("/payment_TEST")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .with(csrf().asHeader())
                    .param("id","-1")
                    .param("price","999")
                    .param("email","valimail@email.com")
                    .param("productName","validproductName")
        ).andExpect(redirectedUrlTemplate("/payment_TEST?error"))
        .andExpect(status().is(302));
    }

    @Test
    void paymentTest() throws Exception {
        this.mockMvc.perform(get("/payment_TEST")
            .param("id","99")
            .param("email","valimail@email.com")
        ).andExpect(redirectedUrlTemplate("/user/cart"));
    }

    @Test
    void successTest() throws Exception {
        this.mockMvc.perform(get("/success")
            .param("payment_intent","qwerty")
            .param("payment_intent_client_secret","qwerty")
            .param("redirect_status","qwerty")
        ).andExpect(redirectedUrlTemplate("/"));
    }
}
