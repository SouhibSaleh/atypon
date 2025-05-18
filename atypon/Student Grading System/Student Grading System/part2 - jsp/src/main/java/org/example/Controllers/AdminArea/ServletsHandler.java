package org.example.Controllers.AdminArea;

import org.example.Services.AuthValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/adminArea/handler")
public class ServletsHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        try{
         String option = req.getParameter("admin_choice");
            List<String> creds = AuthValidator.getToken((String) req.getSession()
                    .getAttribute("auth"),
                    "admin");
            System.out.println(creds);
            if (creds == null||!creds.get(2).equals("admin")) {
                res.sendRedirect("index.jsp");
                return;
            }
            System.out.println(option);
            String response = "";
            switch (option) {
                case "getAllStudentsGrades":
                    String courseId = req.getParameter("course_id");
                    req.setAttribute("course_id",courseId);
                    req.getRequestDispatcher("/adminArea/allstudentsgrades").forward(req, res);
                    break;

                case "updateStudentGrade":
                    String studentId = req.getParameter("student_id");
                    String grade = req.getParameter("grade");
                    courseId = req.getParameter("course_id");
                    req.setAttribute("studentId",studentId);
                    req.setAttribute("grade",grade);
                    req.setAttribute("course_id",courseId);
                    req.getRequestDispatcher("/adminArea/updategrade").forward(req, res);
                    break;

                case "addCourseToStudent":
                    studentId = req.getParameter("student_id");
                    courseId = req.getParameter("course_id");
                    req.setAttribute("course_id",courseId);
                    req.setAttribute("studentId",studentId);
                    req.getRequestDispatcher("/adminArea/addcoursetostudent").forward(req, res);
                    break;

                case "addCourse":
                    String courseName = req.getParameter("course_name");
                    req.setAttribute("course_name",courseName);
                    req.setAttribute("admin_id",String.valueOf(creds.get(3)));
                    req.getRequestDispatcher("/adminArea/addcourse").forward(req, res);
                    break;

                case "getCourseAverage":
                    courseId = req.getParameter("course_id");
                    req.setAttribute("course_id",courseId);
                    req.getRequestDispatcher("/adminArea/courseaverage").forward(req, res);

                    break;

                case "deleteCourse":
                    courseId = req.getParameter("course_id");
                    req.setAttribute("course_id",courseId);
                    req.getRequestDispatcher("/adminArea/deletecourse").forward(req, res);
                    break;

                case "getAllCourses":
                    req.getRequestDispatcher("/adminArea/allcourses").forward(req, res);

                    break;

                case "deleteStudentCourse":
                    studentId = req.getParameter("student_id");
                    courseId = req.getParameter("course_id");
                    req.setAttribute("course_id",courseId);
                    req.setAttribute("studentId",studentId);
                    req.setAttribute("admin_id",creds.get(3));
                    req.getRequestDispatcher("/adminArea/deletestudentcourse").forward(req, res);
                    break;
                case "getAdminCourses":
                    req.setAttribute("admin_id",creds.get(3));
                    req.getRequestDispatcher("/adminArea/mycourses").forward(req, res);
                    break;
                default:
                    response = "Invalid option selected!";
                    break;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
