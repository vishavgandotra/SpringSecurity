package demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.stereotype.Component;

@Component
@EnableGlobalMethodSecurity(jsr250Enabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

	@Autowired
	private RoleHierarchy hierarchy;
	
	@Override
	protected AccessDecisionManager accessDecisionManager() {
		AffirmativeBased affirmativeBased = (AffirmativeBased) super.accessDecisionManager();
		affirmativeBased.getDecisionVoters().add(new RoleHierarchyVoter(hierarchy));
		return affirmativeBased;
	}
	
	@Bean
	public RoleHierarchy getRoleHier() {
		RoleHierarchyImpl impl = new RoleHierarchyImpl();
		impl.setHierarchy("ROLE_ADMIN > ROLE_USER");
		return impl;
	}
}
