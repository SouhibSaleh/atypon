package org.example.Controllers.AdminArea;

import org.example.Repos.AdminRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminArea/allcourses")
public class GetAllCourses extends HttpServlet {
    private AdminRepo adminRepo = new AdminRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        try{

            String result = adminRepo.getAllCourses();

            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req,res);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
