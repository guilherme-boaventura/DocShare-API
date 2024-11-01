package br.ucsal.docshare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/auth")
public class AuthController {

	public record AuthRequest(String username, String password) {}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) throws AuthenticationException {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password));
		jwtUtil.generateToken(authRequest.username);

		JsonObject jsonToken = new JsonObject();
		jsonToken.addProperty("token", jwtUtil.generateToken(authRequest.username));

		return jsonToken.toString();
	}
}