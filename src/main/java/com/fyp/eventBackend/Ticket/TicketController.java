package com.fyp.eventBackend.Ticket;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Database.Attendee;
import com.fyp.eventBackend.Database.AttendeeRepository;
import com.fyp.eventBackend.Database.Event;
import com.fyp.eventBackend.Database.EventRepository;
import com.fyp.eventBackend.Database.Ticket;
import com.fyp.eventBackend.Database.TicketRepository;
import com.fyp.eventBackend.Database.User;
import com.fyp.eventBackend.Database.UserRepository;
import com.fyp.eventBackend.Ticket.Request.CreateTicketRequest;
import com.fyp.eventBackend.Ticket.Request.RegisterTicketRequest;
import com.fyp.eventBackend.Ticket.Response.CreateTicketResponse;
import com.fyp.eventBackend.Ticket.Response.RegisterTicketResponse;

@CrossOrigin("*")
@RestController
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AttendeeRepository attendeeRepository;

	// todos: add admin validation
	@PostMapping("/ticket/create")
	public ResponseEntity<CreateTicketResponse> Edit(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateTicketRequest createTicketRequest) {

		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		CreateTicketResponse createTicketResponse = new CreateTicketResponse();
		Ticket newTicket = null;

		// validation
		Event eventToBeAdded = eventRepository.findById(createTicketRequest.getEventId()).get();

		if (eventToBeAdded.equals(null)) {
			createTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			createTicketResponse.setError("No Event");
			createTicketResponse.setError("Event with Particular ID is not found");
		}

		if (!eventToBeAdded.isUnlimitedParticipant()
				|| eventToBeAdded.getTickets().size() >= eventToBeAdded.getMaxAttendee()) {
			createTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			createTicketResponse.setError("Event Full");
			createTicketResponse.setError("Maximum participant for the event reached.");
		}

		if (!RequestStatusEnum.FAILED.getValue().equals(createTicketResponse.getStatus())) {
			// get random ticket no
			List<String> usedTicketNos = ticketRepository.getAllTicketNo();
			boolean unique = false;
			String randomticketNo = null;

			while (!unique) {
				randomticketNo = generateRandomString();
				if (usedTicketNos.indexOf(randomticketNo) == -1) {
					unique = true;
				}
			}

			newTicket = new Ticket();
			newTicket.setTicketNo(randomticketNo);
			newTicket.setEvent(eventToBeAdded);

			ticketRepository.save(newTicket);

			createTicketResponse.setTicket(newTicket);
			createTicketResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		}
		return ResponseEntity.ok(createTicketResponse);
	}

	@PostMapping("/ticket/register")
	public ResponseEntity<RegisterTicketResponse> RegisterTicket(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody RegisterTicketRequest registerTicketRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);
		RegisterTicketResponse registerTicketResponse = new RegisterTicketResponse();

		User user = userRepository.findByEmail(username);
		Attendee attendee = attendeeRepository.findById(user.getId()).get();
		String ticketNo = registerTicketRequest.getTicketNo();

		if (!validateTicketFormat(ticketNo)) {
			registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			registerTicketResponse.setError("Invalid Ticket No");
			registerTicketResponse.setErrorMessage("Invalid Ticket No, Please retry to enter correct Ticket No");
		}

		if (attendee != null || !registerTicketResponse.getStatus().equals(RequestStatusEnum.FAILED.getValue())) {

			List<Ticket> tickets = ticketRepository.findByTicketNo(ticketNo);
			System.out.println("size " + tickets.size());
			Ticket ticket = tickets.get(0);

			if (tickets.size() != 1) {
				registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
				registerTicketResponse.setError("Ticket Number Not Found");
				registerTicketResponse.setErrorMessage("Ticket No Not Found, Please try to re-enter the ticket code");
			}

			if (ticketIsUsed(ticket)
					&& !RequestStatusEnum.FAILED.getValue().equals(registerTicketResponse.getStatus())) {
				registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
				registerTicketResponse.setError("Ticket No is Used");
				registerTicketResponse.setErrorMessage("Invalid Ticket No, Please retry to enter correct Ticket No");
			}

			if (isUserRegistered(ticket.getEvent(), attendee)) {
				registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
				registerTicketResponse.setError("User alreaady registered");
				registerTicketResponse.setErrorMessage("You already registered for this event");
			}
			if (!RequestStatusEnum.FAILED.getValue().equals(registerTicketResponse.getStatus())) {

				ticket.setAttendee(attendee);
				ticketRepository.save(ticket);

				registerTicketResponse.setEventRegistered(ticket.getEvent());
				registerTicketResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
			}

		} else {
			registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			registerTicketResponse.setError("Attendee Not Found");
			registerTicketResponse.setErrorMessage("Attendee Not Found, Please try to relogin");
		}

		return ResponseEntity.ok(registerTicketResponse);
	}

	private boolean isUserRegistered(Event event, Attendee attendee) {
		List<Ticket> tickets = event.getTickets();
		for (Ticket ticket :tickets) {
			if (ticket.getAttendee() != null) {
				ticket.getAttendee().getId().equals(attendee.getId());
				return true;
			}
		}
		return false;
	}

	private boolean ticketIsUsed(Ticket ticket) {
		return ticket.getAttendee() != null;
	}

	private boolean validateTicketFormat(String ticketNo) {
		if (ticketNo.length() != 10) {
			return false;
		}

		return true;
	}

	private String generateRandomString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}
}