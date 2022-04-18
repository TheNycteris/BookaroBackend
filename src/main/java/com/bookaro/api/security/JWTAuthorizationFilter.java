package com.bookaro.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * Clase para la autorizacion de usuarios
 * @author Pedro
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * Constructor
     * @param authManager
     */
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    
    /**
     * Metodo que filtra por el token obtenido
     * @param req Recibe un objeto HttpServletRequest
     * @param res Recibe un objeto HttpServletResponse
     * @param chain Recibe un objeto FilterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    
    /**
     * Metodo que lee el encabezado de autorización y luego usa JWT para validar el token
     * @param request Recibe un objeto HttpServletRequest
     * @return Retorna un objeto UsernamePasswordAuthenticationTokeno null
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        
        if (token != null) {
            // Parseamos el token
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
            // Decodificamos con JWT
            DecodedJWT jwt = JWT.decode(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
            // Añadimos los roles
            Claim claim = jwt.getClaim("role");
            List<String> rolesList = claim.asList(String.class);;
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for(String role:rolesList) {
            	authorities.add(new SimpleGrantedAuthority(role));
    		}
            // Comprobamos si "user" es null antes de crear el objeto "UsernamePasswordAuthenticationToken"
            if (user != null) {                
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
    
    
}