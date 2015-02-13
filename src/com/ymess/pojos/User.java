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
    private String registrationType;

	@NotEmpty(message="Please Enter First Name")
	private String firstName;
	
	@NotEmpty(message="Please Enter Last Name ")
	private String lastName;
	
	private String password;
	private String confirmPassword;
	private String imageName;
	private String tagline;
	private String designation;
	private String organization;
	private String website;
	private String phoneNumber;
	private String country;
	
	private Date profileLastUpdated;
	
	private Set<String> interests;
	private Set<String> previousOrganizations;
	

	private Map<String,Date> followingUsers;
	private Map<Long,Date> userTickets;
	
	/** For Receiving the Image from JSP to Controller */
	private MultipartFile userImage;
	
	/** Image Data in byte[] in DB */
	private byte[] userImageData;
	
	private long userId;
	private String roleType;
	private Date registrationTimestamp;
	private String department;
	private String speciality;
	private String phoneCode; 
	private String faxPhone;
	private String homePhone;
	private String officePhone;
	private String mobilePhone;
	private String address1;
	private String address2;
	private String city;
	private String pinCode;
	private Boolean isRegisterNewsLetter;
	private Boolean isAgreedService;
	private String createdBy;
	private String ceatedTimestamp;
	private String updatedBy;
	private String updatedTimestamp;
	
	public String getFaxPhone() {
		return faxPhone;
	}

	public void setFaxPhone(String faxPhone) {
		this.faxPhone = faxPhone;
	}

	
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Date getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	public void setRegistrationTimestamp(Date registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean getIsRegisterNewsLetter() {
		return isRegisterNewsLetter;
	}

	public void setIsRegisterNewsLetter(Boolean isRegisterNewsLetter) {
		this.isRegisterNewsLetter = isRegisterNewsLetter;
	}

	public Boolean getIsAgreedService() {
		return isAgreedService;
	}

	public void setIsAgreedService(Boolean isAgreedService) {
		this.isAgreedService = isAgreedService;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCeatedTimestamp() {
		return ceatedTimestamp;
	}

	public void setCeatedTimestamp(String ceatedTimestamp) {
		this.ceatedTimestamp = ceatedTimestamp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
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

	public Map<Long, Date> getUserTickets() {
		return userTickets;
	}

	public void setUserTickets(Map<Long, Date> userTickets) {
		this.userTickets = userTickets;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	
	

}
