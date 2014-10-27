/**
 * 
 */
package com.ymess.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author balaji i
 *
 */
public class YMessCommonUtility {
	
	public static final String ROLE_REGISTERED = "ROLE_REGISTERED";
	public static final Boolean ENABLE_USER_FLAG = true;
	public static final Boolean DISABLE_USER_FLAG = false;
	public static final Boolean IS_JOINED = true;
	public static final Boolean IS_PROFILE_UPDATED = true;
	public static final Boolean IS_POSTED_QUESTIONS = true;
	public static final Boolean IS_ANSWERED_QUESTIONS = true;
	public static final Boolean IS_FOLLOWING = true;
	public static final Boolean IS_UPVOTED = true;
	public static final Boolean IS_DOWNVOTED = true;
	public static final Boolean IS_SHARED = true;
	public static final Boolean IS_LIKED_QUESTION = true;
	public static final Boolean IS_LIKED_ANSWER = true;
	
	public static final String EQUAL_SET = "equalSet";
	public static final String DELETED_ITEMS = "deletedItems";
	public static final String ADDED_ITEMS = "addedItems";
	
	public static final String INDEX_LOCATION = "E://GIT//Ssemy//Indexes";
	
	public static final String INDEX_LOCATION_QUESTIONS = INDEX_LOCATION + "//Questions";
	public static final String INDEX_LOCATION_PEOPLE = INDEX_LOCATION + "//People";
	public static final String INDEX_LOCATION_FILES = INDEX_LOCATION  + "//Files";
	public static final String INDEX_LOCATION_TOPICS = INDEX_LOCATION + "//Topics";
	public static final String SUGGESTER_INDEXES = INDEX_LOCATION + "//Suggester";
	
	public static final String QUESTION_IDENTIFIER_INDEXING = "Questions";
	public static final String PEOPLE_IDENTIFIER_INDEXING = "People";
	public static final String FILE_IDENTIFIER_INDEXING = "Files";
	public static final String TOPIC_IDENTIFIER_INDEXING = "Topics";
	
	
	/**
	 * Returns MD5 hashed password
	 * @author balaji i
	 * @param password
	 * @return String(md5hashedPassword)
	 */
	public static String getMD5HashedPassword(String password)
	{
		return DigestUtils.md5Hex(password);
	}

	/**
	 * Takes the Question Description and finds the relevant keywords to categorize the question properly to aid searching
	 * @author balaji i
	 * @param questionDescription
	 * @return keywordsInQuestion(Set<String>)
	 */
	public static Set<String> findKeywordsinQuestionDescription(String questionDescription) 
	{
		/** Stop Words to be removed from the Question for aiding search later based on keywords */
		List<String> stopWords = Arrays.asList("a", "an", "and", "are", "as", "at","asked", "be", "but", "by",
 			   "for", "if", "in", "into", "is",
 			   "no", "not", "of", "on", "or", "such",
 			   "that", "the", "their", "then", "there", "these","how",
 			   "they", "this", "to", "was", "will", "with","what","why","before","after","while","could","can","would","will","may","might","so","most","much","just","let","my","our"
 			   		+ "ours","do","don't","get","got"," ","","for","however");
		
		List<String> specialCharacters = Arrays.asList("?",".","$","#","!","@","%","^","&","*","(",")","\\","|","/","{","}",";",":","-","~","`","_","=","");
		
		Set<String> keywordsInQuestion = new HashSet<String>();
		
		for (String lowerCaseWord : questionDescription.toLowerCase().split(" "))
		{
			if(!stopWords.contains(lowerCaseWord))
			{
				/** Removing all the Special Characters from the String */
				lowerCaseWord = lowerCaseWord.replaceAll(specialCharacters.toString(), "");
				keywordsInQuestion.add(lowerCaseWord);
			}
		}
		return keywordsInQuestion;
	}
	
	/**
	 * Converts List of EmailIds to a String to be Used in IN Clause of Cassandra 
	 * @param emailIdList
	 * @return String emailIds
	 */
	public static String getEmailIdListAsString(Set<String> emailIdList)
	{
		StringBuilder emailIdsSB = new StringBuilder();
		String emailIds = "";
		
		for (String emailId : emailIdList) {
			emailIdsSB = emailIdsSB.append("'").append(emailId).append("',");
		}
		
		if(emailIdsSB.length() > 0)
			emailIds = emailIdsSB.substring(0,emailIdsSB.lastIndexOf(","));
		
		return emailIds;
	}

	/**
	 * Returns the FileExtension of the File (i.e jpg if the filename is abc.jpg)
	 * @author balaji i
	 * @param imageName
	 * @return String(fileExtension)
	 */
	public static String getFileExtension(String imageName) {
		String fileExtension = "";
		if(imageName.contains("."))
		{
			fileExtension = imageName.substring(imageName.lastIndexOf(".")+1,imageName.length());
		}
		return fileExtension;
	}
	
	 /**
		* This method Decodes the Encoded Parameter using Base64 class of Apache Codec.
		* @author BalajiI
		* @param encodedParameter
		* @return String(decodedParam)
		*/
		public static String decodeEncodedParameter(String encodedParameter)
		{
			/** Decoding the Encoded Parameter */
			byte[] encodedParam = Base64.decodeBase64(encodedParameter);
			return new String(encodedParam);
		}
		
		 /** Compresses the Original Image and Returns it
		 * @author balaji i
		 * @param photoData
		 * @return compressedImageInBytes(byte[])
		 * @throws IOException
		 */
		 public static byte[] returnCompressedImage(byte[] photoData) throws IOException
		 {
			   		InputStream inputStream = new ByteArrayInputStream(photoData);
			   		
			   		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        
			        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);

			        float quality = 0.2f;
			 
			        // create a BufferedImage as the result of decoding the supplied InputStream
			        BufferedImage image = ImageIO.read(inputStream);
			 
			        // get all image writers for JPG format
			        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			        
			        if (!writers.hasNext())
			            throw new IllegalStateException("No writers found");
			 
			        ImageWriter writer = (ImageWriter) writers.next();
			        writer.setOutput(imageOutputStream);
			        
			        ImageWriteParam param = writer.getDefaultWriteParam();
			 
			        // compress to a given quality
			        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			        param.setCompressionQuality(quality);
			        //param.setDestinationType(ImageTypeSpecifier.)
			 
			        // appends a complete image stream containing a single image and associated stream and image metadata and thumbnails to the output
			        writer.write(null, new IIOImage(image, null, null), param);
			 
			        byte[] compressedImageInBytes = byteArrayOutputStream.toByteArray();
			        return compressedImageInBytes;
			}

		 /**
		  * Removes Null and Empty Elements from Set
		  * @param addedInterests
		  * @return Clean Set without null or empty elements (Set<String>)
		  */
		public static Set<String> removeNullAndEmptyElements(Set<String> oldSet) {
			if(null != oldSet)
			{
				oldSet.removeAll(Collections.singleton(null));
				oldSet.removeAll(Collections.singleton(""));
			}
			return oldSet;
		}
		
		
		/**
		 * @author balaji i
		 * @param oldSet
		 * @param newSet
		 * @return Map<String,Set<String>>(DeletedItems and Added Items)
		 */
		
		public static Map<String,Set<String>> compareSetsAndReturnAddedAndDeletedObjects(Set<String> oldSet,Set<String> newSet)
		{
			Map<String,Set<String>> mergedItems = new HashMap<String, Set<String>>();
			
			/** Comapring Values of Both Sets for Equality */
			if(oldSet.toString().contentEquals(newSet.toString()))
			{
				mergedItems.put(EQUAL_SET,new HashSet<String>());
			}
			//Unequal Sets
			else
			{
				Set<String> deletedItems = new HashSet<String>();
				Set<String> addedItems = new HashSet<String>();
				Set<String> intermediary = new HashSet<String>();
				
				addedItems = newSet;
				intermediary = oldSet;
				
				for (String oldSetVal : oldSet) {
					if(! newSet.contains(oldSetVal))
					{
						deletedItems.add(oldSetVal);
					}
				}
				
				intermediary.removeAll(deletedItems);
				addedItems.removeAll(intermediary);
				
				/** Calculated the deleted and added interests */
				mergedItems.put(ADDED_ITEMS, addedItems);
				mergedItems.put(DELETED_ITEMS, deletedItems);
			}
		return mergedItems;
	}
		
		/**
		 * Returns all the popular topics with highest number of files
		 * @author balaji i
		 * @param indexLocation
		 * @return List<String>(Popular Topics)
		 * @throws FileNotFoundException
		 * @throws ParseException
		 * @throws IOException
		 */
		public static List<String> findPopularTopics(File indexLocation)  throws FileNotFoundException, ParseException, IOException  {
	    	Directory directory = null;
			try
			{
				directory = FSDirectory.open(indexLocation);
			}
			catch(Exception exception)
			{
				throw new FileNotFoundException("Indexes not found at "+indexLocation);
			}
	    	
	    	IndexReader indexReader = IndexReader.open(directory);
			
	    	long documentCount = indexReader.numDocs();
	    	Map<String,Long> topicWithFileCount = new HashMap<String, Long>();
	    	
	    	for ( int i = 0; i < documentCount; i++)
	    	{
	    			Document document = indexReader.document(i);

	    			if(document.get("file_count") != null)
	    		    	topicWithFileCount.put(document.get("topic"), Long.parseLong(document.get("file_count")));
	    		    
	    	}
	    	indexReader.close();
	    	
	    	List<String> keys = new ArrayList<String>();
	    	
	    	int maxCounter = 0;
	    	
	    	if(topicWithFileCount.size() < 10)
	    		maxCounter = topicWithFileCount.size();
	    	else
	    		maxCounter = 10;
	    	
	    	for(int i = 0; i < maxCounter; i++)
	    	{
	    		String key = Collections.max(topicWithFileCount.entrySet(), new Comparator<Entry<String,Long>>(){
	                    @Override
	                    public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
	                        return o1.getValue() > o2.getValue()? 1:-1;
	                    }
	                }).getKey() ;
	    		
	    		if(!keys.contains(key)) 
	    			keys.add(key); 
	    		topicWithFileCount.remove(key);
	    	}
	    	
	    	return keys;
		}

		/**
		 * Returns the Corresponding String of Date Object depending on TimeZone
		 * @param date
		 * @param format
		 * @param timeZone
		 * @return
		 */
		public static String formatDateToString(Date date, String format,
	            String timeZone) {
	        // null check
	        if (date == null) return null;
	        // create SimpleDateFormat object with input format
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
	        // default system timezone if passed null or empty
	        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
	            timeZone = Calendar.getInstance().getTimeZone().getID();
	        }
	        // set timezone to SimpleDateFormat
	        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
	        // return Date in required format with timezone as String
	        return sdf.format(date);
	    }
		
		
		/**
		 * Replaces all the Apostrophes
		 * @param wordWithApostrophe
		 * @return wordWithoutApostrophe
		 */
		public static String removeExtraneousApostrophe(String wordWithApostrophe)
		{
			return wordWithApostrophe.replaceAll("'", "");
		}
}
