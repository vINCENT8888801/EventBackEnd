package com.fyp.eventBackend.EventGeneration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.CallWiseAPI;
import com.fyp.eventBackend.Auth.Request.DeleteUserRequest;
import com.fyp.eventBackend.Auth.Response.RegisterUserResponse;
import com.fyp.eventBackend.Common.AttendanceStatusEnum;
import com.fyp.eventBackend.Common.GenderEnum;
import com.fyp.eventBackend.Common.ReportJSON;
import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Database.Event;
import com.fyp.eventBackend.Database.EventRepository;
import com.fyp.eventBackend.Database.Ticket;
import com.fyp.eventBackend.Database.TicketRepository;
import com.fyp.eventBackend.EventGeneration.Request.CreateEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.DeleteEventRequest;
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
			@RequestHeader Map<String, String> headers, @RequestBody EventReportRequest eventReportRequest) throws JsonProcessingException {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);


		EventReportResponse eventReportResponse = new EventReportResponse();

		Event requestedEvent = eventRepository.findById(eventReportRequest.getEventId()).get();
		if (requestedEvent == null) {
			eventReportResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			eventReportResponse.setError("No Event Found");
		} else {
			
			eventReportResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
			
			
			//calculate gender
			List<ReportJSON> genderData = new ArrayList<ReportJSON>();
			
			int maleCount = 0;
			int femaleCount = 0;
			List<Ticket> tickets = requestedEvent.getTickets();
			for(Ticket ticket: tickets) {
				if(ticket.getAttendee() !=null) {
					if( GenderEnum.MALE.getDBValue().equals(ticket.getAttendee().getGender())) {
						maleCount ++;
					}else {
						femaleCount ++;
					}
				}
				
			}
			
			genderData.add(new ReportJSON("Male",maleCount));
			genderData.add(new ReportJSON("Female",femaleCount));
			
			eventReportResponse.setGenderData(new ObjectMapper().writeValueAsString(genderData));
			
			//calculate attendance
			List<ReportJSON> attendanceData = new ArrayList<ReportJSON>();
			
			int attend = 0;
			int walkIn = 0;
			int absent = 0;
			
			for(Ticket ticket: tickets) {
				if(ticket.getAttendanceStatus()!= null) {
					System.out.println(ticket.getAttendanceStatus());
					if(ticket.getAttendanceStatus().equals(AttendanceStatusEnum.PRESENT.getValue())) {
						attend++;
					}
					if(ticket.getAttendanceStatus().equals(AttendanceStatusEnum.WALKIN.getValue())) {
						walkIn++;
					}
				}
				
			}
			absent = tickets.size() - walkIn - attend;
			attendanceData.add(new ReportJSON("Present",attend));
			attendanceData.add(new ReportJSON("Absent",absent));
			attendanceData.add(new ReportJSON("Walks In",walkIn));
			
			eventReportResponse.setAttendanceData(new ObjectMapper().writeValueAsString(attendanceData));
			
			
			//calculate temperature
			List<ReportJSON> temperatureData = new ArrayList<ReportJSON>();
			
			for(float i = 35.0f ; i < 40.0f ; i = Float.sum(i,0.1f)  ) {
				temperatureData.add(new ReportJSON(String.valueOf(i),0));
			}
			for(Ticket ticket: tickets) {
				if(ticket.getTemperature() != null) {
					float temperature = ticket.getTemperature();
					if(temperature >= 35.0f && temperature <= 40.0f) {
						int pos = ((int) ((temperature-35.f)/0.1f));
						ReportJSON datum = temperatureData.get(pos);
						datum.setValue(datum.getValue()+1);
					}
				}
				
				
			}
			eventReportResponse.setTemperatureData(new ObjectMapper().writeValueAsString(temperatureData));
		}

		return ResponseEntity.ok(eventReportResponse);
	}
	
	@PostMapping("/event/delete")
	public ResponseEntity<?> deleteEvent(@RequestBody DeleteEventRequest deleteEventRequest) throws Exception {

		RegisterUserResponse response = new RegisterUserResponse();

		eventRepository.deleteById(deleteEventRequest.getEventId());
		response.setStatus(RequestStatusEnum.SUCCESS.getValue());

		return ResponseEntity.ok(response);
	}

}
