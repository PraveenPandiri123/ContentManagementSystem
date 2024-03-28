package com.example.cms.requestdto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BlogRequest {
	@NotNull(message = "title must be preset")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Title should only contain alphabets")
	private String title;
//	@NotNull(message = "topic must be written")
//	@NotBlank(message = "topic must be written")
	private String[] topics;
	@NotNull(message = "write about the blog")
	@NotBlank(message = "write about the blog")
	private String about;

}
