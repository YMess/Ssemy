package com.ymess.pojos;

import java.util.Date;
import java.util.Set;

/**
 * Contains all the attributes of an Answer
 * @author balaji i
 *
 */
public class Answer {

	private Long answerId;
	private Long questionId;
	private Date answeredTime;
	private String answerDescription;
	private String authorEmailId;
	private Long downvoteCount;
	private Long upvoteCount;
	private Set<String> downvotedUsers;
	private Set<String> upvotedUsers;
	private String firstName;
	private String lastName;
	private String questionDescription;
	
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Date getAnsweredTime() {
		return answeredTime;
	}
	public void setAnsweredTime(Date answeredTime) {
		this.answeredTime = answeredTime;
	}
	public String getAnswerDescription() {
		return answerDescription;
	}
	public void setAnswerDescription(String answerDescription) {
		this.answerDescription = answerDescription;
	}
	public String getAuthorEmailId() {
		return authorEmailId;
	}
	public void setAuthorEmailId(String authorEmailId) {
		this.authorEmailId = authorEmailId;
	}
	public Long getDownvoteCount() {
		return downvoteCount;
	}
	public void setDownvoteCount(Long downvoteCount) {
		this.downvoteCount = downvoteCount;
	}
	public Long getUpvoteCount() {
		return upvoteCount;
	}
	public void setUpvoteCount(Long upvoteCount) {
		this.upvoteCount = upvoteCount;
	}
	public Set<String> getDownvotedUsers() {
		return downvotedUsers;
	}
	public void setDownvotedUsers(Set<String> downvotedUsers) {
		this.downvotedUsers = downvotedUsers;
	}
	public Set<String> getUpvotedUsers() {
		return upvotedUsers;
	}
	public void setUpvotedUsers(Set<String> upvotedUsers) {
		this.upvotedUsers = upvotedUsers;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public String getQuestionDescription() {
		return questionDescription;
	}
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	
}
