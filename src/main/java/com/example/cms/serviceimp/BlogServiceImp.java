package com.example.cms.serviceimp;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotspecifiedException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResonse;
import com.example.cms.service.BlogService;
import com.example.cms.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImp implements BlogService {
	private ResponseStructure<BlogResonse> responseStructure;
	private UserRepository userRepository;
	private BlogRepository blogRepository;

	@Override
	public ResponseEntity<ResponseStructure<BlogResonse>> createBlog(int userId, BlogRequest blogRequest) {

		return userRepository.findById(userId).map(user -> {
			if (blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new TitleAlreadyExistsException("Given title name is alredy present");
			if (blogRequest.getTopics().length < 1)
				throw new TopicNotspecifiedException("failed to create blog");
			
			Blog blog = mapToBlog(blogRequest, new Blog());

			blog.setUsers(Arrays.asList(user));
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("blog created successfully").setData(mapToBlogResponset(blogRepository.save(blog))));
		}).orElseThrow(() -> new UserNotFoundException("failed to create blog"));

	}

	private Blog mapToBlog(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	private BlogResonse mapToBlogResponset(Blog blog) {
		return BlogResonse.builder().blogId(blog.getBlogId()).title(blog.getTitle()).topics(blog.getTopics())
				.createdAt(blog.getCreatedAt())
				.lastModifiedAt(blog.getLastModifiedAt())
				.about(blog.getAbout()).build();
	}

}
