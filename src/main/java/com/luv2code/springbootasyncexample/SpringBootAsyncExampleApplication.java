package com.luv2code.springbootasyncexample;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.luv2code.springbootasyncexample.model.User;
import com.luv2code.springbootasyncexample.service.GitHubLookUpService;

@EnableAsync
@SpringBootApplication
public class SpringBootAsyncExampleApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootAsyncExampleApplication.class);
	
	@Autowired
	private GitHubLookUpService gitHubLookupService;
	
	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAsyncExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Start the clock
				long start = System.currentTimeMillis();

				// Kick of multiple, asynchronous lookups
				CompletableFuture<User> page1 = gitHubLookupService.findUser("Imkbhat");
				CompletableFuture<User> page2 = gitHubLookupService.findUser("iamudachan");
				CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");
				//CompletableFuture<User> page4 = gitHubLookupService.findUser("");
				// Wait until they are all done
				CompletableFuture.allOf(page1, page2, page3).join(); //, page4

				// Print results, including elapsed time
				logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
				logger.info("--> " + page1.get());
				logger.info("--> " + page2.get());
				logger.info("--> " + page3.get());
				//logger.info("--> " + page4.get());
		
	}

}
