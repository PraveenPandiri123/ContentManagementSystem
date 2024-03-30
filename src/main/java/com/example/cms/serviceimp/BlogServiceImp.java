package com.example.cms.serviceimp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.contributionPanel;
import com.example.cms.exception.BlogNotFoundBYId;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotspecifiedException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResonse;
import com.example.cms.service.BlogService;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImp implements BlogService {
	private ResponseStructure<BlogResonse> responseStructure;
	private UserRepository userRepository;
	private BlogRepository blogRepository;
    private ContributionPanelrepository contributionPanelrepository;
	@Override
	public ResponseEntity<ResponseStructure<BlogResonse>> createBlog(int userId, BlogRequest blogRequest) {

		return userRepository.findById(userId).map(user -> {
			if (blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new TitleAlreadyExistsException("Given title name is alredy present");
			if (blogRequest.getTopics().length < 1)
				throw new TopicNotspecifiedException("failed to create blog");

			Blog blog = mapToBlog(blogRequest, new Blog());
			blog.setContributionpanel(contributionPanelrepository.save(new contributionPanel()));
			blog.setUser(user);
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("blog created successfully").setData(mapToBlogResponset(blogRepository.save(blog))));
		}).orElseThrow(() -> new UserNotFoundException("failed to create blog"));

	}
	@Override
	public ResponseEntity<Boolean> checkForBlogTitleAvailability(String title) {
	
		return ResponseEntity.ok(blogRepository.existsByTitle(title));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogResonse>> findByBlogId(int blogId) {
		return blogRepository.findById(blogId).map(blog -> {
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("log found successfully").setData(mapToBlogResponset(blog)));
		}).orElseThrow(() -> new BlogNotFoundBYId("blog is not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResonse>> updateBlog(int blogId,  BlogRequest blogRequest) {

		return blogRepository.findById(blogId).map(blog -> {
			if (blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new TitleAlreadyExistsException("Given title name is alredy present");
			if (blogRequest.getTopics().length < 1)
				throw new TopicNotspecifiedException("failed to create blog");
			return ResponseEntity
					.ok(responseStructure.setStatus(HttpStatus.OK.value()).setMessage("blog updated successfully")
							.setData(mapToBlogResponset(blogRepository.save(mapToBlog(blogRequest, blog)))));
		}).orElseThrow(() -> new BlogNotFoundBYId("blog not found by given id"));
	}

	private Blog mapToBlog(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	private BlogResonse mapToBlogResponset(Blog blog) {
		return BlogResonse.builder().blogId(blog.getBlogId()).title(blog.getTitle()).topics(blog.getTopics())
				.createdAt(blog.getCreatedAt()).lastModifiedAt(blog.getLastModifiedAt()).about(blog.getAbout()).build();
	}



}
