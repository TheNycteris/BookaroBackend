package com.bookaro.api.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * 
 * @author Pedro 
 * 
 * <li>Clase Padre de los usuarios. </li>
 * <li>De esta clase heredan Employee y Client. </li>
 * <li>Todos los usuarios comparte la misma table "SINGLE_TABLE": </li>
 * <li>La columna que discrimina el tipo de usuario es "employeeClient" </li>
 * <li>Para el cliente es "CL" y para el Employee es "EM"</li> 
 * <li>Implementa la interfaz UserDetails</li>
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "userbookaro")
@JsonIgnoreProperties(ignoreUnknown=true)
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="employeeClient" )
public class User implements UserDetails {
	
	// ******* Atributos de clase ******* 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id_user;
	
	@Column(unique = true, nullable = false)
	private String username; 
	
	@Column(unique = true, nullable = false)
	private String password;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(unique = true, nullable = true)
	private String dni;
	
	private String name, surname, address, roles; 	
	private int age;
	
	/**
	 * Constructor	
	 * @param id_user ID de usuario. Tipo de dato String
	 * @param username Usuario. Tipo de dato String
	 * @param password Password del usuario. Tipo de dato String
	 * @param name Nombre del usuario. Tipo de dato String
	 * @param surname Apellido del usuario. Tipo de dato String
	 * @param dni DNI del usuario. Tipo de dato String
	 * @param address Dirección del usuario. Tipo de dato String
	 * @param email Email del usuario. Tipo de dato String
	 * @param age Edad del usuario. Tipo de dato int
	 * @param roles Role para la autorización del usuario. Tipo de dato String
	 */
	public User(Long id_user, String username, String password, 
			    String name, String surname, String dni, String address,
			    String email, int age, String roles) {		
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.email = email;
		this.age = age;		
		this.roles = roles;
	}

	/**
	 * Constructor vacio.
	 */
	public User() {}

	// ************ Getter/Setter ************
	
	/**
	 * Getter ID de usuario
	 * @return retorna el id del usuario
	 */
	public Long getId() {
		return id_user;
	}

	/**
	 * Setter id de usuario
	 * @param id Recibe un parametro de tipo Long
	 */
	public void setId(Long id) {
		this.id_user = id;
	}

	/**
	 * Getter username
	 * @return retorn el username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter username
	 * @param username recibe parametro tipo String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter password
	 * @return retorna el password del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter password
	 * @param password recibe el password como string
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter que obtiene el nombre del usuario
	 * @return retorna el nombre
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter nombre del usuario
	 * @param name recibe el nombre por string
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter apellido
	 * @return retorn un string con el apellido
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Setter apellido
	 * @param surname recibe un string con el apellido
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter DNI
	 * @return Devuelve el dni en string
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Setter dni
	 * @param dni recibe el dni como string
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Setter direccion del usuario
	 * @return retorna la direccion
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter direccion
	 * @param address recibe la dirección como string
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter edad 
	 * @return retorna la edad del usuario
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Setter edad
	 * @param age recibe un int con la edad
	 */
	public void setAge(int age) {
		this.age = age;
	}	
	
	/**
	 * Getter email
	 * @return retorna el email de usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter email
	 * @param email recibe un string con el email
	 */
	public void setEmail(String email) {
		this.email = email;
	}	
	
	/**
	 * Getter role
	 * @return retorna el rol
	 */
	public String getRole() {
		return roles;
	}

	/**
	 * Setter role
	 * @param role recibe un string con el role
	 */
	public void setRole(String role) {
		this.roles = role;
	}
	
	// ************ Metodos heredados de la interfaz UserDetails ************
	
	/**
	 * Metodo para la autorizacion del usuario por role
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		if(this.roles.equals("ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));			
		} else if(this.roles.equals("MOD")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MOD"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return authorities;
	}
	
	/**
	 * Metodo que define si la cuenta tiene expiracion.
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Metodo que define si la cuenta es No bloqueda
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Metodo que define si las credenciales pueden expirar
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Metodo que define si la cuenta esta habilitada.
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
