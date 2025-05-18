package org.example.Controllers.AdminArea;

import org.example.Repos.AdminRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminArea/updategrade")
public class UpdateStudentGrade extends HttpServlet {
    private AdminRepo adminRepo = new AdminRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        try{
            String course_id = req.getParameter("course_id");
            String student_id = req.getParameter("student_id");
            String grade = req.getParameter("grade");
            String result = adminRepo.updateStudentGrade(grade,student_id,course_id);
            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req,res);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
