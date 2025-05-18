package org.example.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepo {
    private Connection connection;
    public AdminRepo(Connection connection) {
        this.connection = connection;
    }
    public String getAdminId(String username,String password) {
        try {
            String query = "select id from admins where username = ? and password = ?";
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            System.out.println(id);
            return id + "";
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String getAllStudentsGrades(String course_id) {
        try{
        String query = "select students.id, students.username, studentCourses.grade " +
                "from studentCourses " +
                "join students on students.id = studentCourses.student_id " +
                "where studentCourses.course_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1,Integer.parseInt(course_id));
        ResultSet rs = stmt.executeQuery();
        StringBuilder sb = new StringBuilder();
        while(rs.next())
        {
            int id = rs.getInt("id");
            String name = rs.getString("username");
            int grade = rs.getInt("grade");

            sb.append(id).append(" ").append(name).append(" ").append(grade).append("\n");
        }
        return sb.toString();
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String updateStudentGrade(String grade,String student_id,String course_id){
        try {
        String command = "update studentCourses " +
                "set grade = ? " +
                "where studentCourses.student_id = ? and studentCourses.course_id = ?";
        var stmt = connection.prepareStatement(command);
        stmt.setInt(1,Integer.parseInt(grade));
        stmt.setInt(2,Integer.parseInt(student_id));
        stmt.setInt(3,Integer.parseInt(course_id));
        int rs = stmt.executeUpdate();
        String result = "";
        if(rs>0){
            result = "record has been updated";
        }else{
            result = "nothing has been updated";
        }
        return result;
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String addCourseToStudent(String student_id,String course_id) {
        try{
            String query = "select studentCourses.student_id from studentCourses " +
                    "where studentCourses.student_id = ? and studentCourses.course_id = ?";
            var test = connection.prepareStatement(query);
            test.setInt(1,Integer.parseInt(student_id));
            test.setInt(2,Integer.parseInt(course_id));
            ResultSet rs = test.executeQuery();
            boolean check = false;
            while(rs.next())
            {
                check = true;
                break;
            }
            if(check)
                return "record already exists";
            String command = "insert into studentCourses (student_id,course_id,grade) " +
                    "values (?,?,0)";
            var stmt = connection.prepareStatement(command);
            stmt.setInt(1,Integer.parseInt(student_id));
            stmt.setInt(2,Integer.parseInt(course_id));
            String result = "";
            if(stmt.executeUpdate()>0)
            {
                result = "record has been added";
            }else{
                result = "nothing has been updated";
            }
            return result;
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String addCourse(String course_name,String admin_id) {
        try
        {
            String command = "insert into courses (courseName,admin_id) values (?,?)";
            var stmt = connection.prepareStatement(command);
            stmt.setString(1, course_name);
            stmt.setInt(2, Integer.parseInt(admin_id));

            String result = "";
            if (stmt.executeUpdate() > 0) {
                result = "record added successfully";
            } else {
                result = "nothing has been updated";
            }

            return result;
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
    public String deleteCourse(String course_id){

        try{
            String command = "delete from studentCourses " +
                    "where studentCourses.course_id = ?";
            var stmt = connection.prepareStatement(command);
            stmt.setInt(1, Integer.parseInt(course_id));
            stmt.executeUpdate();
            String commandUpdateCourses = "delete from courses " +
                    "where  id = ?";
            stmt = connection.prepareStatement(commandUpdateCourses);
            stmt.setInt(1, Integer.parseInt(course_id));
            int i = stmt.executeUpdate();
            String result = "";
            if (i > 0) {
                result = "record deleted successfully";
            } else {
                result = "nothing had been updated";
            }
            return result;
        }
        catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String getAllCourses(){
        try {
            String query = "select *from courses";
            var stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            StringBuilder response = new StringBuilder();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("courseName");
                response.append(id).append(" ").append(name).append("\n");
            }
            return response.toString();
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }
    public String deleteStudentCourse(String student_id,String course_id){
        try{
            String command = "delete from studentCourses " +
                    "where studentCourses.student_id = ?" +
                    " and " +
                    "studentCourses.course_id = ?";
            var stmt = connection.prepareStatement(command);
            stmt.setInt(1, Integer.parseInt(student_id));
            stmt.setInt(2, Integer.parseInt(course_id));
            int result = stmt.executeUpdate();
            String resultString = "";
            if (result == 0) {
                resultString = "nothing had been updated";
            } else {
                resultString = "record deleted successfully";
            }

            return resultString;
        }catch (SQLException ex){
            return ex.getMessage();
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

}
