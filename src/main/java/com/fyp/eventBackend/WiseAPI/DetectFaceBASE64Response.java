package com.fyp.eventBackend.WiseAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPIResponseClass.Face;

//{"success":true,"code":"SUCCESS","message":"Success","size":1,"collection":[{"faces":[{"attributes":{"age":22,"gender":"Male"},"faceLocate":{"height":379,"left":485,"top":243,"width":208},"landmark":[{"x":547,"y":393},{"x":643,"y":394},{"x":596,"y":456},{"x":552,"y":528},{"x":639,"y":531}],"objectToken":"70AC50DD-D2E6-46A2-B6AF-FC01F7B120AA"}]}],"meta":{"requestId":"277e903b-99ad-468b-ab1f-6cf54c7b6eba","timestamp":1585150494484}}
public class DetectFaceBASE64Response extends WiseAPIResponseBase{
	
	private List<Face> faces;

	public List<Face> getFaces() {
		return faces;
	}


	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}


	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String,Object>> collection) {
		
		this.faces = new ArrayList<Face>();
		LinkedHashMap<String, Object> facesArray =  (LinkedHashMap<String, Object>) collection.get(0);
		System.out.println(facesArray);
		List<Map<String,Object>>jsonFaces =  (List<Map<String, Object>>) facesArray.get("faces");
		jsonFaces.forEach((faceMap)->{
			Face newFace = new ObjectMapper().convertValue(faceMap, Face.class);
			this.faces.add(newFace);
		});

	}



}
