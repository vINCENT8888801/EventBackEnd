package com.fyp.eventBackend.EventGeneration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.fyp.eventBackend.EventGeneration.Request.EventDetailRequest;
import com.fyp.eventBackend.EventGeneration.Request.EventListRequest;
import com.fyp.eventBackend.EventGeneration.Response.CreateEventResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventDetailResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventListResponse;

@RestController
public class EventController {

	@Autowired
	private EventRepository eventRepository;

	@PostMapping("/event/create")
	public ResponseEntity<CreateEventResponse> CreateEvent(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateEventRequest createEventRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		Event newEvent = new Event();
		newEvent.setName(createEventRequest.getName());
		try {
			System.out.println(createEventRequest.getDateTime());
			newEvent.setDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(createEventRequest.getDateTime()));
			newEvent.setMaxAttendee(createEventRequest.getMaxAttendee());
			newEvent.setUnlimitedParticipant(createEventRequest.isUnlimitedParticipant());
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
	public ResponseEntity<CreateEventResponse> EditEvent(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EditEventRequest editEventRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		CreateEventResponse createEventResponse = new CreateEventResponse();
		
		Event eventToBeUpdated = eventRepository.findById(editEventRequest.getId()).get();
		
		if ( eventToBeUpdated != null) {
			eventToBeUpdated.setName(editEventRequest.getName());
			try {
				eventToBeUpdated.setDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(editEventRequest.getTime()));
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
	
	@PostMapping("/event/list")
	public ResponseEntity<EventListResponse> GetEventList(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EventListRequest eventListRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		EventListResponse eventListResponse = new EventListResponse();
		
		Pageable pageable = PageRequest.of(eventListRequest.getPageRequested(), 10);
		Page<Event> page = eventRepository.findAll(pageable);
		eventListResponse.setEventList(page.toList());
		eventListResponse.setTotalPage(page.getTotalPages());
		eventListResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		

		return ResponseEntity.ok(eventListResponse);
	}
	
	@PostMapping("/event/detail")
	public ResponseEntity<EventDetailResponse> GetEvent(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EventDetailRequest eventDetailRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		EventDetailResponse eventDetailResponse = new EventDetailResponse();
		
		Event requestedEvent = eventRepository.findById(eventDetailRequest.getEventId()).get();
		if( requestedEvent == null ) {
			eventDetailResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			eventDetailResponse.setError("No Event Found");
		}else {
			eventDetailResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
			
			eventDetailResponse.setId(requestedEvent.getId());
			eventDetailResponse.setName(requestedEvent.getName());
			eventDetailResponse.setUnlimitedParticipant(requestedEvent.isUnlimitedParticipant());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			eventDetailResponse.setDateTime(formatter.format(requestedEvent.getDateTime()));
			eventDetailResponse.setMaxAttendee(requestedEvent.getMaxAttendee());
			eventDetailResponse.setCurrentParticipant(requestedEvent.getTickets().size());
			
		}
		
		
		

		return ResponseEntity.ok(eventDetailResponse);
	}

}
