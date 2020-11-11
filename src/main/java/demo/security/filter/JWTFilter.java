package demo.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.security.jwt.JWTManager;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private JWTManager jwtManager;
	
	@Autowired
	private UserDetailsService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authToken = request.getHeader("X-Authentication-Jwt");
		
		if(authToken != null && authToken.startsWith("Bearer ")) {
			authToken = authToken.substring(7);
		}
		else {
			filterChain.doFilter(request, response);
			return;
		}
		
		String username = jwtManager.getUsername(authToken);
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = service.loadUserByUsername(username);
					
			if(userDetails != null && jwtManager.isValidToken(authToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}
		
		filterChain.doFilter(request, response);
		

	}

}
