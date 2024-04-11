package com.example.cms.requestdto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PublishRequest {
	private int publishid;
	private String seoTitle;
	private String seoDescprition;
	private String seoTags;
	private ScheduleRequest schedule;
}
