package com.bookaro.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookaro.api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pedro<br>
 * Clase para autentificar usuarios<br>
 * Inyecta la dependencia AuthenticationManager
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Constructor
     * @param authenticationManager Recibe un objeto AuthenticationManager
     */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        
        // Filtro endPoing para el login
        setFilterProcessesUrl("/api/user/login");        
       
    }
    
    
	/**
	 * Metodo que comprueba las credenciales del usuario
	 * @param req Recibe un objeto HttpServletRequest
	 * @param res Recibe un objeto HttpServletResponse
	 * @return un objeto Authentication
	 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
        	// Mapeamos el objeto
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword())
            );
        } catch (IOException e) {
        	//System.out.println("prueba");
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que genera un token a partir de las credenciales del usuarios.
     * @param req Recibe un objeto HttpServletRequest
     * @param res Recibe un objeto HttpServletResponse
     * @param chain Recibe un objeto FilterChain
     * @param auth Recibe un objeto Authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
    	 
    	// Se añade el rol y las credenciales a la lista
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		ArrayList<String> roles = new ArrayList<String>();
		for(GrantedAuthority authority:authorities) {
			roles.add(authority.toString());
		}
		
		// Creamos el token a partir de las credenciales del usuario
    	String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())    
                .withClaim("role", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        // Llegara al body el username y el token
    	String body = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername() + " " + token;
        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
