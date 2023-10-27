package com.redis.task.redisRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redis.task.entity.Student;

@Repository("redisRepository")
public interface StudentRedisRepository extends CrudRepository<Student, Integer> {
	
}
