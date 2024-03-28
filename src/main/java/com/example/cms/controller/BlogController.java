package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResonse;
import com.example.cms.service.BlogService;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogController {
	private BlogService blogService;

	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResonse>> createBlog(@PathVariable int userId, @RequestBody @Valid BlogRequest blogRequest) {
		return blogService.createBlog(userId, blogRequest);
	}
}
