package com.redis.task.service;

import com.redis.task.entity.Student;

import java.util.List;

public interface StudentService {

    public Student save(Student student);
    public List<Student> findAll();
    public Student findStudentById(int id);
    public Student updateStudent(Student student);
    public String deleteStudent(int id);

}
