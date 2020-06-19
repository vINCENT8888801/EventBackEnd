package com.fyp.eventBackend.Auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.eventBackend.Auth.Request.RegisterUserRequest;
import com.fyp.eventBackend.Auth.Response.RegisterUserResponse;
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
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password", ex);
        }
        AuthResponse response = new AuthResponse(jwtUtil.generateToken(authRequest.getEmail()));
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/authenticate/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
    	
    	RegisterUserResponse response = new RegisterUserResponse();
    	
    	User newUser = new User();
    	newUser.setEmail(registerUserRequest.getEmail());
    	newUser.setName(registerUserRequest.getName());
    	newUser.setPassword(registerUserRequest.getPassword());
    	newUser.setRole(UserRoleEnum.ATTENDEE.getDBValue());
    	
    	userRepository.save(newUser);
        
    	Attendee newAttendee = new Attendee();
    	newAttendee.setUser(newUser);
    	
    	attendeeRepository.save(newAttendee);
    	
    	response.setNewUser(newUser);
        return ResponseEntity.ok(response);
    }
    
    //Remove
    @GetMapping("/listHeaders")
    public ResponseEntity<String> listAllHeaders(HttpServletRequest httpServletRequest ,
      @RequestHeader Map<String, String> headers) {
    	
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        String username = (String) httpServletRequest.getAttribute(SerlvetKeyConstant.EMAIL);
        
        System.out.println(username);
        
        return new ResponseEntity<String>(
          String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }
}