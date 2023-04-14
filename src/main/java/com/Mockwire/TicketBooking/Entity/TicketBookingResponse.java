package com.Mockwire.TicketBooking.Entity;

public class TicketBookingResponse {
    private final String bookingId;
    private final String paymentId;
    private final BookingResponseStatus bookingResponseStatus;

    public TicketBookingResponse(String bookingId, String paymentId, BookingResponseStatus bookingResponseStatus) {
        this.bookingId = bookingId;
        this.paymentId = paymentId;
        this.bookingResponseStatus = bookingResponseStatus;
    }

    public enum BookingResponseStatus {
        SUCCESS, REJECTED
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public BookingResponseStatus getBookingResponseStatus() {
        return bookingResponseStatus;
    }

    @Override
    public String toString() {
        return "TicketBookingResponse{" +
                "bookingId='" + bookingId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", bookingResponseStatus=" + bookingResponseStatus +
                '}';
    }
}
