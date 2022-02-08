package com.learning.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "register")
public class Register implements Comparable<Register>{
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "regId")
	private String id;
	
	@Size(max=50)
	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	@Size(max=100)
	@NotBlank
	private String password;
	
	@Size(max=200)
	@NotBlank
	private String address;

	@Override
	public int compareTo(Register o) {
		// TODO Auto-generated method stub
		return this.id.compareTo(o.getId());
	}
	
	@OneToOne(mappedBy = "register", cascade = CascadeType.ALL)
	private Login login;
	

}
