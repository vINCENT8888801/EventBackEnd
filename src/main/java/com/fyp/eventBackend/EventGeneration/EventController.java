package com.fyp.eventBackend.EventGeneration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.fyp.eventBackend.CallWiseAPI;
import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Database.Event;
import com.fyp.eventBackend.Database.EventRepository;
import com.fyp.eventBackend.Database.TicketRepository;
import com.fyp.eventBackend.EventGeneration.Request.CreateEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.EditEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.EventDetailRequest;
import com.fyp.eventBackend.EventGeneration.Request.EventListRequest;
import com.fyp.eventBackend.EventGeneration.Request.EventReportRequest;
import com.fyp.eventBackend.EventGeneration.Response.CreateEventResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventDetailResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventListResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventReportResponse;
import com.fyp.eventBackend.WiseAPI.CreateFaceDatabaseResponse;

@RestController
public class EventController {

	private final static float WiseAIThreshold = 0.1f;

	@Autowired
	private EventRepository eventRepository;
	

	@PostMapping("/event/create")
	public ResponseEntity<CreateEventResponse> CreateEvent(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateEventRequest createEventRequest) {
		
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);
		
		CreateEventResponse createEventResponse = new CreateEventResponse();
		CreateFaceDatabaseResponse response = null;
		Event newEvent = new Event();
		
		newEvent.setName(createEventRequest.getName());
		try {
			
			response = CallWiseAPI.createFaceDatabase(createEventRequest.getName(), WiseAIThreshold);
		} catch (Exception e1) {
			createEventResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			createEventResponse.setErrorMessage("WISE AI ENDPOINT FAILED TO GENERATE DATABASE");
			e1.printStackTrace();
		}
		
		try {
			System.out.println(createEventRequest.getDateTime());
			System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(createEventRequest.getDateTime()));
			newEvent.setDatetime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(createEventRequest.getDateTime()));
			newEvent.setMaxAttendee(createEventRequest.getMaxAttendee());
			newEvent.setUnlimitedParticipant(createEventRequest.isUnlimitedParticipant());
			newEvent.setLibraryId(response.getLibraryId());
			eventRepository.save(newEvent);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
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

		if (eventToBeUpdated != null) {
			eventToBeUpdated.setName(editEventRequest.getName());
			try {
				eventToBeUpdated
						.setDatetime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(editEventRequest.getTime()));
				eventToBeUpdated.setName(editEventRequest.getName());
				eventToBeUpdated.setUnlimitedParticipant(editEventRequest.isUnlimitedParticipant());
				if(!editEventRequest.isUnlimitedParticipant()) {
					eventToBeUpdated.setMaxAttendee(editEventRequest.getMaxAttendee());
				}
				
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
		if (requestedEvent == null) {
			eventDetailResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			eventDetailResponse.setError("No Event Found");
		} else {
			eventDetailResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());

			eventDetailResponse.setId(requestedEvent.getId());
			eventDetailResponse.setName(requestedEvent.getName());
			eventDetailResponse.setUnlimitedParticipant(requestedEvent.isUnlimitedParticipant());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			eventDetailResponse.setDateTime(formatter.format(requestedEvent.getDatetime()));
			eventDetailResponse.setMaxAttendee(requestedEvent.getMaxAttendee());
			eventDetailResponse.setCurrentParticipant(requestedEvent.getTickets().size());

		}

		return ResponseEntity.ok(eventDetailResponse);
	}
	
	@PostMapping("/event/today")
	public ResponseEntity<EventListResponse> GetTodayEventList(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EventListRequest eventListRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		EventListResponse eventListResponse = new EventListResponse();
		Pageable pageable = PageRequest.of(eventListRequest.getPageRequested(), 10);
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(new Date());
		
		
		todayCalendar.set(Calendar.HOUR_OF_DAY, 23);
		todayCalendar.set(Calendar.MINUTE, 59);
		Date todayEnd = todayCalendar.getTime();
		todayCalendar.set(Calendar.DAY_OF_MONTH,todayCalendar.get(Calendar.DAY_OF_MONTH)-1);
		Date todayStart = todayCalendar.getTime();
		System.out.println(todayStart);
		
		System.out.println(todayEnd);
		Page<Event> page = eventRepository.findAllByDatetimeBetween(todayStart,todayEnd,pageable);
		eventListResponse.setEventList(page.toList());
		eventListResponse.setTotalPage(page.getTotalPages());
		eventListResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());

		return ResponseEntity.ok(eventListResponse);
	}
	
	@PostMapping("/event/report")
	public ResponseEntity<EventReportResponse> GetEventReport(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody EventReportRequest eventReportRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);


		EventReportResponse eventReportResponse = new EventReportResponse();

		Event requestedEvent = eventRepository.findById(eventReportRequest.getEventId()).get();
		if (requestedEvent == null) {
			eventReportResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			eventReportResponse.setError("No Event Found");
		} else {
			eventReportResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
			Event.
			

		}

		return ResponseEntity.ok(eventReportResponse);
	}

}
