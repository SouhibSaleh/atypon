package org.example;
import org.example.repos.AdminRepo;
import org.example.repos.StudentRepo;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server{


    public static void main(String[] args) {
        int request = 0;


        try{
            System.out.println("Server Connected");
            ServerSocket serverSocket = new ServerSocket(7000);
            while(true) {
                System.out.println(request+" server running");
                request++;
                Socket acceptedSocket = serverSocket.accept();
                Runnable serve = new serviceHandler(acceptedSocket);
                new Thread(serve).start();
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }finally {

        }


    }
    private static class serviceHandler implements Runnable{
        private Connection connection;
        private Socket socket;
        private JSONObject request = new JSONObject();
        private StudentRepo studentRepo = null;
        private AdminRepo adminRepo = null;
        public serviceHandler(Socket socket)
        {
            this.socket = socket;
        }
        private String getKeyValue(String key)
        {

            return (request.isNull(key))?"":request.get(key).toString();
        }

        private String getResponseStudent() {

            return switch (getKeyValue("flag")){
                case "0" -> studentRepo.getUserId(getKeyValue("username"),getKeyValue("password"));
                case "1" -> studentRepo.getStudentCources(getKeyValue("id"));
                case "2" -> studentRepo.getCourseAverage(getKeyValue("course_id"));
                default -> "";
            };
        }


        private String getResponseAdmin() throws SQLException {

            return switch (getKeyValue("flag"))
                    {
                      case "0" -> adminRepo.getAdminId(
                              getKeyValue("username"),
                              getKeyValue("password"));
                      case "1" -> adminRepo.getAllStudentsGrades(
                              getKeyValue("course_id"));
                      case "2" -> adminRepo.updateStudentGrade(
                              getKeyValue("grade"),
                              getKeyValue("student_id"),
                              getKeyValue("course_id"));
                      case "3" -> adminRepo.addCourseToStudent(
                              getKeyValue("student_id"),
                              getKeyValue("course_id"));
                      case "4" -> adminRepo.addCourse(
                              getKeyValue("course_name"),
                              getKeyValue("id"));
                      case "5" -> adminRepo.deleteCourse(getKeyValue("course_id"));
                      case "6" -> adminRepo.deleteStudentCourse(
                              getKeyValue("student_id"),
                              getKeyValue("course_id"));
                      case "7" -> adminRepo.getAllCourses();
                      case "8" -> adminRepo.getCourseAverage(
                              getKeyValue("course_id"));
                        default -> "";
                    };



        }
        @Override
        public void run() {
            try{
                connection =  DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/studentgrading",
                        "root",
                        "shays123_4??");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                while(true) {
                    String message = reader.readLine();
                    request = new JSONObject(message);
                    if(getKeyValue("flag").equals("-1"))continue;

                    if(getKeyValue("admin").equals("0")) {
                        studentRepo = new StudentRepo(connection);
                        if(getKeyValue("flag").equals("3"))break;
                        writer.println(getResponseStudent());
                    }
                    else {
                        if(adminRepo==null)adminRepo = new AdminRepo(connection);
                        if(getKeyValue("flag").equals("9"))break;
                        writer.println(getResponseAdmin());
                    }

                }
            }catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

}
