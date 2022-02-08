package com.learning.service;

import java.util.Optional;
import com.learning.dto.FoodType;

import com.learning.exceptions.IdNotFoundException;

public interface FoodTypeService {
	
	public String addFoodType(FoodType foodType);
	public void deleteFoodType(int foodTypeId) throws IdNotFoundException;
	public Optional<FoodType> getFoodTypeById(int foodTypeId);

}
