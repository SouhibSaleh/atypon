package org.example.Controllers.StudentArea;

import org.example.Repos.StudentRepo;
import org.example.Services.AuthValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/studentArea/average")
public class CoursesAverage extends HttpServlet {
    private StudentRepo studentRepo = new StudentRepo();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        try {
            List<String> creds = AuthValidator.getToken((String) req.getSession().getAttribute("auth"),"student");
            if (creds == null||!creds.get(2).equals("student")) {
                res.sendRedirect("index.jsp");
                return;
            }
            String course_id = (String)req.getParameter("course_id");
            if(course_id==null)
            {
                return;
            }
            String value  =studentRepo.getCourseAverage(course_id);
            req.setAttribute("result",value);
            req.getRequestDispatcher("/result.jsp").forward(req, res);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

