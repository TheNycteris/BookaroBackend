package com.bookaro.api.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.bookaro.api.services.MyUserDetailsService;



@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class RestConfig extends WebSecurityConfigurerAdapter {
	
	 @Autowired
     private MyUserDetailsService userDetailsService;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()   
        		// EndPoints: /api/user
                .antMatchers(HttpMethod.GET, "/api/user/all").hasAnyRole("ADMIN", "MOD")
                .antMatchers(HttpMethod.GET, "/api/user/insert").hasAnyRole("ADMIN", "MOD")
                // EndPoints: /api/book
                .antMatchers(HttpMethod.GET, "/api/book/all").hasAnyRole("ADMIN", "MOD", "USER")
                .antMatchers(HttpMethod.GET, "/api/book/name").hasAnyRole("ADMIN", "MOD", "USER")
                // EndPoints: /api/client
                .antMatchers(HttpMethod.GET, "/api/client/subscription").hasAnyRole("ADMIN", "MOD")
                // EndPoints: /api/client
                .antMatchers(HttpMethod.GET, "/api/employee/position/").hasAnyRole("ADMIN", "MOD")                
                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll()
                
                	.and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
    
    
    
   
}