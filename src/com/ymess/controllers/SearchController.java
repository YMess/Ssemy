package com.ymess.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ymess.pojos.Question;
import com.ymess.pojos.SearchParameters;
import com.ymess.util.JSPMappings;
import com.ymess.util.LuceneSearcher;
import com.ymess.util.URLMappings;
import com.ymess.util.YMessCommonUtility;

@Controller
public class SearchController {

	
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
		
		Map<String,String> ids = new HashMap<String,String>();
		try {
			ids = LuceneSearcher.searchIndex(new File(YMessCommonUtility.INDEX_LOCATION), searchParameters.getSearchString(),searchParameters.getCriterion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
		 {
			 if(null != ids && ! ids.isEmpty())
			 {
					//questions = null;
			 }
		 }
		 
		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
		 {
			 	if(null != ids && ! ids.isEmpty())
				{
					//people = null;
				}
		 }

		 if(searchParameters.getCriterion().equalsIgnoreCase(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
		 {
			 	if(null != ids && ! ids.isEmpty())
				{
					//files = null;
				}
		 }
		
		
		List<Question> questions = new ArrayList<Question>();
		
		
		
		model.addAttribute("questions",questions);
		return JSPMappings.SEARCH_RESULTS;
	}
}
