package com.ymess.pojos;

import java.util.Map;

/**
 * Contains all the Attributes of Topic
 * @author balaji i
 *
 */
public class Topic {

	private String topicName;
	private Long fileCount;
	private Map<Long,String> fileIdsAndAuthorEmails;
	
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public Long getFileCount() {
		return fileCount;
	}
	public void setFileCount(Long fileCount) {
		this.fileCount = fileCount;
	}
	public Map<Long, String> getFileIdsAndAuthorEmails() {
		return fileIdsAndAuthorEmails;
	}
	public void setFileIdsAndAuthorEmails(Map<Long, String> fileIdsAndAuthorEmails) {
		this.fileIdsAndAuthorEmails = fileIdsAndAuthorEmails;
	}
	
}
