package com.example.demo.services.mappers;

import com.example.demo.models.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class courseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCourseName(rs.getString("courseName"));
        course.setAdmin_id(rs.getInt("admin_id"));
        return course;
    }
}
