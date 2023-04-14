package com.Mockwire.TicketBooking.Entity;

import java.time.LocalDate;

public class PaymentRequest {
    private final String cardNumber;
    private final LocalDate cardExpiryDate;
    private final Double amount;

    public PaymentRequest(String cardNumber, LocalDate cardExpiryDate, Double amount) {

        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getCardExpiryDate() {
        return cardExpiryDate;
    }

    public Double getAmount() {
        return amount;
    }
}
