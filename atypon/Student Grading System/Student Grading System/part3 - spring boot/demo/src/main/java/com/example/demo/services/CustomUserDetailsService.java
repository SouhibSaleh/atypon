package com.example.demo.services;


import com.example.demo.models.Admin;
import com.example.demo.models.Student;
import com.example.demo.repositories.AdminRepo;
import com.example.demo.repositories.StudentRepo;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepo adminRepo;
    private final StudentRepo studentRepo;

    public CustomUserDetailsService(AdminRepo adminRepo, StudentRepo studentRepo) {
        this.adminRepo = adminRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.getAdminByUsername(username);
        if (admin != null) {
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();

        }
        Student student = studentRepo.getStudentByUsername(username);
        if (student != null) {
            return User.builder()
                    .username(student.getUsername())
                    .password(student.getPassword())
                    .roles("STUDENT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}