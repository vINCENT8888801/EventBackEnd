package com.fyp.eventBackend.WebSocketConfiguration;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.DrawImageUtil;
import com.fyp.eventBackend.ImageUtil;
import com.fyp.eventBackend.Auth.Response.ValidateUserResponse;
import com.fyp.eventBackend.Common.AttendanceStatusEnum;
import com.fyp.eventBackend.Common.GenderEnum;
import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Database.Attendee;
import com.fyp.eventBackend.Database.Blacklist;
import com.fyp.eventBackend.Database.BlacklistRepository;
import com.fyp.eventBackend.Database.Ticket;
import com.fyp.eventBackend.Database.TicketRepository;
import com.fyp.eventBackend.Database.UserRepository;
import com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest.FaceSearchRequest;
import com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest.MarkAttendanceRequest;
import com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest.RegisterWalkInRequest;
import com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest.getObjectTokenRequest;
import com.fyp.eventBackend.WebSocketConfiguration.Response.GetObjectTokenResponse;
import com.fyp.eventBackend.WebSocketConfiguration.Response.MarkAttendanceResponse;
import com.fyp.eventBackend.CallWiseAPI;
import com.fyp.eventBackend.WiseAPI.DetectFaceBASE64Response;
import com.fyp.eventBackend.WiseAPI.FaceSearchingResponse;
import com.fyp.eventBackend.WiseAPIResponseClass.FaceSearchResult;
import com.fyp.eventBackend.WiseAPIResponseClass.WiseAPIUtils;

@Controller
public class WebSocketController {

	private final SimpMessagingTemplate template;

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlacklistRepository blacklistRepository;

	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/send/message")
	public void onReceivedMessage(String message) {
		FaceSearchRequest responseBody = null;
		FaceSearchingResponse response = null;
		float confidenceThreshold = 0.75f;

		try {
			responseBody = new ObjectMapper().readerFor(FaceSearchRequest.class).readValue(message);
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String imageBase64String = "data:image/jpeg;base64," + responseBody.getImgString();

		// assign ObjToken
		FaceSearchSocketResponse socketRes = new FaceSearchSocketResponse();

		Attendee attendee;

		// check for blacklist
		if (checkForBlacklist(responseBody.getImgString(), imageBase64String, confidenceThreshold)) {
			return;
		}

		try {
			response = CallWiseAPI.faceSearching_BASE64(imageBase64String, responseBody.getId(), 1000);
			if (response.getResults().size() > 0) {

				// assign ObjToken
				socketRes = new FaceSearchSocketResponse();
				FaceSearchResult bestResult = getBestAccuracyFromAPIResult(response);

				if (bestResult.getConfidence() < confidenceThreshold) {
					return;
				}

				socketRes.setObjectToken(bestResult.getObjectToken());
				float x = bestResult.getConfidence();
				socketRes.setAccuracy(x);

				// getNameFromDatabase
				Ticket ticket = ticketRepository.findByObjectToken(bestResult.getObjectToken());
				if (ticket != null) {
					attendee = ticket.getAttendee();
					socketRes.setName(attendee.getUser().getName());
					socketRes.setAge(attendee.getAge());
					socketRes.setGender(attendee.getGender());
					socketRes.setAttendanceStatus(ticket.getAttendanceStatus());
					socketRes.setTicketId(ticket.getId());
					if(AttendanceStatusEnum.PRESENT.getValue().equals(ticket.getAttendanceStatus())||AttendanceStatusEnum.WALKIN.getValue().equals(ticket.getAttendanceStatus()))
					{
						socketRes.setAlreadyRegistered(true);
					}else {
						socketRes.setAlreadyRegistered(false);
					}
					// draw bounding box
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(responseBody.getImgString());
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
					DrawImageUtil.drawBoundingBox(img, response.getFaceLocate());
					String imgString = ImageUtil.imgToBase64String(img, "png");
					socketRes.setImgString(imgString);

					String JSONString = new ObjectMapper().writeValueAsString(socketRes);
					System.out.println("Returning Message : " + JSONString);

					this.template.convertAndSend("/result/detect", JSONString);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@MessageMapping("/send/attend")
	public void markAttendance(String message) throws JsonMappingException, JsonProcessingException {

		
		MarkAttendanceRequest responseBody = new ObjectMapper().readerFor(MarkAttendanceRequest.class).readValue(message);
		
		Ticket ticket = ticketRepository.findById(responseBody.getTicketId()).get();
		ticket.setAttendanceStatus(AttendanceStatusEnum.PRESENT.getValue());
		ticket.setTemperature(responseBody.getTemperature());
		ticketRepository.save(ticket);

		MarkAttendanceResponse response = new MarkAttendanceResponse();

		response.setStatus(RequestStatusEnum.SUCCESS.getValue());
		String JSONString = null;
		try {
			JSONString = new ObjectMapper().writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.template.convertAndSend("/result/attendance", JSONString);
	}

	@MessageMapping("/send/register")
	public void registerAttendance(String message) {
		
		float confidenceThreshold = 0.75f;
		
		try {
			GetObjectTokenResponse response = new GetObjectTokenResponse();
			getObjectTokenRequest responseBody = new ObjectMapper().readerFor(getObjectTokenRequest.class)
					.readValue(message);
			String imageBase64String = "data:image/jpeg;base64," + responseBody.getImgString();

			if(checkForBlacklist(responseBody.getImgString(), imageBase64String, confidenceThreshold)) {
				return;
			}
			
			DetectFaceBASE64Response APIresponse = CallWiseAPI.detectFaceBASE64(imageBase64String, 3);

			response.setAge(APIresponse.getFaces().get(0).getAttributes().getAge());
			response.setObjToken(APIresponse.getFaces().get(0).getObjectToken());
			response.setGender(GenderEnum.getEnumWithAPIValue(APIresponse.getFaces().get(0).getAttributes().getGender())
					.getValue());
			response.setImage64bitOriginal(responseBody.getImgString());

			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(responseBody.getImgString());
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
			DrawImageUtil.drawBoundingBox(img, APIresponse.getFaces().get(0).getFaceLocate());
			String imgString = ImageUtil.imgToBase64String(img, "png");
			response.setImage64bit(imgString);

			String JSONString = new ObjectMapper().writeValueAsString(response);

			this.template.convertAndSend("/result/objToken", JSONString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private FaceSearchResult getBestAccuracyFromAPIResult(FaceSearchingResponse response) {
		FaceSearchResult highestAccuracyResult = response.getResults().get(0);
		for (int i = 1; i < response.getResults().size(); i++) {
			if (response.getResults().get(i).getConfidence() > highestAccuracyResult.getConfidence()) {
				highestAccuracyResult = response.getResults().get(i);
			}
		}
		return highestAccuracyResult;
	}

	private boolean checkForBlacklist(String imgStringOriginal, String imageBase64String,
			float confidenceThreshold) {
		BlacklistResponse blacklistRes = new BlacklistResponse();
		try {
			FaceSearchingResponse response = CallWiseAPI.faceSearching_BASE64(imageBase64String,
					WiseAPIUtils.BLACKLIST_LIBRARY_ID, 10);
			if (response.getResults().size() > 0) {
				Blacklist blacklist = null;
				// getNameFromDatabase

				FaceSearchResult result = getBestAccuracyFromAPIResult(response);
				if (result.getConfidence() < confidenceThreshold) {
					return false;
				}

				blacklist = blacklistRepository.findByObjectToken(result.getObjectToken());

				if (blacklist != null) {
					blacklistRes.setBlacklist(blacklist);
					// draw bounding box
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imgStringOriginal);
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
					DrawImageUtil.drawBoundingBox(img, response.getFaceLocate());
					String imgString = ImageUtil.imgToBase64String(img, "png");
					blacklistRes.setImage64(imgString);
					blacklistRes.setDbImage64(new String(blacklist.getImage64bit()));

					String JSONString = new ObjectMapper().writeValueAsString(blacklistRes);
					System.out.println("Returning Message : " + JSONString);

					this.template.convertAndSend("/result/blacklist", JSONString);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
