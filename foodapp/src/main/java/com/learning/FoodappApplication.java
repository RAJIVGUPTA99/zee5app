package com.learning;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.learning.dto.EFOODTYPE;
import com.learning.dto.FoodType;
import com.learning.repository.FoodTypeRepository;
import com.learning.service.FoodTypeService;


@SpringBootApplication
public class FoodappApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(FoodappApplication.class,
				args);
		
//		FoodTypeService foodTypeService = applicationContext.getBean(FoodTypeService.class);
//        FoodTypeRepository foodTypeRepository = applicationContext.getBean(FoodTypeRepository.class);
//		
//		FoodType foodType = new FoodType();
//		foodType.setFoodType(EFOODTYPE.FOOD_CHINESE);
//		FoodType foodType2 = new FoodType();
//		foodType2.setFoodType(EFOODTYPE.FOOD_INDIAN);
//		FoodType foodType3 = new FoodType();
//		foodType3.setFoodType(EFOODTYPE.FOOD_MEXICAN);
//		foodTypeService.addFoodType(foodType2);
//		foodTypeService.addFoodType(foodType);
//		foodTypeService.addFoodType(foodType3);
//		Set<FoodType> foodtype = new HashSet<>();
//		
		applicationContext.close();
	}

}
