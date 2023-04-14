package com.Mockwire.TicketBooking;

import com.Mockwire.TicketBooking.Entity.TicketBookingPaymentRequest;
import com.Mockwire.TicketBooking.Entity.TicketBookingResponse;
import com.Mockwire.TicketBooking.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketBookingController {

    private final TicketBookingService ticketBookingService;

    @Autowired
    public TicketBookingController(TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }

    @PostMapping
    public TicketBookingResponse bookTicket(@RequestBody TicketBookingPaymentRequest bookingPayment)
    {
        return ticketBookingService.payForBooking(bookingPayment);
    }


}
