package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.Blog;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResonse;
import com.example.cms.util.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResonse>> createBlog(int userId,  BlogRequest blogRequest);

}
