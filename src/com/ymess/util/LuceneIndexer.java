package com.ymess.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Indexes the Data
 * @author balaji i
 *
 */
public class LuceneIndexer
{
		public static final String INDEX_DIR = YMessCommonUtility.INDEX_LOCATION;
		private static final String JDBC_DRIVER = "org.apache.cassandra.cql.jdbc.CassandraDriver";
		private static final String CONNECTION_URL = "jdbc:cassandra://localhost/ymess?version=3.0.0";
		private static final String USER_NAME = "";
		private static final String PASSWORD = "";
		
		private final static String INDEX_QUESTION_DETAILS ="select author_email_id,question_id,updated_date,question_title,question_desc,topics from questions";
		private static final String INDEX_USER_DETAILS = "select email_id,first_name,last_name,profile_last_updated,user_image_name from users_data";
		private static final String INDEX_FILE_DETAILS = "select file_id,user_email_id,filename,topics,upload_time,file_description,user_first_name,user_last_name from files";
		private static final String INDEX_TOPICS = "select topic,file_count from topics";
		
		public static void main(String[] args) throws Exception 
		{
			invokeIndexing();		
		}
		
		public static void invokeIndexing()
		{
			DeleteDirectory.main(null);
			try
			{  
				   Class.forName(JDBC_DRIVER).newInstance();  
				   Connection conn = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);  
				  
				   @SuppressWarnings("deprecation")
				   StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT,CharArraySet.EMPTY_SET);  
				   @SuppressWarnings("deprecation")
				  
				   IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_CURRENT, standardAnalyzer);
				  
				   /** Creating indexes*/
				   
				   int indexedQuestionDocumentCount = createIndexes( indexWriterConfig.clone(), conn, YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING); 

				   int indexedPeopleDocumentCount = createIndexes(indexWriterConfig.clone(),  conn, YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING); 
				   
				   int indexedFileDocumentCount = createIndexes(indexWriterConfig.clone() , conn, YMessCommonUtility.FILE_IDENTIFIER_INDEXING); 
				   
				   int indexedTopicsCount = createIndexes(indexWriterConfig.clone(), conn, YMessCommonUtility.TOPIC_IDENTIFIER_INDEXING);
				   
				   System.out.println(indexedQuestionDocumentCount + " Questions have been indexed successfully");
				   System.out.println(indexedPeopleDocumentCount + " People have been indexed successfully");
				   System.out.println(indexedFileDocumentCount + " Files have been indexed successfully");
				   System.out.println(indexedTopicsCount + " Topics have been indexed successfully");
			} 
			catch (Exception e) 
			{  
			  e.printStackTrace();  
			} 
		}
		
		@SuppressWarnings("deprecation")
		 static int createIndexes(IndexWriterConfig indexWriterConfig , Connection conn,String identifier) throws Exception 
		 {  
			  IndexWriter indexWriter = null;
			  String query = "";  
			
			  if(identifier.equals(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
			  {  
				  query = INDEX_QUESTION_DETAILS;
				  indexWriter = new IndexWriter(FSDirectory.open(new File(YMessCommonUtility.INDEX_LOCATION_QUESTIONS)), indexWriterConfig);
				  System.out.println("Indexing to directory '" + YMessCommonUtility.INDEX_LOCATION_QUESTIONS + "'...");  
			  }
			  if(identifier.equals(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))	
			  {
				  query = INDEX_USER_DETAILS;
				  indexWriter = new IndexWriter(FSDirectory.open(new File(YMessCommonUtility.INDEX_LOCATION_PEOPLE)), indexWriterConfig);
				  System.out.println("Indexing to directory '" + YMessCommonUtility.INDEX_LOCATION_PEOPLE + "'...");  
			  }
			  
			  if(identifier.equals(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
			  {
				  query = INDEX_FILE_DETAILS;
				  indexWriter = new IndexWriter(FSDirectory.open(new File(YMessCommonUtility.INDEX_LOCATION_FILES)), indexWriterConfig);
				  System.out.println("Indexing to directory '" + YMessCommonUtility.INDEX_LOCATION_FILES + "'...");  
			  }
			  
			  if(identifier.equals(YMessCommonUtility.TOPIC_IDENTIFIER_INDEXING))
			  {
				  query = INDEX_TOPICS;
				  indexWriter = new IndexWriter(FSDirectory.open(new File(YMessCommonUtility.INDEX_LOCATION_TOPICS)), indexWriterConfig);
				  System.out.println("Indexing to directory '" + YMessCommonUtility.INDEX_LOCATION_TOPICS + "'...");  
			  }
			  
			  Statement stmt = conn.createStatement();  
			  ResultSet rs = stmt.executeQuery(query);  
			  int indexCount = 0;
			  while (rs.next()) 
			  {  
				  	/** Iterating Result Set to fetch Individual Values and Adding to Document*/
			         Document document = new Document();  
			         
			         if(identifier.equals(YMessCommonUtility.QUESTION_IDENTIFIER_INDEXING))
			         {
			        	 document.add(new TextField("author_email_id", rs.getString("author_email_id"), Field.Store.YES));
			         
				         if(null != rs.getString("question_id"))
					         document.add(new TextField("question_id", rs.getString("question_id"), Field.Store.YES));
				        
				         if(null != rs.getString("question_title"))
				        	 document.add(new Field("question_title", rs.getString("question_title"), Field.Store.YES,Field.Index.ANALYZED));
				        
				         if(null != rs.getString("question_desc"))
				        	 document.add(new Field("question_desc", rs.getString("question_desc"), Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(null != rs.getString("topics"))
				        	 document.add(new Field("topics", rs.getString("topics"), Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(null != rs.getString("updated_date"))
				         {
				        	 float boost = 5.0f;
					        	
				        	 Date profileLastUpdatedDate = rs.getTimestamp("updated_date");
				        	 Date currentDate = new Date();
				        	 
				        	 long postedOnTime = profileLastUpdatedDate.getTime();
				        	 long currentTime = currentDate.getTime();
				        	 long differenceInTime = currentTime - postedOnTime;
				        	 
				        	 //Calculating difference in days
				        	 long differenceInDays = differenceInTime / (1000 * 60 * 60 * 24);
	
				        	 /**  Setting Higher Boosts to Recently Posted Jobs */
				        	 if(differenceInDays > 0)
				        	 {
				        		boost =  (1.0f/differenceInDays) * 5;
				        	 } 
				        	
				        	 String profileLastUpdated = DateTools.dateToString(profileLastUpdatedDate, Resolution.SECOND);
				    
					        Field dateField= new Field("updated_date",profileLastUpdated, Field.Store.YES,Field.Index.ANALYZED); 
					        dateField.setBoost(boost);
					        document.add(dateField);
				         }
			      
			         }
			         
			         
			         if(identifier.equals(YMessCommonUtility.PEOPLE_IDENTIFIER_INDEXING))
			         {
			        	 document.add(new TextField("email_id", rs.getString("email_id"), Field.Store.YES));
				         
				         if(null != rs.getString("first_name"))
					         document.add(new TextField("first_name", rs.getString("first_name"), Field.Store.YES));
				        
				         if(null != rs.getString("last_name"))
				        	 document.add(new TextField("last_name", rs.getString("last_name"), Field.Store.YES));
				        
				         if(null != rs.getString("user_image_name"))
				        	 document.add(new TextField("user_image_name", rs.getString("user_image_name"), Field.Store.YES));
				         
				         
				         if(null != rs.getString("profile_last_updated"))
				         {
				        	 float boost = 25.0f;
					        	
				        	 Date profileLastUpdatedDate = rs.getTimestamp("profile_last_updated");
				        	 Date currentDate = new Date();
				        	 
				        	 long postedOnTime = profileLastUpdatedDate.getTime();
				        	 long currentTime = currentDate.getTime();
				        	 long differenceInTime = currentTime - postedOnTime;
				        	 
				        	 //Calculating difference in days
				        	 long differenceInDays = differenceInTime / (1000 * 60 * 60 * 24);
	
				        	 /**  Setting Higher Boosts to Recently Posted Jobs */
				        	 if(differenceInDays>0)
				        	 {
				        		boost =  (1.0f/differenceInDays) * 20;
				        	 } 
				        	
				        	 String profileLastUpdated = DateTools.dateToString(profileLastUpdatedDate, Resolution.SECOND);
				    
					        Field dateField= new Field("profile_last_updated",profileLastUpdated, Field.Store.YES,Field.Index.ANALYZED); 
					        dateField.setBoost(boost);
					        document.add(dateField);
				         }
			      
			         }
			         
			         
			         if(identifier.equals(YMessCommonUtility.FILE_IDENTIFIER_INDEXING))
			         {
			        	 document.add(new TextField("user_email_id", rs.getString("user_email_id"), Field.Store.YES));
				         
				         if(null != rs.getString("file_id"))
					         document.add(new TextField("file_id", rs.getString("file_id"), Field.Store.YES));
				        
				         if(null != rs.getString("topics"))
				        	 document.add(new Field("topics", rs.getString("topics"), Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(null != rs.getString("filename"))
				        	 document.add(new Field("filename", rs.getString("filename"), Field.Store.YES,Field.Index.ANALYZED));
				        
				         if(null != rs.getString("file_description"))
				        	 document.add(new Field("file_description",rs.getString("file_description"),Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(null != rs.getString("user_first_name"))
				        	 document.add(new Field("user_first_name",rs.getString("user_first_name"),Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(null != rs.getString("user_last_name"))
				        	 document.add(new Field("user_last_name",rs.getString("user_last_name"),Field.Store.YES,Field.Index.ANALYZED));
				         
				         
				         
				         if(null != rs.getString("upload_time"))
				         {
				        	 float boost = 25.0f;
					        	
				        	 Date profileLastUpdatedDate = rs.getTimestamp("upload_time");
				        	 Date currentDate = new Date();
				        	 
				        	 long postedOnTime = profileLastUpdatedDate.getTime();
				        	 long currentTime = currentDate.getTime();
				        	 long differenceInTime = currentTime - postedOnTime;
				        	 
				        	 //Calculating difference in days
				        	 long differenceInDays = differenceInTime / (1000 * 60 * 60 * 24);
	
				        	 /**  Setting Higher Boosts to Recently Posted Jobs */
				        	 if(differenceInDays>0)
				        	 {
				        		boost =  (1.0f/differenceInDays) * 20;
				        	 } 
				        	
				        	 String profileLastUpdated = DateTools.dateToString(profileLastUpdatedDate, Resolution.SECOND);
				    
					        Field dateField= new Field("upload_time",profileLastUpdated, Field.Store.YES,Field.Index.ANALYZED); 
					        dateField.setBoost(boost);
					        document.add(dateField);
				         }
			      
			         }
			         
			         if(identifier.equals(YMessCommonUtility.TOPIC_IDENTIFIER_INDEXING))
			         {
				         if(null != rs.getString("topic"))
				        	 document.add(new Field("topic", rs.getString("topic"), Field.Store.YES,Field.Index.ANALYZED));
				         
				         if(0 != rs.getLong("file_count"))
				        	 document.add(new LongField("file_count", rs.getLong("file_count"), Field.Store.YES));
			         }
			         
			         indexWriter.addDocument(document);  
				     indexCount++;
			 }  
		  indexWriter.commit();
		  indexWriter.close();
		  //indexWriter = null;
		  return indexCount;
		}	
	}