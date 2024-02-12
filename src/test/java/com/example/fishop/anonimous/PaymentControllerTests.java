package com.example.fishop.anonimous;

import com.example.fishop.dto.PaymentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
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
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void paymentTest() throws Exception {
        this.mockMvc.perform(get("/payment_TEST")
            .param("id","99")
            .param("email","valimail@email.com")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }

    @Test
    void successTest() throws Exception {
        this.mockMvc.perform(get("/success")
            .param("payment_intent","qwerty")
            .param("payment_intent_client_secret","qwerty")
            .param("redirect_status","qwerty")
        ).andExpect(redirectedUrlTemplate(TEST_LOGIN_PAGE));
    }
}
