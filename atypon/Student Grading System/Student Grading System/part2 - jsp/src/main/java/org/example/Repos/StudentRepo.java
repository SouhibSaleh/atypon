package org.example.Repos;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;

public class StudentRepo {
    private DataSource dataSource;

    public StudentRepo() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
        } catch (Exception e) {
            throw new RuntimeException("Database connection error!", e);
        }
    }

    public String getUserId(String username, String password) {
        String query = "SELECT id FROM students WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return String.valueOf(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "-1";
    }

    public String getCourseAverage(String courseId) {
        String query = "SELECT grade FROM studentCourses WHERE course_id = ?";
        double sum = 0;
        double count = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(courseId));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sum += rs.getInt("grade");
                    count++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
        System.out.println(count);
        return count > 0 ? String.valueOf(sum / count) : "0";
    }

    public String getStudentCourses(String studentId) {
        if ("-1".equals(studentId)) return "-1";

        String query = "SELECT courses.id, courses.courseName, studentCourses.grade " +
                "FROM studentCourses " +
                "JOIN courses ON studentCourses.course_id = courses.id " +
                "WHERE studentCourses.student_id = ?";

        StringBuilder result = new StringBuilder();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, studentId);
            result.append("<table border='1'>")
                    .append("<tr><th>ID</th><th>Course Name</th><th>Grade</th></tr>");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.append("<tr>")
                            .append("<td>").append(rs.getInt("id")).append("</td>")
                            .append("<td>").append(rs.getString("courseName")).append("</td>")
                            .append("<td>").append(rs.getString("grade")).append("</td>")
                            .append("</tr>");
                }
                result.append("</table>");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
        return result.toString();
    }
}
