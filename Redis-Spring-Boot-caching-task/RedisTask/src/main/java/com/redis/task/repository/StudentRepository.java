package com.redis.task.repository;

import com.redis.task.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.naming.Name;
import java.util.List;

//@Qualifier("db1repo")
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
