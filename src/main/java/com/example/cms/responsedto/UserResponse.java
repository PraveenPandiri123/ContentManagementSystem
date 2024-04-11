package com.example.cms.responsedto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	
	private int userId;
	private String userName;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	
}
