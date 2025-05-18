package com.example.demo.repositories;

import com.example.demo.models.Admin;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.services.mappers.adminMapper;
import com.example.demo.services.mappers.courseMapper;
import com.example.demo.services.mappers.studentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepo {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public AdminRepo(JdbcTemplate jdbcTemplate,PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }



    public Admin getAdminByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(query, new adminMapper(), username, password);
    }
    public Admin getAdminByUsername(String username) {
        String query = "SELECT * FROM admins WHERE username = ?";
        try {
           var temp = jdbcTemplate.queryForObject(query, new adminMapper(), username);
           return temp;
        }catch (Exception ex){
            return null;
        }

    }
    public Admin getAdminById(int id) {
        String query = "SELECT * FROM admins WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new adminMapper(), id);
    }

    public List<Admin> getAllAdmins() {
        String query = "SELECT * FROM admins";
        return jdbcTemplate.query(query, new adminMapper());
    }
    public List<Student> getAllStudents() {
        String query = "SELECT * FROM students";
        return jdbcTemplate.query(query, new studentMapper());
    }


    public int updateStudentGrade(int studentId, int courseId, int grade) {
        String query = """
                UPDATE studentCourses 
                SET grade = ? 
                WHERE student_id = ? AND course_id = ?
                """;
        return jdbcTemplate.update(query, grade, studentId, courseId);
    }

    public int addCourseToStudent(int studentId, int courseId) {
        String checkQuery = "SELECT COUNT(*) FROM studentCourses WHERE student_id = ? AND course_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class, studentId, courseId);

        if (count != null && count > 0) {
            return 0;
        }

        String insertQuery = "INSERT INTO studentCourses (student_id, course_id, grade) VALUES (?, ?, 0)";
        return jdbcTemplate.update(insertQuery, studentId, courseId);
    }

    public int addCourse(String courseName, int adminId) {
        String query = "INSERT INTO courses (courseName, admin_id) VALUES (?, ?)";
        return jdbcTemplate.update(query, courseName, adminId);
    }

    public double getCourseAverage(int courseId) {
        String query = "SELECT AVG(grade) FROM studentCourses WHERE course_id = ?";
        Double avg = jdbcTemplate.queryForObject(query, Double.class, courseId);
        return avg != null ? avg : 0.0;
    }

    public int deleteCourse(int courseId) {
        String deleteStudentCourses = "DELETE FROM studentCourses WHERE course_id = ?";
        String deleteCourses = "DELETE FROM courses WHERE id = ?";

        jdbcTemplate.update(deleteStudentCourses, courseId);
        return jdbcTemplate.update(deleteCourses, courseId);
    }

    public List<Course> getAllCourses() {
        String query = "SELECT * FROM courses";
        return jdbcTemplate.query(query, new courseMapper());
    }

    public int deleteStudentCourse(int studentId, int courseId, int adminId) {
        String query = """
                DELETE FROM studentCourses 
                WHERE student_id = ? AND course_id = ? 
                AND course_id IN (SELECT id FROM courses WHERE admin_id = ?)
                """;
        return jdbcTemplate.update(query, studentId, courseId, adminId);
    }

    public List<Course> getAdminCourses(int adminId) {
        String query = "SELECT * FROM courses WHERE admin_id = ?";
        return jdbcTemplate.query(query, new courseMapper(), adminId);
    }
    public int saveAdmin(String username,String password) {
        password = passwordEncoder.encode(password);
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, username, password);
    }
}