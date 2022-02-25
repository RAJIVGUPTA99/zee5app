package com.learning.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.EFOODTYPE;
import com.learning.dto.Food;

import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;

import com.learning.service.FoodService;
import com.learning.utils.Fileutils;

import lombok.ToString;

@ToString

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	FoodRepository foodRepository;
	
	
	
	@Autowired
	Fileutils fileutils;
	
    //Insert a new record in the table
	@Override
	public Food addFood(Food food) throws AlreadyExistsException {
		// TODO Auto-generated method stub
//		if(foodRepository.findById(food.getFoodId()) != null) {
//			throw new AlreadyExistsException("this record already exists");
//		}
   
	String source = food.getFoodPic();
    String destination = "C:\\Users\\rajiv.gupta\\Downloads\\movies\\foodStore";
    File file = new File(source);
    byte[] data = null;
  
    try {
			data = fileutils.readFile(file);
			fileutils.writeFile(data, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
	Food food2 = foodRepository.save(food);
		if (food2 != null) {
			return food2;
		} else {
			return null;
		}
	}
    
	//Updating the existing record
	@Override
	public Food updateFood(Long foodId, Food food) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(!this.foodRepository.existsById(foodId))
			throw new IdNotFoundException("Sorry food not found");
		
		return foodRepository.save(food);
	}
    
	//retrieve a record by id
	@Override
	public Food getFoodById(Long foodId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Food> optional =  foodRepository.findById(foodId);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Sorry food not found");
		}
		else {
			return optional.get();
		}
	}
    
	//Delete the record by id
	@Override
	public String deleteFoodById(Long foodId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Food optional;
		try {
			optional = this.getFoodById(foodId);
			if(optional==null) {
				throw new IdNotFoundException("Sorry food not found");
			}
			else {
				foodRepository.deleteById(foodId);
				return "food item deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}
    
	//Retrieve all details
	@Override
	public Optional<List<Food>> getAllFoodDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

	@Override
	public Optional<List<Food>> getByFoodType(EFOODTYPE foodType) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAllByFoodType(foodType));
	}


  


}
