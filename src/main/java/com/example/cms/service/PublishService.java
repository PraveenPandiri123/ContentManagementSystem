package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.publishResponse;
import com.example.cms.util.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<publishResponse>> publishBlogPost(int postId, PublishRequest publishRequest);

	ResponseEntity<ResponseStructure<publishResponse>> unpublishBlogPost(int postId);

}
