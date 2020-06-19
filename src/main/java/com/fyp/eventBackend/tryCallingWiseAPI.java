package com.fyp.eventBackend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

import javax.imageio.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPI.*;
import com.fyp.eventBackend.WiseAPIResponseClass.FaceLocate;
import com.fyp.eventBackend.WiseAPIResponseClass.WiseAPIUtils;

public class tryCallingWiseAPI {

	// for method 1
	// private static HttpURLConnection connection;
	private final static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	public static void main(String[] args) throws Exception {

		tryCallingWiseAPI obj = new tryCallingWiseAPI();

//		//Create New Library
//		System.out.println("Testing 1 - Create New Library");
//		obj.createFaceDatabase("testLib", Float.parseFloat("0.1"));

		File f = new File("C:/Users/WeiSeng/Desktop/fyp/sample images/testimages/facebook image.jpg");
		String encodestring = encodeFileToBase64Binary(f);
		encodestring = "data:image/jpeg;base64," + encodestring;

		System.out.println("BASE 64: " + encodestring);

//		Detect face with draw bounding box and saving images		

		FaceSearchingResponse response = faceSearching_BASE64(encodestring, WISESampleData.sampleDatabaseID);
		FaceLocate location = response.getFaceLocate();
		BufferedImage img = ImageIO.read(f);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(location.getLeft(), location.getTop(), location.getWidth(), location.getHeight());
		g2d.dispose();
		File savedFile = new File("C:/Users/WeiSeng/Desktop/fyp/sample images/resultImage/saveImg.jpg");
		ImageIO.write(img, "jpg", savedFile);

//

//		GetListOfFaceDatabaseResponse response = getListOfFaceDatabase();
//		Library lib = response.getLibraries().get(0);
//		System.out.println(lib.getLibraryId());
	}

	public static CreateFaceDatabaseResponse createFaceDatabase(String newLibraryName, float threshold)
			throws Exception {

		CreateFaceDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",")
				.append("\"libraryName\":\"" + newLibraryName + "\",").append("\"thresholds\":" + threshold).append("}")
				.toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.CREATE_FACE_DATABASE)).setHeader("Accept", "application/json") // add
																											// request
																											// header
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(CreateFaceDatabaseResponse.class).readValue(response.body());

		return responseBody;
	}

	public ModifyFaceDatabaseResponse modifyFaceDatabase(String LibraryNewName, String libraryId, float newThreshold)
			throws Exception {

		ModifyFaceDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",")
				.append("\"libraryId\":\"" + libraryId + "\",").append("\"libraryName\":\"" + LibraryNewName + "\",")
				.append("\"thresholds\":" + newThreshold).append("}").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.MODIFY_FACE_DATABASE)).setHeader("Accept", "application/json") // add
																											// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(ModifyFaceDatabaseResponse.class).readValue(response.body());

		return responseBody;

	}

	public static DeleteFaceDatabaseResponse deleteFaceDatabase(String LibraryName, String libraryId, float threshold)
			throws Exception {

		DeleteFaceDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",")
				.append("\"libraryId\":\"" + libraryId + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.DELETE_FACE_DATABASE)).setHeader("Accept", "application/json") // add
																											// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(DeleteFaceDatabaseResponse.class).readValue(response.body());

		return responseBody;

	}

	public static GetListOfFaceDatabaseResponse getListOfFaceDatabase() throws Exception {

		GetListOfFaceDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\"}").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.GET_LIST_OF_FACE_DATABASE)).setHeader("Accept", "application/json") // add
				// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(GetListOfFaceDatabaseResponse.class).readValue(response.body());

		return responseBody;
	}

	public GetFaceDatabaseDetailResponse getFaceDatabaseDetail(String libraryId) throws Exception {

		GetFaceDatabaseDetailResponse responseBody;

		String json = new StringBuilder().append("{").append("\"libraryId\":\"" + libraryId + "\",").append("{")
				.append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.GET_FACE_DATABASE_DETAIL)).setHeader("Accept", "application/json") // add
				// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(GetFaceDatabaseDetailResponse.class).readValue(response.body());

		return responseBody;

	}

	public RemoveSelectedFaceFromDatabaseResponse removeSelectedFaceFromDatabase(String libraryId, String objectTokens)
			throws Exception {

		RemoveSelectedFaceFromDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"libraryId\":\"" + libraryId + "\",")
				.append("\"objectTokens\":\"" + objectTokens + "\",").append("{")
				.append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.REMOVE_SELECTED_FACE_FROM_DATABASE))
				.setHeader("Accept", "application/json") // add
				// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(RemoveSelectedFaceFromDatabaseResponse.class)
				.readValue(response.body());

		return responseBody;
	}

	public AddNewFaceToDatabaseResponse addNewFaceToDatabase(String libraryId, String objectTokens) throws Exception {

		AddNewFaceToDatabaseResponse responseBody;

		String json = new StringBuilder().append("{").append("\"libraryId\":\"" + libraryId + "\",")
				.append("\"objectTokens\":\"" + objectTokens + "\",").append("{")
				.append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.ADD_NEW_FACES_TO_DATABASE)).setHeader("Accept", "application/json") // add
				// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

		responseBody = new ObjectMapper().readerFor(AddNewFaceToDatabaseResponse.class).readValue(response.body());

		return responseBody;

	}

	public void getFaceDetail(String objectTokens) throws Exception {

		String json = new StringBuilder().append("\"objectTokens\":\"" + objectTokens + "\",").append("{")
				.append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.GET_FACE_DETAILS)).setHeader("Accept", "application/json") // add
																										// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());
	}

	public static DetectFaceBASE64Response detectFaceBASE64(String base64image, int returnType) throws Exception {

		String charset = "UTF-8";
		File uploadFile1 = new File("e:/Test/PIC1.JPG");
		File uploadFile2 = new File("e:/Test/PIC2.JPG");
		String requestURL = WiseAPIUtils.FACE_DETECTION;
		Date date1 = new Date();
		DetectFaceBASE64Response response = null;

		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			multipart.addHeaderField("Accept", "application/json");
			multipart.addHeaderField("Content-Type", "multipart/form-data");

			multipart.addFormField("appKey", "0fc4029ea7bd337b81e8afb3b009b2be");
			multipart.addFormField("appSecret", "0fcc6aa6bcd1978c45ed470310c501c4");
			multipart.addFormField("imageBase64", base64image);
			multipart.addFormField("returnType", Integer.toString(returnType));

			String responseString = multipart.finish().get(0);

			System.out.println("SERVER REPLIED:");

			System.out.println(responseString);
			response = new ObjectMapper().readerFor(DetectFaceBASE64Response.class).readValue(responseString);

			System.out.println("ResquestID :" + response.getFaces());

		} catch (IOException ex) {
			System.err.println(ex);
		} finally {
			Date date2 = new Date();
			long difference = date2.getTime() - date1.getTime();
			System.out.println(difference);
		}
		return response;

	}

	public static void faceComparison_BASE64(String imageBase64One, String imageBase64Two) throws Exception {

		String charset = "UTF-8";
		File uploadFile1 = new File("e:/Test/PIC1.JPG");
		File uploadFile2 = new File("e:/Test/PIC2.JPG");
		String requestURL = WiseAPIUtils.FACE_COMPARISON;

		Date date1 = new Date();

		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			multipart.addHeaderField("Accept", "application/json");
			multipart.addHeaderField("Content-Type", "multipart/form-data");

			multipart.addFormField("appKey", "0fc4029ea7bd337b81e8afb3b009b2be");
			multipart.addFormField("appSecret", "0fcc6aa6bcd1978c45ed470310c501c4");
			multipart.addFormField("imageBase64One", imageBase64One);
			multipart.addFormField("imageBase64Two", imageBase64Two);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED:");

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		} finally {
			Date date2 = new Date();
			long difference = date2.getTime() - date1.getTime();
			System.out.println(difference);
		}

	}

	public static FaceSearchingResponse faceSearching_BASE64(String imageBase64One, String uniquenessId)
			throws Exception {

		FaceSearchingResponse response = null;

		String charset = "UTF-8";
		File uploadFile1 = new File("e:/Test/PIC1.JPG");
		File uploadFile2 = new File("e:/Test/PIC2.JPG");
		String requestURL = WiseAPIUtils.FACE_SEARCHING;

		Date date1 = new Date();

		try {

			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			System.out.println(uniquenessId);
			multipart.addHeaderField("Accept", "application/json");
			multipart.addHeaderField("Content-Type", "multipart/form-data");

			multipart.addFormField("appKey", WiseAPIUtils.APP_KEY);
			multipart.addFormField("appSecret", WiseAPIUtils.APP_SECRET);
			multipart.addFormField("imageBase64One", imageBase64One);
			multipart.addFormField("uniquenessId", uniquenessId);
			multipart.addFormField("dumb", "dump");

			String responseString = multipart.finish().get(0);
			System.out.println(responseString);

			response = new ObjectMapper().readerFor(FaceSearchingResponse.class).readValue(responseString);

			System.out.println("SERVER REPLIED:");

		} catch (IOException ex) {
			System.err.println(ex);
		} finally {
			Date date2 = new Date();
			long difference = date2.getTime() - date1.getTime();
			System.out.println(difference);
		}

		return response;
	}

	public void faceAttribute(String objectToken) throws Exception {

		String json = new StringBuilder().append("\"objectTokens\":\"" + objectToken + "\",").append("{")
				.append("\"appKey\":\"" + WiseAPIUtils.APP_KEY + "\",")
				.append("\"appSecret\":\"" + WiseAPIUtils.APP_SECRET + "\",").toString();

		System.out.println("JSON: " + json);

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(WiseAPIUtils.FACE_ATTRIBUTE)).setHeader("Accept", "application/json") // add
																										// request
				// header
				.header("Accept", "application/json").header("Content-Type", "application/json").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());
	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
			fileInputStreamReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encodedfile;
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		var builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append(",");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append(":");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		System.out.println(builder.toString());
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

//	public static void method1() {
//		BufferedReader reader;
//		String line;
//		StringBuffer responseContent = new StringBuffer();
//		try {
//			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
//			connection = (HttpURLConnection) url.openConnection();
//			
//			//Request setup
//			connection.setRequestMethod("GET");
//			connection.setConnectTimeout(5000);
//			connection.setReadTimeout(5000);
//			
//			int status = connection.getResponseCode();
//			//System.out.println(status);
//			
//			if(status >299) {
//				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//				while ((line = reader.readLine()) != null) {
//					responseContent.append(line);
//				}
//				reader.close();
//			} else {
//				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//				while((line = reader.readLine()) != null) {
//					responseContent.append(line);
//				}
//				reader.close();
//			
//			
//			}
//			System.out.println(responseContent.toString());
//		}catch (MalformedURLException e) {
//			e.printStackTrace();
//		}catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			connection.disconnect();
//		}
//			
//	}

}
