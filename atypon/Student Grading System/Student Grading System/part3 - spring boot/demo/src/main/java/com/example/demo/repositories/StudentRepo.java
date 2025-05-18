package com.example.demo.repositories;
import com.example.demo.models.Student;
import com.example.demo.services.mappers.studentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.example.demo.models.CourseGrade;
import java.util.List;

@Repository
public class StudentRepo {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    public StudentRepo(JdbcTemplate jdbcTemplate,PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    public Student getStudentByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(query, new studentMapper(), username, password);
    }
    public Student getStudentByUsername(String username) {
        String query = "SELECT * FROM students WHERE username = ?";
        try{
        var temp= jdbcTemplate.queryForObject(query, new studentMapper(), username);
        return temp;
          }catch (Exception ex){
        return null;
    }
    }

    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        return  jdbcTemplate.queryForObject(query, new studentMapper(), id);
    }


    public double getCourseAverage(int courseId) {
        String query = "SELECT AVG(grade) FROM studentCourses WHERE course_id = ?";
        Double avg = jdbcTemplate.queryForObject(query, Double.class, courseId);
        return avg != null ? avg : 0.0;
    }

    public List<CourseGrade> getStudentCourses(int studentId) {
        String query = """
                SELECT sc.course_id,
                    c.courseName,
                    sc.grade
                FROM studentCourses sc
                JOIN courses c ON sc.course_id = c.id
                WHERE sc.student_id = ?
                """;
        return jdbcTemplate.query(query, (RowMapper<CourseGrade>) (rs, rowNum)
                -> new CourseGrade(
                rs.getInt("course_id"),
                rs.getString("courseName"),
                rs.getInt("grade")
        ), studentId);
    }
    public int saveStudent(String username,String password) {
        password = passwordEncoder.encode(password);
        String sql = "INSERT INTO students (username, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, username, password);
    }
}
