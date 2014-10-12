/**
 * 
 */
package com.ymess.pojos;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contains all the Attributes of an User
 * @author balaji i
 */
public class User {

	@NotEmpty(message="Please Enter an Email Id")
	@Email(message="Please Enter a Valid Email Address")
	private String userEmailId;

	@NotEmpty(message="Please Enter First Name")
	private String firstName;
	
	@NotEmpty(message="Please Enter Last Name ")
	private String lastName;
	
	private String password;

	private String confirmPassword;
	
	private Set<String> interests;
	
	private String tagline;

	private String designation;
	private String organization;
	private Set<String> previousOrganizations;
	private Date profileLastUpdated;
	private Map<String,Date> followingUsers;
	
	/** For Receiving the Image from JSP to Controller */
	private MultipartFile userImage;
	
	/** Image Data in byte[] in DB */
	private byte[] userImageData;
	private String imageName;
	
	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	public Set<String> getPreviousOrganizations() {
		return previousOrganizations;
	}

	public void setPreviousOrganizations(Set<String> previousOrganizations) {
		this.previousOrganizations = previousOrganizations;
	}
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public Date getProfileLastUpdated() {
		return profileLastUpdated;
	}

	public void setProfileLastUpdated(Date profileLastUpdated) {
		this.profileLastUpdated = profileLastUpdated;
	}

	public Map<String,Date> getFollowingUsers() {
		return followingUsers;
	}

	public void setFollowingUsers(Map<String,Date> followingUsers) {
		this.followingUsers = followingUsers;
	}

	public Set<String> getInterests() {
		return interests;
	}

	public void setInterests(Set<String> interests) {
		this.interests = interests;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public MultipartFile getUserImage() {
		return userImage;
	}

	public void setUserImage(MultipartFile userImage) {
		this.userImage = userImage;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getUserImageData() {
		return userImageData;
	}

	public void setUserImageData(byte[] userImageData) {
		this.userImageData = userImageData;
	}

	
	
	

}
