package org.example.Controllers.AdminArea;

import org.example.Repos.AdminRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminArea/addcourse")
public class AddCourse extends HttpServlet {
    private AdminRepo adminRepo = new AdminRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        try{

            String course_name = req.getParameter("course_name");
            String admin_id = (String) req.getAttribute("admin_id");

            String result = adminRepo.addCourse(admin_id,course_name);

            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req,res);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
