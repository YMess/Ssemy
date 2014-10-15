package com.ymess.scheduler;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ymess.util.LuceneIndexer;
 
@Component
public class RunScheduler {
 
	@Scheduled(cron="0 0/1 * * * ?") 
	public void execute() { 
		System.out.println("Creating Indexes "+new Date()); 
		
		//Lucene Indexes to be Invoked
		
		System.out.println("Indexing ");
		 LuceneIndexer.invokeIndexing();
		 System.out.println("Indexed");
	}
}