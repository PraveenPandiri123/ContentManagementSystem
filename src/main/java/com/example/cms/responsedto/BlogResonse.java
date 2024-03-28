package com.example.cms.responsedto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class BlogResonse{
	
	private int blogId;
	private String title;
	private String[] topics;
	private String about;

	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
}
