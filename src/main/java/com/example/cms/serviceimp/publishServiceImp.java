package com.example.cms.serviceimp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundById;
import com.example.cms.exception.IllegalAccesRequestException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.Publishrepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.requestdto.ScheduleRequest;
import com.example.cms.responsedto.publishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.util.ResponseStructure;

import ch.qos.logback.core.util.FixedDelay;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class publishServiceImp implements PublishService {
	private ResponseStructure<publishResponse> responseStructure;
	private Publishrepository publishrepository;
	private BlogPostRepository blogPostRepository;
	private ScheduleRepository scheduleRepository;

	@Override
	public ResponseEntity<ResponseStructure<publishResponse>> publishBlogPost(int postId,
			PublishRequest publishRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogpost -> {

			if (!blogpost.getBlog().getUser().getEmail().equals(email))
				throw new IllegalAccesRequestException("failed to publish");

			Publish publish = mapToPulish(publishRequest, new Publish());
			if (publish.getSchedule() != null)
				blogpost.setPostType(PostType.SCHEDULED);

			blogpost.setPostType(PostType.PUBLISHED);
			publish.setBlogPost(blogpost);

			return ResponseEntity
					.ok(responseStructure.setStatus(HttpStatus.OK.value()).setMessage("BlogPost Successfully published")
							.setData(mapToPublishResponse(publishrepository.save(publish))));

		}).orElseThrow(() -> new BlogPostNotFoundById("blog is not found by id"));

	}

	@Override
	public ResponseEntity<ResponseStructure<publishResponse>> unpublishBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogpost -> {

			if (!blogpost.getBlog().getUser().getEmail().equals(email))
				throw new IllegalAccesRequestException("failed to publish");
		
			
		
			blogpost.setPostType(PostType.DRAFT);
			blogPostRepository.save(blogpost);
			return ResponseEntity
					.ok(responseStructure.setStatus(HttpStatus.OK.value()).setMessage("BlogPost Successfully published")
							.setData(mapToPublishResponse(blogpost.getPublish())));

		}).orElseThrow(() -> new BlogPostNotFoundById("blog is not found by id"));
	}

	private publishResponse mapToPublishResponse(Publish publish) {
		return publishResponse.builder().publishId(publish.getPublishid())// nullpointerexception
				.seoTitle(publish.getSeoTitle()).seoDescprition(publish.getSeoDescprition())
				.seoTags(publish.getSeoTags()).createdAt(publish.getCreatedAt())// *******
				.lastmodifiedAt(publish.getLastmodifiedAt()).build();
	}

	private Publish mapToPulish(PublishRequest publishReusest, Publish publish) {
		publish.setSeoTitle(publishReusest.getSeoTitle());
		publish.setSeoDescprition(publishReusest.getSeoDescprition());
		publish.setSeoTags(publishReusest.getSeoTags());

		
		if (publishReusest.getSchedule() != null) {
			if(publishReusest.getSchedule().getDateTime().isAfter(LocalDateTime.now())) {
				
//				if(scheduleRepository.existsBy(null))
			Schedule schedule = mapToSchedule(publishReusest.getSchedule(), new Schedule());
            scheduleRepository.save(schedule);
			publish.setSchedule(schedule);}
			else
				throw new RuntimeException("zsgvhga");
		}
		return publish;
	}

	private Schedule mapToSchedule(ScheduleRequest scheduleRequest, Schedule schedule) {
		schedule.setDateTime(scheduleRequest.getDateTime());
		return schedule;
	}
	
}
