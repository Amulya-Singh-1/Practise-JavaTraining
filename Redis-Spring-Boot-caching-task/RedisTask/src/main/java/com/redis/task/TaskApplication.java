package com.redis.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.redis.task.redisRepository.StudentRedisRepository;
import com.redis.task.repository.StudentRepository;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.redis.task.repository")//, repositoryBaseClass =StudentRepository.class )
//@EnableRedisRepositories(basePackages = "com.redis.task.redisRepository", repositoryBaseClass = StudentRedisRepository.class)
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

}
