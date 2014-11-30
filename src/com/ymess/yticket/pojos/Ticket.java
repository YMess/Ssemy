package com.ymess.yticket.pojos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contains all the Attributes of Ticket
 * @author balaji i
 *
 */
@Table(value="ticket_details")
public class Ticket {

	@Column(value="ticket_id")
	private Long ticketId;
	
	
	
	@Column(value="ticket_subject")
	private String ticketSubject;
	
	@Column(value="ticket_body")
	private String ticketBody;
	
	
	private Map<String,byte[]> ticketAttachments;
	
	@Column(value="ticket_posted_on")
	private Date ticketPostedOn;
	
	@Column(value="ticket_posted_by")
	private String ticketPostedBy;
	
	@Column(value="ticket_assigned_to")
	private String ticketAssignedTo;
	
	@Column(value="ticket_assigned_by")
	private String ticketAssignedBy;
	
	@Column(value="ticket_status")
	private String ticketStatus;
	
	private List<MultipartFile> attachments;
	
	//@Column(value="ticket_subject")
	private Boolean isAttachmentAttached;
	
	
	@Override
	public int hashCode() {
		return Integer.parseInt(ticketId.toString());
	}
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketSubject() {
		return ticketSubject;
	}
	public void setTicketSubject(String ticketSubject) {
		this.ticketSubject = ticketSubject;
	}
	public String getTicketBody() {
		return ticketBody;
	}
	public void setTicketBody(String ticketBody) {
		this.ticketBody = ticketBody;
	}
	public Map<String, byte[]> getTicketAttachments() {
		return ticketAttachments;
	}
	public void setTicketAttachments(Map<String, byte[]> ticketAttachments) {
		this.ticketAttachments = ticketAttachments;
	}
	public Date getTicketPostedOn() {
		return ticketPostedOn;
	}
	public void setTicketPostedOn(Date ticketPostedOn) {
		this.ticketPostedOn = ticketPostedOn;
	}
	public String getTicketPostedBy() {
		return ticketPostedBy;
	}
	public void setTicketPostedBy(String ticketPostedBy) {
		this.ticketPostedBy = ticketPostedBy;
	}
	public String getTicketAssignedTo() {
		return ticketAssignedTo;
	}
	public void setTicketAssignedTo(String ticketAssignedTo) {
		this.ticketAssignedTo = ticketAssignedTo;
	}
	public String getTicketAssignedBy() {
		return ticketAssignedBy;
	}
	public void setTicketAssignedBy(String ticketAssignedBy) {
		this.ticketAssignedBy = ticketAssignedBy;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public List<MultipartFile> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MultipartFile> attachments) {
		this.attachments = attachments;
	}

	public Boolean getIsAttachmentAttached() {
		return isAttachmentAttached;
	}

	public void setIsAttachmentAttached(Boolean isAttachmentAttached) {
		this.isAttachmentAttached = isAttachmentAttached;
	}
	
}
