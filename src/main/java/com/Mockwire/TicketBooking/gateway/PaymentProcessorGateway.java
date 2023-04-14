package com.Mockwire.TicketBooking.gateway;


import com.Mockwire.TicketBooking.Entity.FraudCheckResponse;
import com.Mockwire.TicketBooking.Entity.PaymentRequest;
import com.Mockwire.TicketBooking.Entity.PaymentResponse;

import java.time.LocalDate;

public class PaymentProcessorGateway {

    private final PaymentProcessorRestTemplate restTemplate = new PaymentProcessorRestTemplate();
    private final String baseUrl;

    public PaymentProcessorGateway(final String host, final int port) {
        this.baseUrl = "http://" + host + ":" + port;
    }

    public PaymentResponse makePayment(String creditCardNumber, LocalDate creditCardExpiry, Double amount) {
        final PaymentRequest request = new PaymentRequest(creditCardNumber, creditCardExpiry, amount);
        return restTemplate.postForObject(baseUrl + "/payments", request, PaymentResponse.class);
    }

    public void updatePayment(String bookingId) {
        restTemplate.postForObject(baseUrl + "/update", bookingId, Void.class);
    }

    public FraudCheckResponse fraudCheck(String cardNumber) {
        return restTemplate.getForObject(baseUrl + "/fraudCheck/"+cardNumber, FraudCheckResponse.class);
    }
}
