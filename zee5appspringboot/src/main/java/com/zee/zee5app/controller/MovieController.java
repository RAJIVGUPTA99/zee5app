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

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.repository.MovieRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@PostMapping("/addMovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {
		if (movieRepository.existsById(movie.getId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Movie Id already exists!"));
		}
		if (movieRepository.existsByMovieNameAndLanguage(movie.getMovieName(), movie.getLanguage())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Movie name already taken!"));
		}
		movieRepository.save(movie);
		return ResponseEntity
				.status(201)
				.body(new MessageResponse("Movie inserted successfully"));
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllMovies() {
		List<Movie> movies = movieRepository.findAll();
		if (movies.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.body(new MessageResponse("No record found"));
		}
		return ResponseEntity.ok(movies);
	}

}