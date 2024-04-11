package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BlogPostRequest {
	@NotNull(message = "The title should not be null.")
	private String title;
	private String subTitle;
	private String Summary;
}
