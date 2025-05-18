package com.example.demo.services;

import com.example.demo.models.Admin;
import com.example.demo.models.Course;
import com.example.demo.models.CourseGrade;
import com.example.demo.models.Student;

import java.util.List;
import java.util.Map;

public class TableMaker {
    public static String makeCourseGradesTable(List<CourseGrade> list) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: left;'>");
        html.append("<tr>")
                .append("<th>Course ID</th>")
                .append("<th>Course Name</th>")
                .append("<th>Grade</th>")
                .append("</tr>");

        for (CourseGrade course : list) {
            int courseId = course.getId();
            String courseName = course.getCourseName();
            int grade = course.getGrade();

            html.append("<tr>")
                    .append("<td>").append(courseId).append("</td>")
                    .append("<td>").append(courseName).append("</td>")
                    .append("<td>").append(grade).append("</td>")
                    .append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }
    public static String makeAdminsTable(List<Admin> list) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: left;'>");
        html.append("<tr>")
                .append("<th>ID</th>")
                .append("<th>Username</th>")
                .append("</tr>");

        for (Admin admin : list) {
            int id = admin.getId();
            String username = admin.getUsername();

            html.append("<tr>")
                    .append("<td>").append(id).append("</td>")
                    .append("<td>").append(username).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        return html.toString();
    }
    public static String makeStudentsTable(List<Student>list){
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: left;'>");
        html.append("<tr>")
                .append("<th>ID</th>")
                .append("<th>Username</th>")
                .append("</tr>");

        for (Student student : list) {
            int id = student.getId();
            String username = student.getUsername();

            html.append("<tr>")
                    .append("<td>").append(id).append("</td>")
                    .append("<td>").append(username).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        return html.toString();
    }
    public static String makeCoursesTable(List<Course> list) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: left;'>");
        html.append("<tr>")
                .append("<th>Course ID</th>")
                .append("<th>Course Name</th>")
                .append("<th>Admin ID</th>")
                .append("</tr>");

        for (Course course : list) {
            int id = course.getId();
            String courseName = course.getCourseName();
            int adminId = course.getAdmin_id();

            html.append("<tr>")
                    .append("<td>").append(id).append("</td>")
                    .append("<td>").append(courseName).append("</td>")
                    .append("<td>").append(adminId).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        return html.toString();
    }

}
