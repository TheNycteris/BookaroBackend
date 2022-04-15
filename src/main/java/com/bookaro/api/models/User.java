package com.bookaro.api.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name = "userbookaro")
@JsonIgnoreProperties(ignoreUnknown=true)
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
//@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn( name="employeeClient" )
//@DiscriminatorColumn(name="employeeClient")
public class User  implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id_user;
	
	@Column(unique = true, nullable = false)
	private String username; 
	
	@Column(unique = true, nullable = false)
	private String password;
	
	@Column(unique = true, nullable = true)
	private String email;
	
	private String name, surname, dni, address; 	
	private int age;
	
	/*@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="roles", joinColumns= @JoinColumn(name="id"))
	private List<String> roles;*/
	
	private String roles;
	
	
	
	public User(Long id_user, String username, String password, String name, String surname, String dni, String address,
			String email, int age, /*List<String> roles*/String roles) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.email = email;
		this.age = age;
		//this.roles = roles;
		this.roles = roles;
	}

	public User() {}

	// Getter/Setter
	public Long getId() {
		return id_user;
	}

	public void setId(Long id) {
		this.id_user = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}*/
	
	public String getRole() {
		return roles;
	}

	public void setRole(String role) {
		this.roles = role;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		if(this.roles.equals("ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			
			//authorities.add(new SimpleGrantedAuthority(role));
		} else if(this.roles.equals("MOD")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MOD"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return authorities;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
