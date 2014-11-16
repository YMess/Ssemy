package com.ymess.yticket.pojos;

import java.util.Date;
import java.util.Map;

/**
 * Contains all the Attributes of Ticket
 * @author balaji i
 *
 */
public class Ticket {

	private Long ticketId;
	private String ticketSubject;
	private String ticketBody;
	private Map<String,byte[]> ticketAttachments;
	private Date ticketPostedOn;
	private String ticketPostedBy;
	private String ticketAssignedTo;
	private String ticketAssignedBy;
	private String ticketStatus;
	
	
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
	
}
