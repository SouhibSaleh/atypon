package org.example.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepo {
    private Connection connection;
    public StudentRepo(Connection connection) {
        this.connection = connection;
    }
    public String getUserId(String username,String password)  {
        try{
        String query = "select id from students " +
                "where username = ? and password = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        int id = -1;
        if (rs.next()) {
            id =  rs.getInt("id");
        }
        return id+"";
    }catch (SQLException ex){
        return ex.getMessage();
    }catch (Exception ex)
    {
        return ex.getMessage();
    }
    }
    public String getCourseAverage(String course_id) {
        try{
        String query = "select grade from studentCourses " +
                "where course_id = ?";
        var stmt = connection.prepareStatement(query);
        stmt.setInt(1,Integer.parseInt(course_id));
        ResultSet rs = stmt.executeQuery();
        double sum = 0,counter=0;
        while(rs.next())
        {
            sum += rs.getInt("grade");
            counter++;
        }
        return (sum/counter)+"";
    }catch (SQLException ex){
        return ex.getMessage();
    }catch (Exception ex)
    {
        return ex.getMessage();
    }
    }
    public String getStudentCources(String Id)  {
        try {
            if (Id.equals("-1")) return "-1";
            String query = "SELECT courses.id, courses.courseName , studentCourses.grade " +
                    "FROM studentCourses " +
                    "JOIN courses ON studentCourses.course_id = courses.id " +
                    "WHERE studentCourses.student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, Id);
            ResultSet rs = stmt.executeQuery();
            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                int id = rs.getInt("id");
                String courseName = rs.getString("courseName");
                String grade = rs.getString("grade");
                result.append(id)
                        .append(" ")
                        .append(courseName)
                        .append(" ")
                        .append(grade)
                        .append("\n");
            }
            return result.toString();
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }


}
