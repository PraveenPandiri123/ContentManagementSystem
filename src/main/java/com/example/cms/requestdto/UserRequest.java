package com.example.cms.requestdto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserRequest {
	@NotBlank(message = "User must be contain")
	private String userName;
	@Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "given email format invalid")
	@Column(unique = true)
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;
}
