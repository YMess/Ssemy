/**
 * 
 */
package com.ymess.pojos;

import java.util.Date;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * Contains all the features of a File
 * @author balaji i
 *
 */
public class File {
	
	private Long fileId;
	private String authorEmailId;
	private Boolean shared;
	private Date uploadedTime;
	private String filename;
	private Set<String> topics;
	private String fileType;
	private String fileSize;
	
	private MultipartFile fileData;
	private byte[] fileDataDb;
	
	
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getAuthorEmailId() {
		return authorEmailId;
	}
	public void setAuthorEmailId(String authorEmailId) {
		this.authorEmailId = authorEmailId;
	}
	
	public Date getUploadedTime() {
		return uploadedTime;
	}
	public void setUploadedTime(Date uploadedTime) {
		this.uploadedTime = uploadedTime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Set<String> getTopics() {
		return topics;
	}
	public void setTopics(Set<String> topics) {
		this.topics = topics;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	public byte[] getFileDataDb() {
		return fileDataDb;
	}
	public void setFileDataDb(byte[] fileDataDb) {
		this.fileDataDb = fileDataDb;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	
	@Override
	public int hashCode() {
		return Integer.parseInt(this.fileId.toString());
	}
	

}
