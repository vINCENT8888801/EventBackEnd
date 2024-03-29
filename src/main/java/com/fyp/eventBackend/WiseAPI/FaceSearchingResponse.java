package com.fyp.eventBackend.WiseAPI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPIResponseClass.FaceLocate;
import com.fyp.eventBackend.WiseAPIResponseClass.FaceSearchResult;

public class FaceSearchingResponse extends WiseAPIResponseBase {

	private FaceLocate faceLocate;
	private String imageUrl;
	private List<FaceSearchResult> results;

	public FaceLocate getFaceLocate() {
		return faceLocate;
	}

	public void setFaceLocate(FaceLocate faceLocate) {
		this.faceLocate = faceLocate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<FaceSearchResult> getResults() {
		return results;
	}

	public void setResults(List<FaceSearchResult> results) {
		this.results = results;
	}

	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String, Object>> collection) {
		if (collection.size() > 0) {
			this.results = new ArrayList<FaceSearchResult>();
			LinkedHashMap<String, Object> resultArray = (LinkedHashMap<String, Object>) collection.get(0);
			System.out.println(resultArray);
			LinkedHashMap<String, Object> face = (LinkedHashMap<String, Object>) resultArray.get("face");
			this.faceLocate = new ObjectMapper().convertValue(face.get("faceLocate"), FaceLocate.class);
			this.imageUrl = (String) face.get("imageUrl");

			List<LinkedHashMap<String, Object>> resultarr = (List<LinkedHashMap<String, Object>>) resultArray
					.get("results");
			resultarr.forEach(result -> {
				this.results.add(new ObjectMapper().convertValue(result, FaceSearchResult.class));
			});

		}

	}
}
