package com.learning.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/foods")
public class FoodController {
	
	@Autowired
	FoodService foodService;
	
	//add record
	@PostMapping("/addFood")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {
		
	
		Food result = foodService.addFood(food);
		return ResponseEntity.status(201).body(result);
		
		}
	
	//retrieve single record
	@GetMapping("/{foodId}")
	public ResponseEntity<?> getFoodById(@PathVariable("foodId") Long foodId) throws IdNotFoundException{
		Food result = foodService.getFoodById(foodId);
		return ResponseEntity.ok(result);	
		
	}
	
	//retrieve all records
	@GetMapping("/all")
	public ResponseEntity<?> getAllFoodDetails(){
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());	
		
	}
	
	@DeleteMapping("/delete/{foodId}")
	public ResponseEntity<?> deleteFoodById(@PathVariable("foodId") Long foodId) throws IdNotFoundException, SQLException
	{
		String result = foodService.deleteFoodById(foodId);
		Map<String, String> map = new HashMap<>();
		map.put("message", "food item deleted");
		return ResponseEntity.status(201).body(result);
	}
	
	@PutMapping("/update/{foodId}")
	public ResponseEntity<?> updateFood(@PathVariable("foodId") Long foodId, @RequestBody Food food) throws IdNotFoundException
	{
		Food result = foodService.updateFood(foodId, food);
		return ResponseEntity.status(201).body(result);
	}

}
