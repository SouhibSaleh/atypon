package org.example.Controllers.StudentArea;

import org.example.Repos.StudentRepo;
import org.example.Services.AuthValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/studentArea/courses")
public class StudentCourses extends HttpServlet {
    private StudentRepo studentRepo = new StudentRepo();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        try{
            List<String> creds = AuthValidator.getToken(
                    (String) req.getSession()
                            .getAttribute("auth"),"student");
            System.out.println(creds.get(2));
            if (creds == null||!creds.get(2).equals("student")) {
                res.sendRedirect("index.jsp");
                return;
            }
            String result = studentRepo.getStudentCourses(creds.get(3));
            req.setAttribute("result",result);
            req.getRequestDispatcher("/result.jsp").forward(req, res);


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }


}
