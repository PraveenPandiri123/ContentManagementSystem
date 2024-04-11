package com.example.cms.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.entity.BlogPost;
import com.example.cms.enums.PostType;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleJobs {
	private BlogPostRepository blogPostRepository;

	@Scheduled(fixedDelay = 10000l)
	public void logDateTime() {
		// System.out.println(LocalDateTime.now());
		List<BlogPost> list = blogPostRepository.findByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULED)
				.stream().map(post ->{
					 post.setPostType(PostType.PUBLISHED);
					 return post;
				}).collect(Collectors.toList());
				
//		for (BlogPost blogPost : list) {
//			blogPost.setPostType(PostType.PUBLISHED);
//		}
		blogPostRepository.saveAll(list);
	}

}
