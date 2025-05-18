package org.example;

import org.example.Repos.AdminRepo;
import org.example.Repos.StudentRepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private StudentRepo studentRepo = new StudentRepo();
    private AdminRepo adminRepo = new AdminRepo();

    private String generateTokenHelper(String type,String username,String password,String role){

        return type+" "+ Base64.getEncoder().encodeToString((username+" "+password+" "+role).getBytes());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String memberType = req.getParameter("member_type");
            String token = "";
            if (memberType.equals("admin")) {
                String id = adminRepo.getAdminId(username,password);
                if(id=="-1")
                    res.sendRedirect("index.jsp");
                token  = generateTokenHelper("mybasic",username,password,"admin");
                req.getSession().setAttribute("auth",token);
                res.sendRedirect("adminArea/adminArea.jsp");


            } else {
                String id = studentRepo.getUserId(username,password);
                if(id=="-1")
                    res.sendRedirect("index.jsp");
                token  = generateTokenHelper("mybasic",username,password,"student");
                req.getSession().setAttribute("auth",token);
                res.sendRedirect("studentArea/studentArea.jsp");
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
