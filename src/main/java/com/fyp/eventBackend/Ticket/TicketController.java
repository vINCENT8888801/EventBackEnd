package com.fyp.eventBackend.Ticket;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.fyp.eventBackend.EventGeneration.Request.CreateEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.EditEventRequest;
import com.fyp.eventBackend.EventGeneration.Response.CreateEventResponse;
import com.fyp.eventBackend.Ticket.Request.CreateTicketRequest;
import com.fyp.eventBackend.Ticket.Request.RegisterTicketRequest;
import com.fyp.eventBackend.Ticket.Response.CreateTicketResponse;
import com.fyp.eventBackend.Ticket.Response.RegisterTicketResponse;

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

	@PostMapping("/ticket/create")
	public ResponseEntity<CreateTicketResponse> Edit(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateTicketRequest createTicketRequest) {
		
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		CreateTicketResponse createTicketResponse = new CreateTicketResponse();
		Ticket newTicket = null;
		
		//validation
		Event eventToBeAdded = eventRepository.findById(createTicketRequest.getEventId()).get();
		int totalTicketAllowed = ticketRepository.getTotalTicketByEventId(createTicketRequest.getEventId()).intValue();
		
		
		if(eventToBeAdded.getMaxAttendee() <= totalTicketAllowed) {
			//get random ticket no
			List<String> usedTicketNos = ticketRepository.getAllTicketNo();
			boolean unique = false;
			String randomticketNo = null;
			
			while(!unique) {
				randomticketNo = generateRandomString();
				if(randomticketNo.indexOf(randomticketNo) == -1) {
					unique = true;
				}
			}
			
			newTicket = new Ticket();
			newTicket.setTicketNo(randomticketNo);
			newTicket.setEvent(eventToBeAdded);
			
			ticketRepository.save(newTicket);
			
			createTicketResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		}else {
			createTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			createTicketResponse.setError("No Event");
			createTicketResponse.setError("Event with Particular ID is not found");
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
		
		if(validateTicketNo(ticketNo)) {
			registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			registerTicketResponse.setError("Invalid Ticket No");
			registerTicketResponse.setErrorMessage("Invalid Ticket No, Please retry to enter correct Ticket No");
		}
		
		if(attendee != null || registerTicketResponse.getStatus().equals(RequestStatusEnum.FAILED.getValue())) {
			List<Ticket> tickets = ticketRepository.findByTicketNo(ticketNo);
			if (tickets.size() == 1) {
				Ticket ticket = tickets.get(0);
				ticket.setAttendee(attendee);
				registerTicketResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
				registerTicketResponse.setEventRegistered(ticket.getEvent());
				
			}else {
				registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
				registerTicketResponse.setError("Ticket No Not Found");
				registerTicketResponse.setErrorMessage("Ticket No Not Found, Please try to retry");
			}
				
		}else {
			registerTicketResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			registerTicketResponse.setError("Attendee Not Found");
			registerTicketResponse.setErrorMessage("Attendee Not Found, Please try to relogin");
		}
			
			registerTicketResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		
		return ResponseEntity.ok(registerTicketResponse);
	}

	private boolean validateTicketNo(String ticketNo) {
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
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	 
	    return generatedString;
	}
}