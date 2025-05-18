package com.example.demo.controllers;


import com.example.demo.repositories.AdminRepo;
import com.example.demo.repositories.StudentRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping
public class AuthController {
    private final AdminRepo adminRepo;
    private final StudentRepo studentRepo;

    public AuthController(AdminRepo adminRepo,StudentRepo studentRepo) {
        this.adminRepo = adminRepo;
        this.studentRepo = studentRepo;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest",new LoginRequest());
        return "login";
    }
    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("signupRequest",new LoginRequest());
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(@ModelAttribute SignRequest signupRequest,Model model) {
        if(signupRequest.role.equals("ADMIN"))
        {
            adminRepo.saveAdmin(signupRequest.username,signupRequest.password);
        }else{
            studentRepo.saveStudent(signupRequest.username,signupRequest.password);
        }
        return "redirect:/login";
    }

    private class LoginRequest{
        private String username;
        private String password;

        @Override
        public String toString() {
            return "LoginRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    private class SignRequest {
        private String username;
        private String password;
        private String role;

        @Override
        public String toString() {
            return "signRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }


}
