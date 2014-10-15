package com.ymess.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.suggest.DocumentDictionary;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author BalajiI
 * This Class is used to Auto Suggest Keywords on User Search Topics
 */
public class AutoSuggest
{
	public List<LookupResult> returnSuggestedWords(String enteredText)
	{
		//File containing Indexes for Searched Keywords and their Frequency
		 File indexDirectory = new File(YMessCommonUtility.KEYWORD_INDEXES);
		 
		 //File created for AnalysingInfixSuggester containing it's own version of Indexes used to suggest Keywords
		 File suggesterDirectory = new File(YMessCommonUtility.SUGGESTER_INDEXES);
		 
		 //Suggester Result 
		 List<LookupResult> results= null;
		
		try
		{
		
			   //Used to read pre-generated Indexes from indexDirectory
				FSDirectory topicDirectory = FSDirectory.open(indexDirectory); 
				
				//Creating DocumentDictionary to read Indexes
				DocumentDictionary documentDictionary = new DocumentDictionary(IndexReader.open(topicDirectory), "topics", "topic_count");
				
				//Open Suggester Directory
				FSDirectory fsDirectoryForSuggesterIndexes= FSDirectory.open(suggesterDirectory);
				
				//AnalyzingInfixSuggester - Analyses the input text and then suggests matches based on prefix matches to any tokens in the indexed text 
				AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(Version.LUCENE_CURRENT, fsDirectoryForSuggesterIndexes, new StandardAnalyzer(Version.LUCENE_CURRENT));

				//Builds up a new internal Lookup representation based on the given documentDictionary
				 suggester.build(documentDictionary);
				
				 //LookUp Results containing Top 5 Matched Values
				 results = suggester.lookup(enteredText, true, 5);
				 
				 //Close Directories
				 suggester.close();
				 fsDirectoryForSuggesterIndexes.close();
				 topicDirectory.close();
		
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("File not found");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if(null != results || results.size() != 0)
			return results;
		
		else throw new EmptyResultDataAccessException(1);
	}
}