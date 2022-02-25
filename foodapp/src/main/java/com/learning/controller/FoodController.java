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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.EFOODTYPE;
import com.learning.dto.Food;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.FoodService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
//	POST request for adding food item
	@PostMapping("/addFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addFood(@RequestBody Food food) throws AlreadyExistsException {
		Food result = foodService.addFood(food);
		return ResponseEntity.status(201).body(result);
	}
	
//	GET request for retrieving food item by id
	@GetMapping("/{foodId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getFoodById(@PathVariable("foodId") Long foodId) throws IdNotFoundException {
		Food result = foodService.getFoodById(foodId);
		return ResponseEntity.ok(result);
	}
	
//	PUT request for updating food item by id
	@PutMapping("/{foodId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateFood(@RequestBody @PathVariable("foodId") Long foodId,  Food food) throws IdNotFoundException {
		Food result = foodService.updateFood(foodId, food);
		return ResponseEntity.status(200).body(result);
	}
	
//	GET request for retrieving all food items
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllFood() {
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "No record found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
//	GET request for retrieving food item by food type
	@GetMapping("/foodType/{foodType}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getFoodByType(@PathVariable("foodType") EFOODTYPE foodType) {
		Optional<List<Food>> optional = foodService.getByFoodType(foodType);
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Sorry Food Not Found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
//	DELETE request for deleting food item by id
	@DeleteMapping("/{foodId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteFoodById(@PathVariable("foodId") Long foodId) throws IdNotFoundException {
		foodService.deleteFoodById(foodId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "Food item deleted");
		return ResponseEntity.status(200).body(map);
	}

}
