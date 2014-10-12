package com.ymess.pojos;

import java.util.Date;
import java.util.Set;

/**
 * Contributes all attributes of timeline
 * @author Raj
 *
 */
public class TimeLine {

	private String userEmailId;
	private String userTimestamp;
	private String activity;
	private String userFirstName;
	private String userLastName;
	
	private Date joinedJoiningDate;
	private boolean hasJoined;
	
	private String profileUpdatedFirstName;
	private String profileUpdatedLastName;
	private String profileUpdatedOrganization;
	private String profileUpdatedDesignation;
	private Set<String> profileUpdatedPreviousOrganizations;
	private Set<String> profileUpdatedInterests;
	private boolean hasUpdatedProfile;
	
	private Long questionPostedId;
	private String questionPostedTitle;
	private String questionPostedDesc;
	private boolean questionIsImageAttached;
	private Set<String> questionTopics;
	private Date questionUpdatedDate;
	private String questionLastAnswer;
	private boolean hasPostedQuestion;

	private Long answeredQuestionId;
	private String answeredQuestionTitle;
	private String answeredQuestionDesc;
	private String answeredQuestionEmailId;
	private boolean answeredQuestionIsImageAttached;
	private Set<String> answeredQuestionTopics;
	public String getQuestionPostedTitle() {
		return questionPostedTitle;
	}
	public void setQuestionPostedTitle(String questionPostedTitle) {
		this.questionPostedTitle = questionPostedTitle;
	}
	public String getAnsweredQuestionTitle() {
		return answeredQuestionTitle;
	}
	public void setAnsweredQuestionTitle(String answeredQuestionTitle) {
		this.answeredQuestionTitle = answeredQuestionTitle;
	}
	public boolean isAnsweredQuestionIsImageAttached() {
		return answeredQuestionIsImageAttached;
	}
	public void setAnsweredQuestionIsImageAttached(
			boolean answeredQuestionIsImageAttached) {
		this.answeredQuestionIsImageAttached = answeredQuestionIsImageAttached;
	}
	public Set<String> getAnsweredQuestionTopics() {
		return answeredQuestionTopics;
	}
	public void setAnsweredQuestionTopics(Set<String> answeredQuestionTopics) {
		this.answeredQuestionTopics = answeredQuestionTopics;
	}
	private Long answeredAnsweredId;
	private String answeredAnsweredDesc;
	private boolean hasAnsweredQuestion;
	
	private String followingFirstName;
	private String followingLastName;
	private String followingEmailId;
	private boolean hasFollowing;
	
	private Long upvotedQuestionId;
	private String upvotedQuestionDesc;
	private String upvotedQuestionEmailId;
	private Long upvotedAnswerId;
	private String upvotedAnswerDesc;
	private boolean hasUpvoted;
	
	private Long  downvotedQuestionId;
	private String  downvotedQuestionDesc;
	private String  downvotedQuestionEmailId;
	private Long  downvotedAnswerId;
	private String  downvotedAnswerDesc;
	private boolean  hasDownvoted;
	
	private Long  sharedQuestionId;
	private String  sharedQuestionDesc;
	private String  sharedQuestionEmailId;
	private boolean  hasShared;
	
	private Long  likedQuestionId;
	private String likedQuestionDesc;
	private String  likedQuestionEmailId;
	private boolean hasLikedQuestion;
	
	private String likedAnswerId;
	private String likedAnswerDesc;
	private String likedAnswerEmailId;
	private boolean hasLikedAnswer;
	
	
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserTimestamp() {
		return userTimestamp;
	}
	public void setUserTimestamp(String userTimestamp) {
		this.userTimestamp = userTimestamp;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public Date getJoinedJoiningDate() {
		return joinedJoiningDate;
	}
	public void setJoinedJoiningDate(Date joinedJoiningDate) {
		this.joinedJoiningDate = joinedJoiningDate;
	}
	public boolean isHasJoined() {
		return hasJoined;
	}
	public void setHasJoined(boolean hasJoined) {
		this.hasJoined = hasJoined;
	}
	public String getProfileUpdatedFirstName() {
		return profileUpdatedFirstName;
	}
	public void setProfileUpdatedFirstName(String profileUpdatedFirstName) {
		this.profileUpdatedFirstName = profileUpdatedFirstName;
	}
	public String getProfileUpdatedLastName() {
		return profileUpdatedLastName;
	}
	public void setProfileUpdatedLastName(String profileUpdatedLastName) {
		this.profileUpdatedLastName = profileUpdatedLastName;
	}
	public String getProfileUpdatedOrganization() {
		return profileUpdatedOrganization;
	}
	public void setProfileUpdatedOrganization(String profileUpdatedOrganization) {
		this.profileUpdatedOrganization = profileUpdatedOrganization;
	}
	public String getProfileUpdatedDesignation() {
		return profileUpdatedDesignation;
	}
	public void setProfileUpdatedDesignation(String profileUpdatedDesignation) {
		this.profileUpdatedDesignation = profileUpdatedDesignation;
	}
	public Set<String> getProfileUpdatedPreviousOrganizations() {
		return profileUpdatedPreviousOrganizations;
	}
	public void setProfileUpdatedPreviousOrganizations(
			Set<String> profileUpdatedPreviousOrganizations) {
		this.profileUpdatedPreviousOrganizations = profileUpdatedPreviousOrganizations;
	}
	public Set<String> getProfileUpdatedInterests() {
		return profileUpdatedInterests;
	}
	public void setProfileUpdatedInterests(Set<String> profileUpdatedInterests) {
		this.profileUpdatedInterests = profileUpdatedInterests;
	}
	public boolean isHasUpdatedProfile() {
		return hasUpdatedProfile;
	}
	public void setHasUpdatedProfile(boolean hasUpdatedProfile) {
		this.hasUpdatedProfile = hasUpdatedProfile;
	}
	public Long getQuestionPostedId() {
		return questionPostedId;
	}
	public void setQuestionPostedId(Long questionPostedId) {
		this.questionPostedId = questionPostedId;
	}
	public String getQuestionPostedDesc() {
		return questionPostedDesc;
	}
	public void setQuestionPostedDesc(String questionPostedDesc) {
		this.questionPostedDesc = questionPostedDesc;
	}
	public boolean isHasPostedQuestion() {
		return hasPostedQuestion;
	}
	public void setHasPostedQuestion(boolean hasPostedQuestion) {
		this.hasPostedQuestion = hasPostedQuestion;
	}
	public Long getAnsweredQuestionId() {
		return answeredQuestionId;
	}
	public void setAnsweredQuestionId(Long answeredQuestionId) {
		this.answeredQuestionId = answeredQuestionId;
	}
	public String getAnsweredQuestionDesc() {
		return answeredQuestionDesc;
	}
	public void setAnsweredQuestionDesc(String answeredQuestionDesc) {
		this.answeredQuestionDesc = answeredQuestionDesc;
	}
	public String getAnsweredQuestionEmailId() {
		return answeredQuestionEmailId;
	}
	public void setAnsweredQuestionEmailId(String answeredQuestionEmailId) {
		this.answeredQuestionEmailId = answeredQuestionEmailId;
	}
	public Long getAnsweredAnsweredId() {
		return answeredAnsweredId;
	}
	public void setAnsweredAnsweredId(Long answeredAnsweredId) {
		this.answeredAnsweredId = answeredAnsweredId;
	}
	public String getAnsweredAnsweredDesc() {
		return answeredAnsweredDesc;
	}
	public void setAnsweredAnsweredDesc(String answeredAnsweredDesc) {
		this.answeredAnsweredDesc = answeredAnsweredDesc;
	}
	public boolean isHasAnsweredQuestion() {
		return hasAnsweredQuestion;
	}
	public void setHasAnsweredQuestion(boolean hasAnsweredQuestion) {
		this.hasAnsweredQuestion = hasAnsweredQuestion;
	}
	public String getFollowingFirstName() {
		return followingFirstName;
	}
	public void setFollowingFirstName(String followingFirstName) {
		this.followingFirstName = followingFirstName;
	}
	public String getFollowingLastName() {
		return followingLastName;
	}
	public void setFollowingLastName(String followingLastName) {
		this.followingLastName = followingLastName;
	}
	public String getFollowingEmailId() {
		return followingEmailId;
	}
	public void setFollowingEmailId(String followingEmailId) {
		this.followingEmailId = followingEmailId;
	}
	public boolean isHasFollowing() {
		return hasFollowing;
	}
	public void setHasFollowing(boolean hasFollowing) {
		this.hasFollowing = hasFollowing;
	}
	public Long getUpvotedQuestionId() {
		return upvotedQuestionId;
	}
	public void setUpvotedQuestionId(Long upvotedQuestionId) {
		this.upvotedQuestionId = upvotedQuestionId;
	}
	public String getUpvotedQuestionDesc() {
		return upvotedQuestionDesc;
	}
	public void setUpvotedQuestionDesc(String upvotedQuestionDesc) {
		this.upvotedQuestionDesc = upvotedQuestionDesc;
	}
	public String getUpvotedQuestionEmailId() {
		return upvotedQuestionEmailId;
	}
	public void setUpvotedQuestionEmailId(String upvotedQuestionEmailId) {
		this.upvotedQuestionEmailId = upvotedQuestionEmailId;
	}
	public Long getUpvotedAnswerId() {
		return upvotedAnswerId;
	}
	public void setUpvotedAnswerId(Long upvotedAnswerId) {
		this.upvotedAnswerId = upvotedAnswerId;
	}
	public String getUpvotedAnswerDesc() {
		return upvotedAnswerDesc;
	}
	public void setUpvotedAnswerDesc(String upvotedAnswerDesc) {
		this.upvotedAnswerDesc = upvotedAnswerDesc;
	}
	public boolean isHasUpvoted() {
		return hasUpvoted;
	}
	public void setHasUpvoted(boolean hasUpvoted) {
		this.hasUpvoted = hasUpvoted;
	}
	public Long getDownvotedQuestionId() {
		return downvotedQuestionId;
	}
	public void setDownvotedQuestionId(Long downvotedQuestionId) {
		this.downvotedQuestionId = downvotedQuestionId;
	}
	public String getDownvotedQuestionDesc() {
		return downvotedQuestionDesc;
	}
	public void setDownvotedQuestionDesc(String downvotedQuestionDesc) {
		this.downvotedQuestionDesc = downvotedQuestionDesc;
	}
	public String getDownvotedQuestionEmailId() {
		return downvotedQuestionEmailId;
	}
	public void setDownvotedQuestionEmailId(String downvotedQuestionEmailId) {
		this.downvotedQuestionEmailId = downvotedQuestionEmailId;
	}
	public Long getDownvotedAnswerId() {
		return downvotedAnswerId;
	}
	public void setDownvotedAnswerId(Long downvotedAnswerId) {
		this.downvotedAnswerId = downvotedAnswerId;
	}
	public String getDownvotedAnswerDesc() {
		return downvotedAnswerDesc;
	}
	public void setDownvotedAnswerDesc(String downvotedAnswerDesc) {
		this.downvotedAnswerDesc = downvotedAnswerDesc;
	}
	public boolean isHasDownvoted() {
		return hasDownvoted;
	}
	public void setHasDownvoted(boolean hasDownvoted) {
		this.hasDownvoted = hasDownvoted;
	}
	public Long getSharedQuestionId() {
		return sharedQuestionId;
	}
	public void setSharedQuestionId(Long sharedQuestionId) {
		this.sharedQuestionId = sharedQuestionId;
	}
	public String getSharedQuestionDesc() {
		return sharedQuestionDesc;
	}
	public void setSharedQuestionDesc(String sharedQuestionDesc) {
		this.sharedQuestionDesc = sharedQuestionDesc;
	}
	public String getSharedQuestionEmailId() {
		return sharedQuestionEmailId;
	}
	public void setSharedQuestionEmailId(String sharedQuestionEmailId) {
		this.sharedQuestionEmailId = sharedQuestionEmailId;
	}
	public boolean isHasShared() {
		return hasShared;
	}
	public void setHasShared(boolean hasShared) {
		this.hasShared = hasShared;
	}
	public Long getLikedQuestionId() {
		return likedQuestionId;
	}
	public void setLikedQuestionId(Long likedQuestionId) {
		this.likedQuestionId = likedQuestionId;
	}
	public String getLikedQuestionDesc() {
		return likedQuestionDesc;
	}
	public void setLikedQuestionDesc(String likedQuestionDesc) {
		this.likedQuestionDesc = likedQuestionDesc;
	}
	public String getLikedQuestionEmailId() {
		return likedQuestionEmailId;
	}
	public void setLikedQuestionEmailId(String likedQuestionEmailId) {
		this.likedQuestionEmailId = likedQuestionEmailId;
	}
	public boolean isHasLikedQuestion() {
		return hasLikedQuestion;
	}
	public void setHasLikedQuestion(boolean hasLikedQuestion) {
		this.hasLikedQuestion = hasLikedQuestion;
	}
	public String getLikedAnswerId() {
		return likedAnswerId;
	}
	public void setLikedAnswerId(String likedAnswerId) {
		this.likedAnswerId = likedAnswerId;
	}
	public String getLikedAnswerDesc() {
		return likedAnswerDesc;
	}
	public void setLikedAnswerDesc(String likedAnswerDesc) {
		this.likedAnswerDesc = likedAnswerDesc;
	}
	public String getLikedAnswerEmailId() {
		return likedAnswerEmailId;
	}
	public void setLikedAnswerEmailId(String likedAnswerEmailId) {
		this.likedAnswerEmailId = likedAnswerEmailId;
	}
	public boolean isHasLikedAnswer() {
		return hasLikedAnswer;
	}
	public void setHasLikedAnswer(boolean hasLikedAnswer) {
		this.hasLikedAnswer = hasLikedAnswer;
	}
	public boolean isQuestionIsImageAttached() {
		return questionIsImageAttached;
	}
	public void setQuestionIsImageAttached(boolean questionIsImageAttached) {
		this.questionIsImageAttached = questionIsImageAttached;
	}
	public Set<String> getQuestionTopics() {
		return questionTopics;
	}
	public void setQuestionTopics(Set<String> questionTopics) {
		this.questionTopics = questionTopics;
	}
	public Date getQuestionUpdatedDate() {
		return questionUpdatedDate;
	}
	public void setQuestionUpdatedDate(Date questionUpdatedDate) {
		this.questionUpdatedDate = questionUpdatedDate;
	}
	public String getQuestionLastAnswer() {
		return questionLastAnswer;
	}
	public void setQuestionLastAnswer(String questionLastAnswer) {
		this.questionLastAnswer = questionLastAnswer;
	}
	
	
	
}
