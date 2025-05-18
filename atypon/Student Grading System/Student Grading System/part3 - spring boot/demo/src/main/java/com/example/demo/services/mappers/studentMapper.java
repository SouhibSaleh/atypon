package com.example.demo.services.mappers;

import com.example.demo.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class studentMapper implements RowMapper<Student>{
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setUsername(rs.getString("username"));
        student.setPassword(rs.getString("password"));
        return student;
    }
}
