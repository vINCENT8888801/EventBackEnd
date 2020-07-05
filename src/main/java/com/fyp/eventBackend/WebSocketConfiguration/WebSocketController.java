package com.fyp.eventBackend.WebSocketConfiguration;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.DrawImageUtil;
import com.fyp.eventBackend.ImageUtil;
import com.fyp.eventBackend.WISESampleData;
import com.fyp.eventBackend.tryCallingWiseAPI;
import com.fyp.eventBackend.WiseAPI.FaceSearchingResponse;

@Controller
public class WebSocketController {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@MessageMapping("/send/message")
	public void onReceivedMessage(String message) {
		String imageBase64String = "data:image/jpeg;base64," +message; 
		try {
			FaceSearchingResponse response = tryCallingWiseAPI.faceSearching_BASE64(imageBase64String,  WISESampleData.sampleDatabaseID);
			if(response.getSize()> 0 ) {
				//assign ObjToken
				FaceSearchSocketResponse socketRes = new FaceSearchSocketResponse();
				socketRes.setObjectToken(response.getResults().getObjectToken());
				float x = response.getResults().getConfidence();
				socketRes.setAccuracy(x);
				
				//getNameFromDatabase
				if(response.getResults().getConfidence() > 0.65 && response.getResults().getObjectToken().equals(WISESampleData.WeiSengObjectToken)) {
					socketRes.setName("Wei Seng");
				}else {
					socketRes.setName("Unidentified Individual");
				}
				
				//draw bounding box
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(message);
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
				DrawImageUtil.drawBoundingBox(img, response.getFaceLocate());
				String imgString = ImageUtil.imgToBase64String(img, "png");
				socketRes.setImgString(imgString);
				
				String JSONString = new ObjectMapper().writeValueAsString(socketRes);
				System.out.println("Returning Message : " + JSONString);
				
				this.template.convertAndSend("/result", JSONString);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
