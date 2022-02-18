package com.zee.zee5app.controller;

import java.util.List;
import java.util.Optional;

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

import com.zee.zee5app.dto.Episodes;
import com.zee.zee5app.dto.Series;
import com.zee.zee5app.payload.request.EpisodesRequest;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.repository.EpisodeRepository;
import com.zee.zee5app.repository.SeriesRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/episode")
public class EpisodesController {
	
	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private SeriesRepository seriesRepository;
	
	@PostMapping("/addEpisode")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addEpisode(@Valid @RequestBody EpisodesRequest episodesRequest) {
		if (episodeRepository.existsById(episodesRequest.getId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Episode Id already exists!"));
		}
		if (seriesRepository.existsById(episodesRequest.getSerId()) == false) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Series Id doesnot exist!"));
		}
		Optional<Series> optional = seriesRepository.findById(episodesRequest.getSerId());
		Episodes episode = new Episodes(episodesRequest.getId(),
				episodesRequest.getName(),
				episodesRequest.getLength(),
				episodesRequest.getLocation(),
				episodesRequest.getTrailer(),
				optional.get());
		episodeRepository.save(episode);
		return ResponseEntity
				.status(201)
				.body(new MessageResponse("Episode inserted successfully"));
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllEpisodes() {
		List<Episodes> episodes = episodeRepository.findAll();
		if (episodes.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.body(new MessageResponse("No record found"));
		}
		return ResponseEntity.ok(episodes);
	}

}