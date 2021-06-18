package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test08Spies {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setUp() {

		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = spy(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void should_MakeBooking_When_InputOK() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, true);
		
		// when
		String bookingId = bookingService.makeBooking(bookingRequest);
		
		// then
		verify(bookingDAOMock, times(1)).save(bookingRequest);
		System.out.println("BookingId=" + bookingId);

	}
	
	@Test
	void should_CancelBooking_When_InputOK() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";
		
		//manipulating the spy to return bookingRequest defined above
		doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);
		
		// when
		bookingService.cancelBooking(bookingId);
		
		// then
		System.out.println("BookingId=" + bookingId);

	}

}
