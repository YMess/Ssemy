package com.ymess.scheduler;

import java.util.Date;

import org.springframework.stereotype.Component;
 
@Component
public class RunScheduler {
 
	/*@Scheduled(cron="0 0/1 * * * ?") */
	public void execute() { 
		System.out.println("Creating Indexes "+new Date()); 
		//LuceneIndexer.invokeIndexing();
		System.out.println("Successfully Indexed "+ new Date());
	}
}