package com.redis.task.controller;

import com.redis.task.entity.Student;
import com.redis.task.service.StudentService;
import com.redis.task.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAll")
    public List<Student> getAllStudents(){
        return studentService.findAll();
    }

    @GetMapping("/get/{id}")
    public Student getStudentById(@PathVariable int id){
        return studentService.findStudentById(id);
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.save(student);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id){
    	System.out.println("entered delete");
        return studentService.deleteStudent(id);
    }

}
