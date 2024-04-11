package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.util.ErrorStructure;
import com.example.cms.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {
	private BlogPostService blogPostService;

	@Operation(description = "the end point is used to create blogpost as draft", responses = {
			@ApiResponse(responseCode = "200", description = "user create blogpost successfully", content = {
					@Content(schema = @Schema(implementation = ResponseStructure.class)) }),
			@ApiResponse(responseCode = "400", description = "invalid input", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPostDraft(@PathVariable int blogId,
			@RequestBody @Valid BlogPostRequest blogPostRequest) {
		return blogPostService.createBlogPostDraft(blogId, blogPostRequest);
	}
	@Operation(description = "the endpoint is used to update the draft", responses = {
			@ApiResponse(responseCode = "200",description = "updated successfully",content = {@Content(schema = @Schema(implementation = ResponseStructure.class))} )
			,@ApiResponse(responseCode = "400",description = "invalid inputs",content = {@Content(schema = @Schema(implementation = ErrorStructure.class))})})
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId,@RequestBody @Valid BlogPostRequest blogPostRequest){
		return blogPostService.updateDraft(postId,blogPostRequest);
	} 
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	} 
	@GetMapping("/blog-posts/{postId}")
	private ResponseEntity<ResponseStructure<BlogPostResponse>> findById(@PathVariable int postId)
	{
		return blogPostService.findById(postId);
	}
	@GetMapping("/published/blog-posts/{postId}")
	private ResponseEntity<ResponseStructure<BlogPostResponse>> publishedBlogPostFindById(@PathVariable int postId)
	{
		return blogPostService.publishedBlogPostFindById(postId);
	}
}
