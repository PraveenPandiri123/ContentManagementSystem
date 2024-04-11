package com.example.cms.responsedto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class publishResponse {
	private int publishId;
	private String seoTitle;
	private String seoDescprition;
	private String seoTags;

	private LocalDateTime createdAt;
	private LocalDateTime lastmodifiedAt;

}
