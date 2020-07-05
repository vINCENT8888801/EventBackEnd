package com.fyp.eventBackend.Auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.Auth.Request.DeleteUserRequest;
import com.fyp.eventBackend.Auth.Request.RegisterUserRequest;
import com.fyp.eventBackend.Auth.Response.RegisterUserResponse;
import com.fyp.eventBackend.Auth.Response.ValidateUserResponse;
import com.fyp.eventBackend.Common.RequestStatusEnum;
import com.fyp.eventBackend.Common.SerlvetKeyConstant;
import com.fyp.eventBackend.Common.UserRoleEnum;
import com.fyp.eventBackend.Database.Attendee;
import com.fyp.eventBackend.Database.AttendeeRepository;
import com.fyp.eventBackend.Database.User;
import com.fyp.eventBackend.Database.UserRepository;

@RestController
public class AuthController {

	@Autowired
	private JwtUtils jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AttendeeRepository attendeeRepository;

	@PostMapping("/authenticate/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest) throws Exception {
		
		AuthResponse response = new AuthResponse();
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		} catch (Exception ex) {
			response.setError("Invalid Credential");
			response.setStatus(RequestStatusEnum.FAILED.getValue());
			return ResponseEntity.ok(response);
		}
		
		response = new AuthResponse(jwtUtil.generateToken(authRequest.getEmail()));
		response.setStatus(RequestStatusEnum.SUCCESS.getValue());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/authenticate/register/user/validate")
	public ResponseEntity<ValidateUserResponse> validateNewUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
		ValidateUserResponse validateUserResponse = new ValidateUserResponse();
		
		if(userRepository.findByName(registerUserRequest.getName()) == null) {
			validateUserResponse.setUniqueName(true);
		}
		
		if(userRepository.findByEmail(registerUserRequest.getEmail()) == null) {
			validateUserResponse.setUniqueEmail(true);
		}
		
		return ResponseEntity.ok(validateUserResponse);
	}

	
	@PostMapping("/authenticate/register/user")
	public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {

		RegisterUserResponse response = new RegisterUserResponse();
		User newUser = new User();
		// validation
		if (registerUserRequest.getImage64bit() != null) {
			newUser.setEmail(registerUserRequest.getEmail());
			newUser.setName(registerUserRequest.getName());
			newUser.setPassword(registerUserRequest.getPassword());
			newUser.setRole(UserRoleEnum.ATTENDEE.getDBValue());

			userRepository.save(newUser);
			Attendee newAttendee = new Attendee();
			newAttendee.setUser(newUser);
			newAttendee.setImage64bit(registerUserRequest.getImage64bit().getBytes());
			attendeeRepository.save(newAttendee);
			response.setStatus(RequestStatusEnum.SUCCESS.getValue());
		}else {
			response.setStatus(RequestStatusEnum.FAILED.getValue());
			response.setError("Illegal Response");
		}

		response.setNewUser(newUser);
		return ResponseEntity.ok(response);
	}

	// todo:Remove if not used later
	// Currently for development purpose

	@PostMapping("/authenticate/delete/user")
	public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) throws Exception {

		RegisterUserResponse response = new RegisterUserResponse();

		userRepository.deleteById(deleteUserRequest.getUserId());

		return ResponseEntity.ok(response);
	}

	// todo:Remove
	@GetMapping("/listHeaders")
	public ResponseEntity<String> listAllHeaders(HttpServletRequest httpServletRequest,
			@RequestHeader Map<String, String> headers) {

		headers.forEach((key, value) -> {
			System.out.println(String.format("Header '%s' = %s", key, value));
		});
		String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);

		System.out.println(username);

		return new ResponseEntity<String>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
	}
}