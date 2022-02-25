package com.learning.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ManyToAny;

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
@Table(name = "food")
public class Food implements Comparable<Food> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long foodId;
	
	@Size(max=50)
	@NotBlank
	private String foodName;
	
	//private EFOODTYPE foodType;
	private String description;
	private String foodPic;
	private float foodCost;
	
	@Enumerated(EnumType.STRING)
	private EFOODTYPE foodType;

	@Override
	public int compareTo(Food o) {
		// TODO Auto-generated method stub
		return this.foodId.compareTo(o.getFoodId());
	}
	
	
	
	
	

}
