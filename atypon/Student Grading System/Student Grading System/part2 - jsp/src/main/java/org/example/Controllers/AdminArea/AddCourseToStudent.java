package org.example.Controllers.AdminArea;

import org.example.Repos.AdminRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminArea/addcoursetostudent")
public class AddCourseToStudent extends HttpServlet {
    private AdminRepo adminRepo = new AdminRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        try{

            String course_id = req.getParameter("course_id");
            String student_id = req.getParameter("student_id");

            String result = adminRepo.addCourseToStudent(student_id,course_id);

            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req,res);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
}
}
