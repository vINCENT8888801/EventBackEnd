package com.fyp.eventBackend.BlackList;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.BlackList.Response.CreateBlacklistResponse;
import com.fyp.eventBackend.Ticket.Request.CreateTicketRequest;
import com.fyp.eventBackend.Ticket.Response.CreateTicketResponse;

@RestController
public class BlacklistController {
	
	@PostMapping("/blacklist/create")
	public ResponseEntity<CreateBlacklistResponse> Edit(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateTicketRequest createTicketRequest) {
				return null;
	
	}

}
