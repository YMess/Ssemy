/**
 * 
 */
package com.ymess.pojos;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contains all the properties of a Question.
 * @author balaji i
 *
 */
public class Question {
	
	//private Long questionId;
	
	private UUID questionId;

	@NotEmpty(message="Please Add Question Details")
	private String questionDescription;
	
	@NotEmpty(message="Please Add Question Title")
	private String questionTitle;
	
	
	private String authorEmailId;
	private Date createdDate;
	private Date updatedDate;
	private List<String> keywords;
	private String category;
	private String subCategory;
	private String lastAnswer;
	private String firstName;
	private String lastName;
	private Boolean isImageAttached;
	private String questionImageName;

	/** For Receiving the Image from JSP to Controller */
	private MultipartFile questionImage;
	
	/** Image Data in byte[] in DB */
	private byte[] questionImageDB;
	
	private Set<String> topics;
	
	public Set<String> getTopics() {
		return topics;
	}

	public void setTopics(Set<String> topics) {
		this.topics = topics;
	}

	public String getQuestionImageName() {
		return questionImageName;
	}
	
	public void setQuestionImageName(String questionImageName) {
		this.questionImageName = questionImageName;
	}
	public byte[] getQuestionImageDB() {
		return questionImageDB;
	}

	public void setQuestionImageDB(byte[] questionImageDB) {
		this.questionImageDB = questionImageDB;
	}

	public Boolean getIsImageAttached() {
		return isImageAttached;
	}

	public void setIsImageAttached(Boolean isImageAttached) {
		this.isImageAttached = isImageAttached;
	}

	public MultipartFile getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(MultipartFile questionImage) {
		this.questionImage = questionImage;
	}


	public UUID getQuestionId() {
		return questionId;
	}

	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getAuthorEmailId() {
		return authorEmailId;
	}

	public void setAuthorEmailId(String authorEmailId) {
		this.authorEmailId = authorEmailId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getLastAnswer() {
		return lastAnswer;
	}

	public void setLastAnswer(String lastAnswer) {
		this.lastAnswer = lastAnswer;
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
	
	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

}
