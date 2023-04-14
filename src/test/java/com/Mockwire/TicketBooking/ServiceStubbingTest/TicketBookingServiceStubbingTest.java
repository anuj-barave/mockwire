package com.Mockwire.TicketBooking.ServiceStubbingTest;

import com.Mockwire.TicketBooking.Entity.CardDetails;
import com.Mockwire.TicketBooking.Entity.PaymentUpdateResponse;
import com.Mockwire.TicketBooking.Entity.TicketBookingPaymentRequest;
import com.Mockwire.TicketBooking.Entity.TicketBookingResponse;
import com.Mockwire.TicketBooking.TicketBookingController;
import com.Mockwire.TicketBooking.gateway.PaymentProcessorGateway;
import com.Mockwire.TicketBooking.service.TicketBookingService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.Mockwire.TicketBooking.Entity.TicketBookingResponse.BookingResponseStatus.SUCCESS;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class TicketBookingServiceStubbingTest {

    private TicketBookingService ticketBookingService;

    private TicketBookingController ticketBookingController;

    private WireMockServer wireMockRule;

    @BeforeEach
    void setup()
    {
        wireMockRule = new WireMockServer();
        configureFor("localhost", 8080);
        wireMockRule.start();
        PaymentProcessorGateway paymentProcessorGateway = new PaymentProcessorGateway("localhost", wireMockRule.port());
        ticketBookingService = new TicketBookingService(paymentProcessorGateway);
    }

    @Test
    void testcase1()
    {
        stubFor(any(anyUrl()).willReturn(ok()));

        TicketBookingPaymentRequest tbqr = new TicketBookingPaymentRequest("1111",2000D,new CardDetails("1111-1111-1111",LocalDate.now()));

        final PaymentUpdateResponse paymentUpdateResponse = ticketBookingService.updatePaymentDetails(tbqr);

        Assertions.assertThat(paymentUpdateResponse.getStatus()).isEqualTo("SUCCESS");
        System.out.println(LocalDate.now());

    }

    @Test
    void testcase2()
    {
        stubFor(post(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                        "\"cardNumber\" : \"1111-1111-1111-1111\",\n" +
                        "\"cardExpiryDate\" : \"2020-08-03\",\n" +
                        "\"amount\" : 200.0\n" +
                        "}"))
                .willReturn(okJson("{\n" +
                        "\"paymentId\" : \"3333\",\n" +
                        "\"paymentResponseStatus\" : \"SUCCESS\"\n" +
                        "}")));

        //when
        TicketBookingPaymentRequest tbpr = new TicketBookingPaymentRequest("1111",
                200.0,
                new CardDetails("1111-1111-1111-1111", LocalDate.of(2020,8,3)));

        TicketBookingResponse response = ticketBookingService.payForBooking(tbpr);
        TicketBookingResponse expectedResponse = new TicketBookingResponse("1111", "3333", SUCCESS);

        Assertions.assertThat(response.toString().trim()).isEqualTo(expectedResponse.toString().trim());
    }

    @Test
    void testcase3()
    {
        stubFor(post(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                        "\"cardNumber\" : \"1111-1111-1111-1111\",\n" +
                        "\"cardExpiryDate\" : \"2020-08-03\",\n" +
                        "\"amount\" : 200.0\n" +
                        "}"))
                .willReturn(okJson("{\n" +
                        "\"paymentId\" : \"3333\",\n" +
                        "\"paymentResponseStatus\" : \"SUCCESS\"\n" +
                        "}")));

        //when
        TicketBookingPaymentRequest tbpr = new TicketBookingPaymentRequest("1111",
                200.0,
                new CardDetails("1111-1111-1111-1111", LocalDate.of(2020,8,3)));

        TicketBookingResponse response = ticketBookingService.payForBooking(tbpr);
        TicketBookingResponse expectedResponse = new TicketBookingResponse("1111", "3333", SUCCESS);

//        Assertions.assertThat(response.toString().trim()).isEqualTo(expectedResponse.toString().trim());

        verify(postRequestedFor(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                "\"cardNumber\" : \"1111-1111-1111-1111\",\n" +
                "\"cardExpiryDate\" : \"2020-08-03\",\n" +
                "\"amount\" : 200.0\n" +
                "}")));
    }


    @AfterEach
    void end()
    {
        wireMockRule.stop();
    }

}
