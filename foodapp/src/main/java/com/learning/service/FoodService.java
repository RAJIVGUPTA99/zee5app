package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.Food;
import com.learning.exceptions.IdNotFoundException;

public interface FoodService {
	
	public Food addFood(Food food);
	public Food updateFood(String foodId, Food food) throws IdNotFoundException;
	public Food getFoodById(String foodId) throws IdNotFoundException;
	public String deleteFoodById(String foodId) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();

}