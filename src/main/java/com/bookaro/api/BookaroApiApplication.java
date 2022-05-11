package com.bookaro.api;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
 * @author Pedro
 * 
 * Clase que inicia la aplicación.
 *
 */
@SpringBootApplication
public class BookaroApiApplication {

	/**
	 * Metodo Main
	 * @param args Recibe un array de tipo String
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookaroApiApplication.class, args);
	}
	
	/**
	 * Enlace al objeto BCryptPasswordEncoder
	 * @return Retorna un objeto BCryptPasswordEncoder
	 */
	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); 
    }	
	
	
	/////////////////////////////////////////////////////////////////////
	@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }
	
	private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }	
	/////////////////////////////////////////////////////////////////////
}
