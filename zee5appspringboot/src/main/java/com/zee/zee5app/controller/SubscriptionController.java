package com.zee.zee5app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.repository.SubscriptionRepository;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.security.services.UserDetailsImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/addSubscription")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addSubscription(@Valid @RequestBody Subscription subscription) {
		if (subscriptionRepository.existsById(subscription.getId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Subscription Id already exists!"));
		}
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long id = userDetailsImpl.getId();
		Optional<User> optional = userRepository.findById(id);
		subscription.setUser(optional.get());
		subscriptionRepository.save(subscription);
		return ResponseEntity
				.status(201)
				.body(new MessageResponse("Subscription inserted successfully"));
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllSubscriptions() {
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		if (subscriptions.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.body(new MessageResponse("No record found"));
		}
		return ResponseEntity.ok(subscriptions);
	}

}