package com.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.learning.dto.EFOODTYPE;
import com.learning.dto.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
//	Retrieving food items by food type

	List<Food> findAllByFoodType(EFOODTYPE foodType);

}
