package com.fyp.eventBackend.EventGeneration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Database.Event;
import com.fyp.eventBackend.Database.EventRepository;
import com.fyp.eventBackend.EventGeneration.Request.CreateEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.EditEventRequest;
import com.fyp.eventBackend.EventGeneration.Response.CreateEventResponse;

@RestController
public class EventController {

	@Autowired
	private EventRepository eventRepository;

	@PostMapping("/event/create")
	public ResponseEntity<CreateEventResponse> Edit(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateEventRequest createEventRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		Event newEvent = new Event();
		newEvent.setName(createEventRequest.getName());
		try {
			newEvent.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(createEventRequest.getTime()));
			newEvent.setMaxAttendee(createEventRequest.getMaxAttendee());
			eventRepository.save(newEvent);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		CreateEventResponse createEventResponse = new CreateEventResponse();
		
		createEventResponse.setNewEvent(newEvent);
		createEventResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());

		return ResponseEntity.ok(createEventResponse);
	}
	
	@PostMapping("/event/edit")
	public ResponseEntity<CreateEventResponse> CreateEvent(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EditEventRequest editEventRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		CreateEventResponse createEventResponse = new CreateEventResponse();
		
		Event eventToBeUpdated = eventRepository.findById(editEventRequest.getId()).get();
		
		if ( eventToBeUpdated != null) {
			eventToBeUpdated.setName(editEventRequest.getName());
			try {
				eventToBeUpdated.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(editEventRequest.getTime()));
				eventToBeUpdated.setMaxAttendee(editEventRequest.getMaxAttendee());
				eventRepository.save(eventToBeUpdated);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			createEventResponse.setNewEvent(eventToBeUpdated);
			createEventResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		}
		

		return ResponseEntity.ok(createEventResponse);
	}

}
