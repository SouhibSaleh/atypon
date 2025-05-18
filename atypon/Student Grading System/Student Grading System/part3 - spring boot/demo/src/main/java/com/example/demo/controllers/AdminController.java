package com.example.demo.controllers;


import com.example.demo.repositories.AdminRepo;
import com.example.demo.services.TableMaker;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminRepo adminRepo;

    public AdminController(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @PostMapping("/allAdmins")
    public String getAllAdmins(Model model){
        var results =adminRepo.getAllAdmins();
        model.addAttribute("result", TableMaker.makeAdminsTable(results));
        return "admin";
    }
    @PostMapping("/allStudents")
    public String getAllStudents(Model model){
        var results = adminRepo.getAllStudents();
        model.addAttribute("result",TableMaker.makeStudentsTable(results));
        return "admin";
    }
    @PostMapping("/allCourses")
    public String getAllCourses(Model model){
        var results = adminRepo.getAllCourses();
        model.addAttribute("result",TableMaker.makeCoursesTable(results));
        return "admin";
    }
    @PostMapping("/updateStudentGrade")
    public String updateStudentGrade(
            @RequestParam("studentId") int studentId,
            @RequestParam("courseId") int courseId,
            @RequestParam("grade") int grade,
            Model model
            ){
        var result = adminRepo.updateStudentGrade(studentId,courseId,grade);
        model.addAttribute("result",
                (result<=0)?"nothing changed":"record updated"
                );
        return "admin";

    }
    @PostMapping("/addCourseToStudent")
    public String addCourseToUser(
            @RequestParam("studentId") int studentId,
            @RequestParam("courseId") int courseId,
            Model model
    ){
            var result = adminRepo.addCourseToStudent(studentId,courseId);
            model.addAttribute("result",
                    (result<=0)?"nothing changed":"record has been updated"
                    );
            return "admin";
    }
    @PostMapping("/courseAverage")
    public String getCourseAverage(
            @RequestParam("courseId") int courseId,
            Model model
    ){
        var result =  adminRepo.getCourseAverage(courseId);
        model.addAttribute("result",result);
        return "admin";
    }

    @PostMapping("/addCourse")
    public String getCourseAverage(
            @RequestParam("courseName") String courseName,
            Model model
    ){

        var result =  adminRepo.addCourse(courseName,
                adminRepo.getAdminByUsername(getCurrentUserName()).getId()
                );
        model.addAttribute("result",(result<=0)?"nothing changed":"record has been updated");
        return "admin";
    }
    @PostMapping("/deleteCourseFromStudent")
    public String deleteCourseFromStudent(
            @RequestParam("studentId") int studentId,
            @RequestParam("courseId") int courseId,
            Model model
            ){
        var result = adminRepo.deleteStudentCourse(studentId,courseId,
                    adminRepo.getAdminByUsername(getCurrentUserName()).getId()
                );
        model.addAttribute("result",(result<=0)?"nothing changed":"record has been updated");
        return  "admin";

    }
    @PostMapping("/getMyCourses")
    public String getMyCourses(Model model)
    {
        var result = adminRepo.getAdminCourses(
                adminRepo.getAdminByUsername(
                        getCurrentUserName()
                ).getId()
        );
        model.addAttribute("result",TableMaker.makeCoursesTable(result));
        return "admin";
    }
    @PostMapping("/deleteCourse")
    public String deleteCourse(
            @RequestParam("courseId") int courseId,
            Model model
    ){
        var result = adminRepo.deleteCourse(courseId);
        model.addAttribute("result",(result<=0)?"nothing changed":"record has been updated");
        return "admin";
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
