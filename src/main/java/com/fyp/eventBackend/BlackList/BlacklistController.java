package com.fyp.eventBackend.BlackList;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.CallWiseAPI;
import com.fyp.eventBackend.DrawImageUtil;
import com.fyp.eventBackend.ImageUtil;
import com.fyp.eventBackend.BlackList.Request.BlacklistDetailRequest;
import com.fyp.eventBackend.BlackList.Request.BlacklistListRequest;
import com.fyp.eventBackend.BlackList.Request.CreateBlackListRequest;
import com.fyp.eventBackend.BlackList.Request.DeleteBlacklistRequest;
import com.fyp.eventBackend.BlackList.Request.UpdateBlackListRequest;
import com.fyp.eventBackend.BlackList.Request.VerifyBlacklistImageRequest;
import com.fyp.eventBackend.BlackList.Response.BlacklistDetailResponse;
import com.fyp.eventBackend.BlackList.Response.BlacklistListResponse;
import com.fyp.eventBackend.BlackList.Response.CreateBlacklistResponse;
import com.fyp.eventBackend.BlackList.Response.DeleteBlacklistResponse;
import com.fyp.eventBackend.BlackList.Response.VerifyBlacklistImageResponse;
import com.fyp.eventBackend.Common.GenderEnum;
import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Database.Blacklist;
import com.fyp.eventBackend.Database.BlacklistRepository;
import com.fyp.eventBackend.Database.Event;
import com.fyp.eventBackend.EventGeneration.Request.EditEventRequest;
import com.fyp.eventBackend.EventGeneration.Request.EventListRequest;
import com.fyp.eventBackend.EventGeneration.Response.CreateEventResponse;
import com.fyp.eventBackend.EventGeneration.Response.EventListResponse;
import com.fyp.eventBackend.Ticket.Request.CreateTicketRequest;
import com.fyp.eventBackend.Ticket.Response.CreateTicketResponse;
import com.fyp.eventBackend.WiseAPI.DetectFaceBASE64Response;
import com.fyp.eventBackend.WiseAPI.RemoveSelectedFaceFromDatabaseResponse;
import com.fyp.eventBackend.WiseAPIResponseClass.WiseAPIUtils;

@RestController
public class BlacklistController {
	
	@Autowired
	private BlacklistRepository blacklistRepository;
	
	@PostMapping("/blacklist/create")
	public ResponseEntity<CreateBlacklistResponse> createBlackList (HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody CreateBlackListRequest createBlackListRequest) {
			CreateBlacklistResponse response = new CreateBlacklistResponse();
			
			try {
				CallWiseAPI.addNewFaceToDatabase(WiseAPIUtils.BLACKLIST_LIBRARY_ID, createBlackListRequest.getObjectToken());
			} catch (Exception e1) {
				response.setStatus(RequestStatusEnum.FAILED.getValue());
				response.setErrorMessage("WISE API erro");
				return ResponseEntity.ok(response);
			}
			Blacklist blacklist = new Blacklist();
			blacklist.setAge(createBlackListRequest.getAge());
			blacklist.setGender(GenderEnum.getEnumwithValue(createBlackListRequest.getGender()).getDBValue());
			blacklist.setImage64bit(createBlackListRequest.getImage64bit().getBytes());
			blacklist.setName(createBlackListRequest.getName());
			blacklist.setObjectToken(createBlackListRequest.getObjectToken());
			try {
				blacklistRepository.save(blacklist);
			}catch(Exception e) {
				response.setStatus(RequestStatusEnum.FAILED.getValue());
				return ResponseEntity.ok(response);
			}
			
			
			response.setNewBlacklist(blacklist);
			response.setImage64bit(new String(blacklist.getImage64bit()));
			response.setStatus(RequestStatusEnum.SUCCESS.getValue());
			return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/blacklist/verify")
	public ResponseEntity<VerifyBlacklistImageResponse> verifyBlacklistImage (HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody VerifyBlacklistImageRequest verifyBlacklistImageRequest) throws IOException {
			VerifyBlacklistImageResponse response = new VerifyBlacklistImageResponse();
			DetectFaceBASE64Response APIresponse = null;
			String imageBase64String = "data:image/jpeg;base64," +verifyBlacklistImageRequest.getImage64bit();
			
			
			
			
			try {
				APIresponse = CallWiseAPI.detectFaceBASE64(imageBase64String, 3);
			} catch (Exception e) {
				response.setStatus(RequestStatusEnum.FAILED.getValue());
				response.setErrorMessage("WISE API Failed to detect face");
				return ResponseEntity.ok(response);
			}
			if(APIresponse.getFaces().size() > 0) {
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(verifyBlacklistImageRequest.getImage64bit());
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
				DrawImageUtil.drawBoundingBox(img, APIresponse.getFaces().get(0).getFaceLocate());
				String imgString = ImageUtil.imgToBase64String(img, "png");
				response.setImage64bit(imgString);
				response.setObjToken(APIresponse.getFaces().get(0).getObjectToken());
				String gender = GenderEnum.getEnumWithAPIValue(APIresponse.getFaces().get(0).getAttributes().getGender()).getValue();
				response.setGender(gender);
				response.setAge(APIresponse.getFaces().get(0).getAttributes().getAge());
			}
			
			
			response.setStatus(RequestStatusEnum.SUCCESS.getValue());
			return ResponseEntity.ok(response);
	}
	
	@PostMapping("/blacklist/detail")
	public ResponseEntity<BlacklistDetailResponse> getBlacklistDetail (HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody BlacklistDetailRequest blacklistDetailRequest) throws IOException {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		BlacklistDetailResponse blacklistDetailResponse = new BlacklistDetailResponse();

		Blacklist requestedBlacklist = blacklistRepository.findById(blacklistDetailRequest.getBlackListId()).get();

		if (requestedBlacklist == null) {
			blacklistDetailResponse.setStatus(RequestStatusEnum.FAILED.getValue());
			blacklistDetailResponse.setError("No Blacklist Found");
		} else {
			blacklistDetailResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
			blacklistDetailResponse.setName(requestedBlacklist.getName());
			blacklistDetailResponse.setAge(requestedBlacklist.getAge());
			blacklistDetailResponse.setGender(GenderEnum.getEnumwithdbValue(requestedBlacklist.getGender()).getValue());
			blacklistDetailResponse.setImage64bit(new String(requestedBlacklist.getImage64bit()));

		}

		return ResponseEntity.ok(blacklistDetailResponse);
	}
	
	@PostMapping("/blacklist/edit")
	public ResponseEntity<CreateBlacklistResponse> updateBlacklist(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody UpdateBlackListRequest updateBlackListRequest) throws Exception {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		CreateBlacklistResponse createBlacklistResponse = new CreateBlacklistResponse();

		Blacklist blacklistToBeUpdated = blacklistRepository.findById(updateBlackListRequest.getId()).get();

		if (blacklistToBeUpdated != null) {
			blacklistToBeUpdated.setName(updateBlackListRequest.getName());
			blacklistToBeUpdated.setAge(updateBlackListRequest.getAge());
			blacklistToBeUpdated.setGender(GenderEnum.getEnumwithValue(updateBlackListRequest.getGender()).getDBValue());
			if(updateBlackListRequest.isImgChanged()) {
				CallWiseAPI.removeSelectedFaceFromDatabase(WiseAPIUtils.BLACKLIST_LIBRARY_ID, updateBlackListRequest.getObjectToken());
				blacklistToBeUpdated.setImage64bit(updateBlackListRequest.getImage64bit().getBytes());
				blacklistToBeUpdated.setObjectToken(updateBlackListRequest.getObjectToken());
			}
			
			blacklistRepository.save(blacklistToBeUpdated);

			createBlacklistResponse.setNewBlacklist(blacklistToBeUpdated);
			createBlacklistResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());
		}

		return ResponseEntity.ok(createBlacklistResponse);
	}
	
	@PostMapping("/blacklist/list")
	public ResponseEntity<BlacklistListResponse> GetEventList(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody BlacklistListRequest blacklistListRequest) {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		BlacklistListResponse blacklistListResponse = new BlacklistListResponse();

		Pageable pageable = PageRequest.of(blacklistListRequest.getPageRequested(), 10);
		Page<Blacklist> page = blacklistRepository.findAll(pageable);
		blacklistListResponse.setBlacklistList(page.toList());
		blacklistListResponse.setTotalPage(page.getTotalPages());
		blacklistListResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());

		return ResponseEntity.ok(blacklistListResponse);
	}
	
	@PostMapping("/blacklist/delete")
	public ResponseEntity<DeleteBlacklistResponse> deleteteBlacklist(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers, @RequestBody DeleteBlacklistRequest deleteBlacklistRequest) throws Exception {
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		DeleteBlacklistResponse blacklistListResponse = new DeleteBlacklistResponse();

		
		blacklistRepository.deleteById(deleteBlacklistRequest.getBlacklistId());
		blacklistListResponse.setStatus(RequestStatusEnum.SUCCESS.getValue());

		return ResponseEntity.ok(blacklistListResponse);
	}

}
