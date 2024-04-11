package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.entity.Publish;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.publishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {
	private PublishService publishService;

	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<publishResponse>> publishBlogPost(@PathVariable int postId,@RequestBody  @Valid PublishRequest publishRequest) {
		System.out.println("jgvchgcfcgfcg");
		return publishService.publishBlogPost(postId,publishRequest);
	}
	
	@PutMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<publishResponse>> unpublishBlogPost(@PathVariable int postId) {
		return publishService.unpublishBlogPost(postId);
	}
}
