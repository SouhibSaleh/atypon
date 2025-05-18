package org.example.Repos;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepo {
    private DataSource dataSource;

    public AdminRepo() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
        } catch (Exception e) {
            throw new RuntimeException("Database connection error!", e);
        }
    }

    public String getAdminId(String username, String password) {
        String query = "SELECT id FROM admins WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return String.valueOf(rs.getInt("id"));
            }
            return "-1";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String getAllStudentsGrades(String course_id) {
        String query = """
            SELECT students.id, students.username, studentCourses.grade
            FROM studentCourses
            JOIN students ON students.id = studentCourses.student_id
            WHERE studentCourses.course_id = ?""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(course_id));
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("<table border='1'>");
            sb.append("<tr><th>ID</th><th>Username</th><th>Grade</th></tr>");

            while (rs.next()) {
                sb.append("<tr>")
                        .append("<td>").append(rs.getInt("id")).append("</td>")
                        .append("<td>").append(rs.getString("username")).append("</td>")
                        .append("<td>").append(rs.getInt("grade")).append("</td>")
                        .append("</tr>");
            }

            sb.append("</table>");
            return sb.toString();

        } catch (SQLException ex) {
            return "<p>Error: " + ex.getMessage() + "</p>";
        }
    }


    public String updateStudentGrade(String grade, String student_id, String course_id) {
        String command = """
                UPDATE studentCourses 
                SET grade = ? 
                WHERE student_id = ? AND course_id = ?""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(command)) {

            stmt.setInt(1, Integer.parseInt(grade));
            stmt.setInt(2, Integer.parseInt(student_id));
            stmt.setInt(3, Integer.parseInt(course_id));

            return stmt.executeUpdate() > 0 ? "Record updated successfully" : "Nothing was updated";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String addCourseToStudent(String student_id, String course_id) {
        String checkQuery = """
                SELECT student_id FROM studentCourses 
                WHERE student_id = ? AND course_id = ?""";
        String insertQuery = """
                INSERT INTO studentCourses (student_id, course_id, grade) VALUES (?, ?, 0)""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, Integer.parseInt(student_id));
            checkStmt.setInt(2, Integer.parseInt(course_id));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return "Record already exists";
            }

            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, Integer.parseInt(student_id));
                insertStmt.setInt(2, Integer.parseInt(course_id));
                return insertStmt.executeUpdate() > 0 ? "Record added successfully" : "Nothing was updated";
            }
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String addCourse(String id,String course_name) {
        String command = "INSERT INTO courses (courseName,admin_id) VALUES (?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(command)) {

            stmt.setString(1, course_name);
            stmt.setInt(2, Integer.parseInt(id));

            return stmt.executeUpdate() > 0 ? "Record added successfully" : "Nothing was updated";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String getCourseAverage(String course_id) {
        String query = "SELECT grade FROM studentCourses WHERE course_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(course_id));
            ResultSet rs = stmt.executeQuery();

            double sum = 0, count = 0;
            while (rs.next()) {
                sum += rs.getInt("grade");
                count++;
            }
            return count > 0 ? String.valueOf(sum / count) : "No grades found";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String deleteCourse(String course_id) {
        String deleteStudentCourses = "DELETE FROM studentCourses WHERE course_id = ?";
        String deleteCourses = "DELETE FROM courses WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStudentStmt = connection.prepareStatement(deleteStudentCourses);
             PreparedStatement deleteCourseStmt = connection.prepareStatement(deleteCourses)) {

            deleteStudentStmt.setInt(1, Integer.parseInt(course_id));
            deleteStudentStmt.executeUpdate();

            deleteCourseStmt.setInt(1, Integer.parseInt(course_id));
            return deleteCourseStmt.executeUpdate() > 0 ? "Record deleted successfully" : "Nothing was updated";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String getAllCourses() {
        String query = "SELECT * FROM courses";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder response = new StringBuilder();
            response.append("<table border='1'>");
            response.append("<tr><th>ID</th><th>Course Name</th></tr>");

            while (rs.next()) {
                response.append("<tr>")
                        .append("<td>").append(rs.getInt("id")).append("</td>")
                        .append("<td>").append(rs.getString("courseName")).append("</td>")
                        .append("</tr>");
            }
            response.append("</table>");
            return response.toString();
        } catch (SQLException ex) {
            return "<p>Error: " + ex.getMessage() + "</p>";
        }
    }


    public String deleteStudentCourse(String student_id, String course_id,String admin_id) {
        String command = """
            DELETE FROM studentCourses 
            WHERE student_id = ? AND course_id = ? 
            AND course_id IN (SELECT id FROM courses WHERE admin_id = ?)""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(command)) {

            stmt.setInt(1, Integer.parseInt(student_id));
            stmt.setInt(2, Integer.parseInt(course_id));

            stmt.setInt(3, Integer.parseInt(admin_id));
            return stmt.executeUpdate() > 0 ? "Record deleted successfully" : "Nothing was updated";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String getAdminCourses(String admin_id) {
        String command = "SELECT id, courseName FROM courses WHERE admin_id = ?";
        StringBuilder response = new StringBuilder();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(command)) {

            stmt.setInt(1, Integer.parseInt(admin_id));

            try (ResultSet rs = stmt.executeQuery()) {


                response.append("<table border='1'><tr><th>Course ID</th><th>Course Name</th></tr>");
                while (rs.next()) {
                    response.append("<tr><td>").append(rs.getInt("id"))
                            .append("</td><td>").append(rs.getString("courseName"))
                            .append("</td></tr>");
                }
                response.append("</table>");

                if (response.length() == 0) {
                    return "No courses found for this admin.";
                }

            }
        } catch (SQLException ex) {
            return ex.getMessage();
        }

        return response.toString();
    }

}
