package com.ymess.ymail.pojos;

import java.util.Date;
import java.util.Set;


public class Folder {

	private long folderId;
	private String folderName;
	private Date createdTime;
	private Date modifiedTime;
    private String userEmailId;
    private Set<String> ruleTo;
    private Set<String> ruleFrom;
    private Set<String> ruleCC;
    private Set<String> ruleBCC;
    private String ruleSubject;
	private int mailCount;
	private boolean excludeInbox;
	
	
	
	
	
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
	public int getMailCount() {
		return mailCount;
	}
	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}
	public boolean getExcludeInbox() {
		return excludeInbox;
	}
	public void setExcludeInbox(boolean excludeInbox) {
		this.excludeInbox = excludeInbox;
	}
	public String getRuleSubject() {
		return ruleSubject;
	}
	public void setRuleSubject(String ruleSubject) {
		this.ruleSubject = ruleSubject;
	}
	public Set<String> getRuleTo() {
		return ruleTo;
	}
	public void setRuleTo(Set<String> ruleTo) {
		this.ruleTo = ruleTo;
	}
	public Set<String> getRuleFrom() {
		return ruleFrom;
	}
	public void setRuleFrom(Set<String> ruleFrom) {
		this.ruleFrom = ruleFrom;
	}
	public Set<String> getRuleCC() {
		return ruleCC;
	}
	public void setRuleCC(Set<String> ruleCC) {
		this.ruleCC = ruleCC;
	}
	public Set<String> getRuleBCC() {
		return ruleBCC;
	}
	public void setRuleBCC(Set<String> ruleBCC) {
		this.ruleBCC = ruleBCC;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}
