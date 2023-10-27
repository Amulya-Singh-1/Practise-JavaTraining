package com.redis.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.redis.task.entity.Student;
import com.redis.task.repository.StudentRepository;

@Service
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService{

    public static final String HASH_KEY= "Student";
    
    @Autowired
    private RedisTemplate<String, Object> template; 
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll(){
//        List<Object> list=template.opsForHash().values(HASH_KEY);
//        if(list!=null) {
//        	System.out.println("fetched from cache");
//        	return list.stream().map(i->(Student)i).collect(Collectors.toList());
//        }
//        else {
//        	System.out.println("fetched from db");
        	return studentRepository.findAll();
//        }
    }

    @Cacheable(key="#id", value = "Student")
    public Student findStudentById(int id){

        // fetching from cache
        Student cachedStudent= (Student) template.opsForHash().get(HASH_KEY, id);
        if(cachedStudent!=null){       
        	System.out.println("fetched from cache");
            return cachedStudent;
        }
        else{
            // fetching from db
            Optional<Student> obj=studentRepository.findById(id);
            if(obj.isEmpty()){
                System.out.println("the student with that id doesn't exist");
                return null;
            }else {
                Student studentFromDb = obj.get();
                template.opsForHash().put(HASH_KEY, id, studentFromDb);
                System.out.println("saved in cache");
                return studentFromDb;
            }
        }
    }

    public Student save(Student student){
//        template.opsForHash().put(HASH_KEY, student.getId(), student);
//        System.out.println("saved in cache");
//		  saved in cache

        Student savedInDb=studentRepository.save(student);
        System.out.println("saved in db");
        //saved in db
        return savedInDb;
    }

//    @CachePut(key = "#id", value = "Student", unless = "#result == null")
    @CachePut(key = "#id", value = "Student") 
    public Student updateStudent(Student student){
        
        // updating in db
        Student updatedStudent=new Student();
        Optional<Student> obj=studentRepository.findById(student.getId());
        if(obj.isEmpty()){
            System.out.println("the student with that id doesn't exist");
            return null;
        }else {      	
        	Student existingStudent = obj.get();
        	System.out.println(existingStudent.getId());
        	BeanUtils.copyProperties(student, existingStudent);
//        	studentRepository.deleteById(student.getId());
            studentRepository.save(existingStudent);
        	System.out.println("updated in db");

        	//deleting the old one and saving the updated one in cache
            template.opsForHash().put(HASH_KEY, existingStudent.getId(), existingStudent);
            System.out.println("updated in cache");
            return existingStudent;
        }
    }
    
//    
//    @CachePut(key = "#result.id", value = "Student")
//    public Student updateStudent(Student student) {
//        // updating in db
//        Optional<Student> obj = studentRepository.findById(student.getId());
//        if (obj.isEmpty()) {
//            System.out.println("The student with that id doesn't exist");
//            return null;
//        } else {
//            Student existingStudent = obj.get();
//            // Copy properties from updated student to the existing student
//            BeanUtils.copyProperties(student, existingStudent);
//            studentRepository.save(existingStudent);
//            System.out.println("Updated in db");
//
//            // Updating the cached student
//            template.opsForHash().put(HASH_KEY, existingStudent.getId(), existingStudent);
//            System.out.println("Updated in cache");
//
//            return existingStudent;
//        }
//    }


    @CacheEvict(key = "#id", value = "Student")
    public String deleteStudent(int id){
//        template.opsForHash().delete(HASH_KEY,id);

        Optional<Student> obj=studentRepository.findById(id);
        if(obj.isPresent()){
        	studentRepository.deleteById(id);
            System.out.println("deleted from db");

            template.opsForHash().delete(HASH_KEY, id);
            System.out.println("deleted from cache");

            return "product deleted";
        }else {
            System.out.println("the student with that id doesn't exist");
            return "the student with that id doesn't exist";
        }
    }

}
