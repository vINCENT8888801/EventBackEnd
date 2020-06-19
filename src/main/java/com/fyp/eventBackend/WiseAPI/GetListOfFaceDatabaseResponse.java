package com.fyp.eventBackend.WiseAPI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPIResponseClass.Library;

public class GetListOfFaceDatabaseResponse extends WiseAPIResponseBase {

	private List<Library> libraries;

	public List<Library> getLibraries() {
		return libraries;
	}

	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}

	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String, Object>> collection) {

		this.libraries = new ArrayList<Library>();
		LinkedHashMap<String, Object> libraryArray = (LinkedHashMap<String, Object>) collection.get(0);
		System.out.println(libraryArray);
		List<Map<String, Object>> jsonLibraries = (List<Map<String, Object>>) libraryArray.get("libraries");
		jsonLibraries.forEach((LibraryMap) -> {
			Library newLibrary = new ObjectMapper().convertValue(LibraryMap, Library.class);
			this.libraries.add(newLibrary);
		});

	}
}
