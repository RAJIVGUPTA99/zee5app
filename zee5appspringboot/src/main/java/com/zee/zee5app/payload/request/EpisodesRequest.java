package com.zee.zee5app.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EpisodesRequest {
	
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotNull
	private int length;
	@NotBlank
	private String location;
	private String trailer;
	@NotBlank
	private String serId;

}