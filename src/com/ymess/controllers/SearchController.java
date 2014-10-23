package com.ymess.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymess.pojos.Question;
import com.ymess.pojos.SearchParameters;
import com.ymess.pojos.User;
import com.ymess.util.AutoSuggest;
import com.ymess.util.JSPMappings;
import com.ymess.util.LuceneSearcher;
import com.ymess.util.URLMappings;
import com.ymess.util.YMessCommonUtility;

@Controller
public class SearchController {

	/**
	 * Retrieves Results based on search parameters
	 * @author balaji i
	 * @param searchParameters
	 * */
	@RequestMapping(value = URLMappings.SEARCH)
	String getSearchResults(@ModelAttribute("searchParameters") SearchParameters searchParameters,BindingResult result,Model model)
	{
		if(searchParameters.getSearchString() != null && searchParameters.getSearchString().length() == 0)
		{
			result.rejectValue("searchString", "Search String","Please Enter Search String");
		}
		
		if(result.hasErrors())
		{
			return JSPMappings.SEARCH_RESULTS;
		}
		
		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
		 {
			 Map<String,Question> questions = new HashMap<String, Question>();
			 try {
				 questions = LuceneSearcher.searchQuestions(new File(YMessCommonUtility.INDEX_LOCATION_QUESTIONS), searchParameters.getSearchString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			 if(null != questions && ! questions.isEmpty())
			 {
				 model.addAttribute("questions",questions);
				 model.addAttribute("questionCount",questions.size());
			 }
		 }
		 
		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
		 {
			 Map<String,User> users = new HashMap<String, User>();
			 try {
				 users = LuceneSearcher.searchUsers(new File(YMessCommonUtility.INDEX_LOCATION_PEOPLE), searchParameters.getSearchString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			 if(null != users && ! users.isEmpty())
			 {
				 model.addAttribute("users",users);
				 model.addAttribute("userCount",users.size());
			 }
		 }

		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
		 {
			 Map<String, com.ymess.pojos.File> files = new HashMap<String, com.ymess.pojos.File>();
			 try {
				 files = LuceneSearcher.searchFiles(new File(YMessCommonUtility.INDEX_LOCATION_FILES), searchParameters.getSearchString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			 if(null != files && ! files.isEmpty())
			 {
				 model.addAttribute("files",files);
				 model.addAttribute("fileCount",files.size());
			 }
		 }
		
		return JSPMappings.SEARCH_RESULTS;
	}
	
	/**
	 * Fetches the related topics(Suggestions) once the user starts to type something
	 * @author balaji i
	 * @param topic
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = URLMappings.GET_RELATED_TOPICS, produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET,headers="Accept=*/*")
	@ResponseBody
	public String getRelatedTopics(@RequestParam("topic") String topic,HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException
	{
		AutoSuggest autoSuggest = new AutoSuggest();
		List<LookupResult> words = autoSuggest.returnSuggestedWords(topic);
		
		System.out.println("Reached");
		List<String> suggestions = new ArrayList<String>();
		if(!words.isEmpty())
		{
			for (LookupResult word : words) {
				suggestions.add((String) word.key.toString().replace("<b>", "").replace("</b>", ""));
			}
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		return mapper.writeValueAsString(suggestions);
	}
	
}
