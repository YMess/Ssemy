/**
 * 
 */
package com.ymess.pojos;

import java.util.List;

/**
 * Contains all the Attributes of a keyword
 * @author balaji i
 *
 */
public class Keyword {

	private String keywordName;
	private List<String> contributors;
	private Long contributorCount;
	private List<Long> questionIds;
	private Long questionCount;
	private List<String> viewers;
	private Long viewerCount;
	
	
	public String getKeywordName() {
		return keywordName;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	public List<String> getContributors() {
		return contributors;
	}

	public void setContributors(List<String> contributors) {
		this.contributors = contributors;
	}

	public Long getContributorCount() {
		return contributorCount;
	}

	public void setContributorCount(Long contributorCount) {
		this.contributorCount = contributorCount;
	}

	public List<Long> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<Long> questionIds) {
		this.questionIds = questionIds;
	}

	public Long getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Long questionCount) {
		this.questionCount = questionCount;
	}

	public List<String> getViewers() {
		return viewers;
	}

	public void setViewers(List<String> viewers) {
		this.viewers = viewers;
	}

	public Long getViewerCount() {
		return viewerCount;
	}

	public void setViewerCount(Long viewerCount) {
		this.viewerCount = viewerCount;
	}
	
	
	
	

}
