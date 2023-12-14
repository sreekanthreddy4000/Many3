package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class StudentJpaService implements StudentRepository {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> list = studentJpaRepository.findAll();
        ArrayList<Student> students = new ArrayList<>(list);
        return students;
    }

    @Override
    public Student getStudentById(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        try {

            List<Integer> courseIds = new ArrayList<>();
            for (Course course : student.getCourses()) {
                courseIds.add(course.getCourseId());
            }

            List<Course> courses = courseJpaRepository.findAllById(courseIds);
            student.setCourses(courses);

            for (Course course : courses) {
                course.getStudents().add(student);
            }

            Student saveStudent = studentJpaRepository.save(student);
            courseJpaRepository.saveAll(courses);
            return saveStudent;

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student updateStudent(int id, Student student) {
        try {
            Student newStudent = studentJpaRepository.findById(id).get();

            if (student.getStudentName() != null) {
                newStudent.setStudentName(student.getStudentName());
            }

            if (student.getEmail() != null) {
                newStudent.setEmail(student.getEmail());
            }

            if (student.getCourses() != null) {
                List<Course> courses = newStudent.getCourses();
                for (Course course : courses) {
                    course.getStudents().remove(newStudent);
                }
                courseJpaRepository.saveAll(courses);

                List<Integer> newCourseIds = new ArrayList<>();
                for (Course course : student.getCourses()) {
                    newCourseIds.add(course.getCourseId());
                }
                List<Course> newCourses = courseJpaRepository.findAllById(newCourseIds);
                for (Course course : newCourses) {
                    course.getStudents().add(newStudent);
                }
                courseJpaRepository.saveAll(newCourses);
                newStudent.setCourses(newCourses);
            }
            newStudent = studentJpaRepository.save(newStudent);
            return newStudent;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();

            List<Course> courses = student.getCourses();

            for (Course course : courses) {
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(courses);
            studentJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Course> getStudentCourses(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();
            return student.getCourses();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}