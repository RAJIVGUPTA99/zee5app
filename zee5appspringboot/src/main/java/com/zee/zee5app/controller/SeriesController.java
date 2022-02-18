package com.zee.zee5app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.repository.SeriesRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/series")
public class SeriesController {
	
	@Autowired
	private SeriesRepository seriesRepository;
	
	@PostMapping("/addSeries")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addSeries(@Valid @RequestBody Series series) {
		if (seriesRepository.existsById(series.getId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Series Id already exists!"));
		}
		if (seriesRepository.findBySeriesNameAndLanguage(series.getSeriesName(), series.getLanguage())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Series name already taken!"));
		}
		seriesRepository.save(series);
		return ResponseEntity
				.status(201)
				.body(new MessageResponse("Series inserted successfully"));
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllSeries() {
		List<Series> series = seriesRepository.findAll();
		if(series.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.body(new MessageResponse("No record found"));
		}
		return ResponseEntity.ok(series);
	}

}