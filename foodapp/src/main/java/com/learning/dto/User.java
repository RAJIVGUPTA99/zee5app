package com.learning.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "register", uniqueConstraints = 
{@UniqueConstraint(columnNames = "userName"), 
		@UniqueConstraint(columnNames = "email")})
public class User implements Comparable<User>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "regId")
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String userName;

	@NotBlank
	@Size(min = 3, max = 20)
	private String name;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String address;

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id.compareTo(o.getId());
	}
	
	public User(String userName, String name, String email, String password, String address) {
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	//@JsonIgnore
	//maintain in 3rd table
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "regId"), 
	inverseJoinColumns = @JoinColumn(name = "roleId") )//relationship btwn registered user(regId) and role(roleId)
	private Set<Role> roles = new HashSet<>();
	
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Login login;
	

}
