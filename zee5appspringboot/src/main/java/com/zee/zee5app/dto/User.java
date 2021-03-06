package com.zee.zee5app.dto;

import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zee.zee5app.utils.CustomListSerializer;
import com.zee.zee5app.utils.CustomListSerializer2;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//write @Data and then press ctrl+space then enter to get the lombok
@Setter
@Getter
@NoArgsConstructor

//we use this method to override instead of other one used below coz when we change anything later, it can handle on its own
@EqualsAndHashCode
@ToString
//ORM mapping purpose
@Entity //entity class is used for ORM - from javax
//to customize table name
@Table(name = "register", uniqueConstraints = 
{@UniqueConstraint(columnNames = "userName"), 
		@UniqueConstraint(columnNames = "email")})
public class User implements Comparable<User>{

	@Id //it will consider this column as primary key
	//camel naming conventions are converted to snake case i.e. reg_id
	@Column(name = "regId")
//	@Length(min = 6)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Size(max=20)
	private String userName;
	
	@Size(max=50)
	@NotBlank
	private String firstName;
	
	@Size(max=50)
	@NotBlank
	private String lastName;
	
	@Size(max=50)
	@Email
	private String email;
	
	@Size(max=100)
	@NotBlank
	private String password;
	
	
	private BigInteger contactNumber;
	
	

	@Override
	public int compareTo(User o) {
		 //TODO Auto-generated method stub
		//ascending
		return this.id.compareTo(o.getId());
	
		//descending order
		//return o.id.compareTo(this.getId())
	}
	
	public User(String userName,String email, String password, String firstName, String lastName) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	//@JsonIgnore
	//maintain in 3rd table
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "regId"), 
	inverseJoinColumns = @JoinColumn(name = "roleId") )//relationship btwn registered user(regId) and role(roleId)
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	//@JsonIgnore
	//@JsonSerialize(using = CustomListSerializer2.class)
	//@JsonIgnoreProperties({"hibernateLazyInitializer","handler","subscription"})
	private Subscription subscription;
	
	@OneToOne(mappedBy = "register", cascade = CascadeType.ALL)
    //@JsonSerialize(using = CustomListSerializer.class)
	//@JsonIgnore
	//@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
	private Login login;
	
	

}