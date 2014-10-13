package com.ymess.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/**
 * Retrieves Results from Indexes
 * @author balaji i
 *
 */
public class LuceneSearcher {
	
	//Maximum number of records to be fetched
	private static final int MAX_HITS = 100;
	
	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String,String> searchIndex(File indexLocation, String searchString, String searchCriterion) throws Exception
	{
		@SuppressWarnings("deprecation")
		Version luceneVersion = Version.LUCENE_CURRENT;
		Directory directory = null;
			try
			{
				directory = FSDirectory.open(indexLocation);
			}
			catch(Exception exception)
			{
				throw new FileNotFoundException("Indexes not found at "+indexLocation);
			}
    	MultiFieldQueryParser multiFieldQueryParser = null;
    	BooleanQuery booleanQuery = new BooleanQuery();
    	TermRangeQuery termRangeQuery = null;

    	//To get the date 90 days back(lowerDate)
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DAY_OF_YEAR, - 90);
    	Date ninetyDaysBack = calendar.getTime();
    	
    	String upperDate = DateTools.dateToString(new Date(),Resolution.SECOND);
    	String lowerDate = DateTools.dateToString(ninetyDaysBack,Resolution.SECOND);	 
    	 
    	if(searchCriterion.equalsIgnoreCase(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
    		termRangeQuery = new TermRangeQuery("updated_date",new BytesRef(lowerDate),new BytesRef(upperDate), true, true);
    	
    	if(searchCriterion.equals(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
    		termRangeQuery = new TermRangeQuery("profile_last_updated",new BytesRef(lowerDate),new BytesRef(upperDate), true, true);
    	
    	if(searchCriterion.equals(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
    		termRangeQuery = new TermRangeQuery("upload_time",new BytesRef(lowerDate),new BytesRef(upperDate), true, true);
    	
    			if(searchCriterion.equalsIgnoreCase(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
    			{
    					//Setting the boosts for search parameters
		    			HashMap<String,Float> boosts = new HashMap<String,Float>();
		    			boosts.put("question_title", (float) 10 );
		    			boosts.put("topics", (float) 7.5 );
		    			boosts.put("question_desc", (float) 10 );
		    			boosts.put("updated_date", (float) 5 );
		    			
		    			String[] fields = new String[] {"question_title","topics","question_desc","updated_date"};
		    			multiFieldQueryParser = new MultiFieldQueryParser(luceneVersion, fields,new StandardAnalyzer(luceneVersion),boosts);
		    			booleanQuery.add(multiFieldQueryParser.parse(searchString), BooleanClause.Occur.MUST);
    	    	} 
    			
    			if(searchCriterion.equals(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
    			{
    				//Setting the boosts for search parameters
	    			HashMap<String,Float> boosts = new HashMap<String,Float>();
	    			boosts.put("first_name", (float) 10 );
	    			boosts.put("last_name", (float) 7.5 );
	    			boosts.put("email_id", (float) 5 );
	    			boosts.put("profile_last_updated", (float) 3 );
	    			
	    			String[] fields = new String[] {"first_name","last_name","profile_last_updated","email_id"};
	    			multiFieldQueryParser = new MultiFieldQueryParser(luceneVersion, fields,new StandardAnalyzer(luceneVersion),boosts);
	    			booleanQuery.add(multiFieldQueryParser.parse(searchString), BooleanClause.Occur.MUST);
    			}
    			
    			if(searchCriterion.equals(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
    			{
    				//Setting the boosts for search parameters
	    			HashMap<String,Float> boosts = new HashMap<String,Float>();
	    			boosts.put("topics", (float) 10 );
	    			boosts.put("filename", (float) 7.5 );
	    			boosts.put("upload_time", (float) 3.5 );
	    			
	    			String[] fields = new String[] {"topics","filename","upload_time"};
	    			multiFieldQueryParser = new MultiFieldQueryParser(luceneVersion, fields,new StandardAnalyzer(luceneVersion),boosts);
	    			booleanQuery.add(multiFieldQueryParser.parse(searchString), BooleanClause.Occur.MUST);
    			}
    			
    	
    	//Filtering results in the range of 90 days
    	booleanQuery.add(new BooleanClause(termRangeQuery,Occur.MUST));
    	
    	@SuppressWarnings("deprecation")
    	IndexReader indexReader = IndexReader.open(directory);
    	IndexSearcher indexSearcher = new IndexSearcher(indexReader);     
    	TopDocs topDocs = null;
    	
    	/**
    	 * The sorting is done on the FIELD_SCORE(document relevance) 
    	 */
    		topDocs = indexSearcher.search(booleanQuery, MAX_HITS, new Sort(SortField.FIELD_SCORE));
    
    	
    	Map<String,String> ids = new HashMap<String,String>();
        ScoreDoc[] hits = topDocs.scoreDocs;
       
        System.out.println(hits.length + " Record(s) Found");
        
        for (ScoreDoc scoreDoc : hits) 
        {
        	int docId = scoreDoc.doc;
            Document d = indexSearcher.doc(docId);
            
            if(searchCriterion.equalsIgnoreCase(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
            {
            	ids.put(d.get("author_email_id"),d.get("question_id"));
            }
            
            if(searchCriterion.equalsIgnoreCase(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
            {
            	ids.put(d.get("email_id"),d.get("email_id"));
            } 
            
            if(searchCriterion.equalsIgnoreCase(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
            {
            	ids.put(d.get("file_id"),d.get("file_id"));
            } 
		}
        
        if(hits.length == 0)
        {
        	System.out.println("No Records Found ");
        }
        
        indexReader.close();

    	return ids;
    }
}

