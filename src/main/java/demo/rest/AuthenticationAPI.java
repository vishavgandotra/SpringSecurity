package demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.security.jwt.JWTManager;
import demo.security.model.AuthenticationRequest;
import demo.security.model.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationAPI {

	@Autowired
	private UserDetailsService service;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTManager jwtManager;
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> generateToken(@RequestBody AuthenticationRequest authenticationRequest){
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		UserDetails userDetails = service.loadUserByUsername(username);
		
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		String jwt = jwtManager.createToken(userDetails);
		return ResponseEntity.ok().body(new AuthenticationResponse(jwt));		
		
	}
	
}
