package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.EFOODTYPE;
import com.learning.dto.Food;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

public interface FoodService {
	
	public Food addFood(Food food) throws AlreadyExistsException;
	public Food updateFood(Long foodId, Food food) throws IdNotFoundException;
	public Food getFoodById(Long foodId) throws IdNotFoundException;
	public String deleteFoodById(Long foodId) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();
	public Optional<List<Food>> getByFoodType(EFOODTYPE foodType);

}
