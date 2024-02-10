package com.example.fishop.dto.response;

public class ResponsePaymentDTO {
    private String intentID;
    private String clientSecret;

    public ResponsePaymentDTO() {
    }

    public ResponsePaymentDTO(String intentID, String clientSecret) {
        this.intentID = intentID;
        this.clientSecret = clientSecret;
    }

    public String getIntentID() {
        return intentID;
    }

    public void setIntentID(String intentID) {
        this.intentID = intentID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
