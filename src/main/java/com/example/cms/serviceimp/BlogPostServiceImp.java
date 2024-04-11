package com.example.cms.serviceimp;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundBYId;
import com.example.cms.exception.BlogPostNotFoundById;
import com.example.cms.exception.IllegalAccesRequestException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.publishResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImp implements BlogPostService {
	private ResponseStructure<BlogPostResponse> responseStructure;
	private BlogPostRepository blogPostRepository;
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ContributionPanelrepository panelrepository;

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPostDraft(int blogId,
			BlogPostRequest blogPostRequest) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepository.findByEmail(email).map(user -> {
			return blogRepository.findById(blogId).map(blog -> {
				System.out.println("jhdvajhvahgsvgvchag");
				System.out.println(blog.getUser());
				if (!blog.getUser().getEmail().equals(email)
						&& !panelrepository.existsByPanelIdAndUsers(blog.getContributionpanel().getPanelId(), user))
					throw new IllegalAccesRequestException("faild to create draft");

				BlogPost blogPost = mapToBlogPost(blogPostRequest, new BlogPost());
				blogPost.setPostType(PostType.DRAFT);
				blogPost.setBlog(blog);

				return ResponseEntity
						.ok(responseStructure.setStatus(HttpStatus.OK.value()).setMessage("Blogpost saved successfully")
								.setData(mapToBlogPostResponse(blogPostRepository.save(blogPost))));

			}).orElseThrow(() -> new BlogNotFoundBYId("blog is not found by given id"));
		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,
			BlogPostRequest blogPostRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByEmail(email).map(user -> {
			return blogPostRepository.findById(postId).map(blogpost -> {

				if (!blogpost.getBlog().getUser().getEmail().equals(email) && !panelrepository
						.existsByPanelIdAndUsers(blogpost.getBlog().getContributionpanel().getPanelId(), user))
					throw new IllegalAccesRequestException("faild to create draft");

				return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
						.setMessage("Successfully updated draft").setData(mapToBlogPostResponse(
								blogPostRepository.save(mapToBlogPost(blogPostRequest, blogpost)))));
			}).orElseThrow(() -> new BlogPostNotFoundById("blogpost is notfound"));

		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogpost -> {
			if (!blogpost.getBlog().getUser().getEmail().equals(email) || !blogpost.getCreatedBy().equals(email))
				throw new IllegalAccesRequestException("faild to delete draft");
			Blog blog = blogpost.getBlog();
			blogPostRepository.delete(blogpost);
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("Successfully deleted draft").setData(mapToBlogPostResponse(blogpost)));

		}).orElseThrow(() -> new BlogNotFoundBYId("blog post is not found"));
	}

	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {

		BlogPostResponse response = BlogPostResponse.builder().postId(blogPost.getPostId()).title(blogPost.getTitle())
				.subTitle(blogPost.getSubTitle()).Summary(blogPost.getSummary()).postType(blogPost.getPostType())
				.createAt(blogPost.getCreateAt()).lastModifiedAt(blogPost.getLastModifiedAt())
				.createdBy(blogPost.getCreatedBy()).lastModifiedBy(blogPost.getLastModifiedBy()).build();
		if (blogPost.getPublish() != null)// if it is not null then it add publishresponse
			response.setPublishResponse(mapToPublishResponse(blogPost.getPublish()));
		return response;
	}

	private BlogPost mapToBlogPost(BlogPostRequest blogPostRequest, BlogPost blogPost) {
		blogPost.setTitle(blogPostRequest.getTitle());
		blogPost.setSubTitle(blogPostRequest.getSubTitle());
		blogPost.setSummary(blogPostRequest.getSummary());
		return blogPost;
	}

	private publishResponse mapToPublishResponse(Publish publish) {

		return publishResponse.builder().publishId(publish.getPublishid()).seoTitle(publish.getSeoTitle())
				.seoDescprition(publish.getSeoDescprition()).seoTags(publish.getSeoTags()).build();

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>>findById(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost -> {
			if (!blogPost.getBlog().getUser().getEmail().equals(email) || blogPost.getCreatedBy().equals(email))
				throw new IllegalAccesRequestException("failed to find blog");
			return ResponseEntity.ok(responseStructure.setMessage("blog found Successfully")
					.setStatus(HttpStatus.FOUND.value()).setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(() -> new BlogNotFoundBYId("blog post is not found by id"));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> publishedBlogPostFindById(int postId) {
		return blogPostRepository.findByPostIdAndPostType(postId,PostType.PUBLISHED).map(blogPost -> {// here publishresponse showing null coz
			return ResponseEntity.ok(responseStructure.setMessage("blog found Successfully")
					.setStatus(HttpStatus.FOUND.value()).setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(() -> new BlogNotFoundBYId("blog post isnot found by id"));
		
	}
	
}