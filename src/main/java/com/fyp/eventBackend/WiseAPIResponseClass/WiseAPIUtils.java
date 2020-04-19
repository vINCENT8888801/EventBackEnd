package com.fyp.eventBackend.WiseAPIResponseClass;

public class WiseAPIUtils {
	//KEYS
	public final static String APP_KEY = "0fc4029ea7bd337b81e8afb3b009b2be";
	public final static String APP_SECRET = "0fcc6aa6bcd1978c45ed470310c501c4";
	
	//POST
	public final static String CREATE_FACE_DATABASE = "https://api-live.wiseai.tech/face/v2/library/create";
	public final static String MODIFY_FACE_DATABASE = "https://api-live.wiseai.tech/face/v2/library/update";
	public final static String DELETE_FACE_DATABASE = "https://api-live.wiseai.tech/face/v2/library/delete";
	public final static String GET_LIST_OF_FACE_DATABASE = "https://api-live.wiseai.tech/face/v2/library/getLibraryList";
	public final static String GET_FACE_DATABASE_DETAIL = "https://api-live.wiseai.tech/face/v2/library/getLibraryDetails";
	public final static String ADD_NEW_FACES_TO_DATABASE = "https://api-live.wiseai.tech/face/v2/library/addFace";
	public final static String REMOVE_SELECTED_FACE_FROM_DATABASE = "https://api-live.wiseai.tech/face/v2/library/removeFace";
	
	
	public final static String GET_FACE_DETAILS = "https://api-live.wiseai.tech/face/v2/library/getFaceDetailByToken";
	
	public final static String FACE_DETECTION = "https://api-live.wiseai.tech/face/v2/detect";
	
	public final static String FACE_COMPARISON = "https://api-live.wiseai.tech/face/v2/compare";
	public final static String FACE_SEARCHING = "https://api-live.wiseai.tech/face/v2/search";
	public final static String FACE_ATTRIBUTE = "https://api-live.wiseai.tech/face/v2/analyzeByToken";

}
