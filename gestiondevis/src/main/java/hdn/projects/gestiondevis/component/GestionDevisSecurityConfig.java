package hdn.projects.gestiondevis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Profile({"prod"})
public class GestionDevisSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired()
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	 
	 @Bean
	    @Override
	    public UserDetailsService userDetailsService() {

	        UserDetails user =
	                User.withUsername("admin")
	                        .password(bCryptPasswordEncoder.encode("admin"))
	                        .roles("USER")
	                        .build();

	        return new InMemoryUserDetailsManager(user);
	    }
	
}
