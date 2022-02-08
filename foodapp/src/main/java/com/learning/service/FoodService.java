package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.EFOODTYPE;
import com.learning.dto.Food;
import com.learning.dto.FoodType;
import com.learning.exceptions.AlreadyExistsException;
import com.learning.exceptions.IdNotFoundException;

public interface FoodService {
	
	public Food addFood(Food food) throws AlreadyExistsException;
	public Food updateFood(String foodId, Food food) throws IdNotFoundException;
	public Food getFoodById(String foodId) throws IdNotFoundException;
	public String deleteFoodById(String foodId) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();
	//public Food getFoodByType(FoodType EFOODTYPE, Food food);

}
