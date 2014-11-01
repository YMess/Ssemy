package com.ymess.ymail.pojos;

import java.util.Date;
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
	  private Set<String> mailFrom;
	  private Set<String> mailCC;
	  private Set<String> mailBCC;
	  private Set<MultipartFile> mailAttachment;
	  private Set<MultipartFile> mailPicture;
	  private Date mailSavedTimestamp;
	  private Date mailSentTimestamp;
	  private Set<String> mailThread;
	  private boolean mailSent;
	  private boolean mailDraft;
	  private boolean mailInbox;
	  private boolean mailStarred;
	  private boolean mailImportant;
	  private boolean mailTrash;
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
	public Set<String> getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(Set<String> mailFrom) {
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
	public Set<MultipartFile> getMailAttachment() {
		return mailAttachment;
	}
	public void setMailAttachment(Set<MultipartFile> mailAttachment) {
		this.mailAttachment = mailAttachment;
	}
	public Set<MultipartFile> getMailPicture() {
		return mailPicture;
	}
	public void setMailPicture(Set<MultipartFile> mailPicture) {
		this.mailPicture = mailPicture;
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
	public boolean isMailSent() {
		return mailSent;
	}
	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}
	public boolean isMailDraft() {
		return mailDraft;
	}
	public void setMailDraft(boolean mailDraft) {
		this.mailDraft = mailDraft;
	}
	public boolean isMailInbox() {
		return mailInbox;
	}
	public void setMailInbox(boolean mailInbox) {
		this.mailInbox = mailInbox;
	}
	public boolean isMailStarred() {
		return mailStarred;
	}
	public void setMailStarred(boolean mailStarred) {
		this.mailStarred = mailStarred;
	}
	public boolean isMailImportant() {
		return mailImportant;
	}
	public void setMailImportant(boolean mailImportant) {
		this.mailImportant = mailImportant;
	}
	public boolean isMailTrash() {
		return mailTrash;
	}
	public void setMailTrash(boolean mailTrash) {
		this.mailTrash = mailTrash;
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
