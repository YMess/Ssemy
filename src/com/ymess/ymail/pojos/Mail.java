package com.ymess.ymail.pojos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * Contains All the Attributes of Mail
 * @author RAJ
 *
 */
public class Mail {

	  private long mailId;
	  private String mailSubject;
	  private String mailBody;
	  private Set<String> mailTo;
	  private String mailFrom;
	  private Set<String> mailCC;
	  private Set<String> mailBCC;
	  private List<MultipartFile>  mailAttachment;
	  private Boolean isAttachmentAttached;
	  private Set<MultipartFile> mailPicture;
	  private Boolean isPictureAttached;
	  private Date mailSavedTimestamp;
	  private Date mailSentTimestamp;
	  private Set<String> mailThread;
	  private Boolean mailStatus;
	  private Boolean mailRead;
	  private String userFirstName;
	  private String userLastName;
	  
	public long getMailId() {
		return mailId;
	}
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public Set<String> getMailTo() {
		return mailTo;
	}
	public void setMailTo(Set<String> mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public Set<String> getMailCC() {
		return mailCC;
	}
	public void setMailCC(Set<String> mailCC) {
		this.mailCC = mailCC;
	}
	public Set<String> getMailBCC() {
		return mailBCC;
	}
	public void setMailBCC(Set<String> mailBCC) {
		this.mailBCC = mailBCC;
	}
	public List<MultipartFile> getMailAttachment() {
		return mailAttachment;
	}
	public void setMailAttachment(List<MultipartFile> mailAttachment) {
		this.mailAttachment = mailAttachment;
	}
	public boolean getIsAttachmentAttached() {
		return isAttachmentAttached;
	}
	public void setIsAttachmentAttached(boolean isAttachmentAttached) {
		this.isAttachmentAttached = isAttachmentAttached;
	}
	public Set<MultipartFile> getMailPicture() {
		return mailPicture;
	}
	public void setMailPicture(Set<MultipartFile> mailPicture) {
		this.mailPicture = mailPicture;
	}
	public boolean getIsPictureAttached() {
		return isPictureAttached;
	}
	public void setPictureAttached(boolean isPictureAttached) {
		this.isPictureAttached = isPictureAttached;
	}
	public Date getMailSavedTimestamp() {
		return mailSavedTimestamp;
	}
	public void setMailSavedTimestamp(Date mailSavedTimestamp) {
		this.mailSavedTimestamp = mailSavedTimestamp;
	}
	public Date getMailSentTimestamp() {
		return mailSentTimestamp;
	}
	public void setMailSentTimestamp(Date mailSentTimestamp) {
		this.mailSentTimestamp = mailSentTimestamp;
	}
	public Set<String> getMailThread() {
		return mailThread;
	}
	public void setMailThread(Set<String> mailThread) {
		this.mailThread = mailThread;
	}
	public boolean isMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(boolean mailStatus) {
		this.mailStatus = mailStatus;
	}
	public boolean isMailRead() {
		return mailRead;
	}
	public void setMailRead(boolean mailRead) {
		this.mailRead = mailRead;
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
	
}
