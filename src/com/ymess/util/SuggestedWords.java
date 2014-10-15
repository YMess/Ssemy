/**
 * 
 */
package com.ymess.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.suggest.Lookup.LookupResult;

/**
 * @author balaji i
 *
 */
public class SuggestedWords {

	 public List<String> returnSuggestedWords(String enteredText )
	 {
		
		 AutoSuggest autoSuggest = new AutoSuggest();
		 List<String> returnedWords=new ArrayList<String>();
		 
		 try{
			 
			 //Fetching topic suggestions for the entered text by User
			 List<LookupResult>suggestedWords = autoSuggest.returnSuggestedWords(enteredText);
			 
			 String word="";
			 //List of LookUpResult to List of String Conversion
			 if(null!=suggestedWords||suggestedWords.size()!=0)
			 {
				 for(int i=0; i<suggestedWords.size();i++)
				 {
					 word = suggestedWords.get(i).key.toString();
					
					 //Removing Unwanted Character Sequence Returned by Lucene (Highlighted Text)
					 word = word.replaceAll("<b>", "");
					 word = word.replaceAll("</b>", "");
					 returnedWords.add(word);
					 
				 }
				 
			 }
			
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
		 return returnedWords;	 
	 }
}
