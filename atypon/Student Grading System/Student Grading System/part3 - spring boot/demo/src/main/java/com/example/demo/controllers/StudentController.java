package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepo;
import com.example.demo.services.TableMaker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public String getPage(){
        return "student";
    }
    @PostMapping("/courseAverage")
    public String getCourseAverage(@RequestParam("courseId") int courseId,Model model){
        double x = studentRepo.getCourseAverage(courseId);
        model.addAttribute("result", x);
        return "student";

    }
    @PostMapping("/studentCourses")
    public String getStudentCourses(Model model){

        Student student = studentRepo.getStudentByUsername(getCurrentUserName());
        var results = studentRepo.getStudentCourses(student.getId());
        model.addAttribute("result", TableMaker.makeCourseGradesTable(results));
        return "student";
    }

    private String getCurrentUserName()
    {
        return ((User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
    }

}
