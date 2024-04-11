package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.Valid;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPostDraft(int blogId, BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId, @Valid BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findById(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> publishedBlogPostFindById(int postId);

}
