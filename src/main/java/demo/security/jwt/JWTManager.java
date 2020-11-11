package demo.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTManager {
	private static final String SECRET_KEY = "secret";
	
	public String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("organization", "http://www.abc.com");
		
		try {
			return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() +( 1000 * 60 * 5)))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.compact();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token)
					.getBody();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getUsername(String token) {
		return Optional.ofNullable(getClaims(token))
					.map(Claims::getSubject)
					.orElse(null);
	}
	
	private boolean isTokenExpired(String token) {
		return Optional.ofNullable(getClaims(token))
				.map(Claims::getExpiration)
				.map(date -> date.before(new Date(System.currentTimeMillis())))
				.orElse(true);
	}
	
	public boolean isValidToken(String token, UserDetails userDetails) {
		String username = getUsername(token);
		if(username != null && userDetails != null) {
			return userDetails.getUsername().equals(username) && !isTokenExpired(token);
					
		}
		else {
			return false;
		}
	}
}
