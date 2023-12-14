package com.example.university.repository;

import com.example.university.model.*;
import java.util.*;

public interface StudentRepository {
    ArrayList<Student> getStudents();

    Student getStudentById(int id);

    Student addStudent(Student student);

    Student updateStudent(int id, Student student);

    void deleteStudent(int id);

    List<Course> getStudentCourses(int id);
}