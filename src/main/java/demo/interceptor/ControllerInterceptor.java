package demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import demo.security.jwt.JWTManager;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private JWTManager jwtManager;
	
	@Autowired
	private UserDetailsService service;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String authToken = request.getHeader("X-Authentication-Jwt");
		
		if(authToken != null && authToken.startsWith("Bearer ")) {
			authToken = authToken.substring(7);
		}
		else return;
		
		if(modelAndView == null) return;
		
		modelAndView.addObject("admin", "false");
		modelAndView.addObject("login", "false");
		modelAndView.addObject("username", "");
		
		String username = jwtManager.getUsername(authToken);
		
		if(username != null) {
			
			UserDetails userDetails = service.loadUserByUsername(username);
					
			if(userDetails != null && jwtManager.isValidToken(authToken, userDetails)) {
				boolean isAdmin = userDetails.getAuthorities()
									.stream()
									.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
				
				modelAndView.addObject("admin", isAdmin ? "true" : "false");
				modelAndView.addObject("login", "true");
				modelAndView.addObject("username", username);
			}
			
		}
		
	}
}
