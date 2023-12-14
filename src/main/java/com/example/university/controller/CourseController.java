package com.example.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.university.model.*;
import com.example.university.service.*;

@RestController
public class CourseController {

    @Autowired
    public CourseJpaService courseService;

    @GetMapping("/courses")
    public ArrayList<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/courses/{courseId}")
    public Course getCourseById(@PathVariable("courseId") int courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping("/courses/{courseId}")
    public Course updateCourse(@PathVariable("courseId") int courseId, @RequestBody Course course) {
        return courseService.updateCourse(courseId, course);
    }

    @DeleteMapping("/courses/{courseId}")
    public void deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourse(courseId);
    }

    @GetMapping("/courses/{courseId}/professor")

    public Professor getCourseProfessor(@PathVariable("courseId") int courseId) {
        return courseService.getCourseProfessor(courseId);
    }

    @GetMapping("/courses/{courseId}/students")

    public List<Student> getCourseStudents(@PathVariable("courseId") int courseId) {
        return courseService.getCourseStudents(courseId);
    }

}