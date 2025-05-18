package org.example.Controllers.AdminArea;

import org.example.Repos.AdminRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminArea/deletestudentcourse")
public class DeleteStudentCourse extends HttpServlet {
    private AdminRepo adminRepo = new AdminRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        try{

            String student_id = req.getParameter("student_id");
            String course_id = (String) req.getAttribute("course_id");
            String admin_id = (String) req.getAttribute("admin_id");
            String result = adminRepo.deleteStudentCourse(student_id,course_id,admin_id);

            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req,res);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
