package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.Blog;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResonse;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.Valid;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResonse>> createBlog(int userId,  BlogRequest blogRequest);

	ResponseEntity<ResponseStructure<BlogResonse>> findByBlogId(int blogId);

	ResponseEntity<ResponseStructure<BlogResonse>> updateBlog(int blogId, @Valid BlogRequest blogRequest);

	ResponseEntity<Boolean> checkForBlogTitleAvailability(String title);

}
