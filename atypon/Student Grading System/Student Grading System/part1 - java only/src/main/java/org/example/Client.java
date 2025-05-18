package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Client {

    private static HashMap<String,String>request = new HashMap<>();

    private static String getJsonString()
    {
        return new JSONObject(request).toString();
    }
    private static void getUserData()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("0-> for students");
        System.out.println("1-> for admins");
        String isAdmin = scanner.nextLine();
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        request.put("username",username);
        request.put("password",password);
        request.put("admin",isAdmin);
        request.put("id","-1");

    }
    private static void commandsAndQueriesStudents()
    {
        System.out.println("1 -> get your coures and grades");
        System.out.println("2 -> get course average");
        System.out.println("3 -> exit");



    }
    private static void commandsAndQueriesAdmins()
    {
        System.out.println("1 -> get all students and there grades");
        System.out.println("2 -> update user grade for course");
        System.out.println("3 -> add course to student");
        System.out.println("4 -> add course");
        System.out.println("5 -> delete course");
        System.out.println("6 -> delete student");
        System.out.println("7 -> get all courses");
        System.out.println("8 -> get course average");
        System.out.println("9 -> exit");



    }
    private static void getInput()
    {
        Scanner scanner = new Scanner(System.in);
        if(request.get("admin").equals("0")){
            switch (request.get("flag")) {
                case "2":
                    System.out.println("course id");
                    request.put("course_id",scanner.nextLine().strip());
                    break;
            }
        }
        else switch (request.get("flag")) {
            case "1":
            case "5":
            case "8":
                System.out.println("course id");
                request.put("course_id", scanner.nextLine().strip());
                break;
            case "2":
                System.out.println("course id");
                request.put("course_id", scanner.nextLine().strip());
                System.out.println("student id");
                request.put("student_id", scanner.nextLine().strip());
                System.out.println("grade");
                request.put("grade", scanner.nextLine().strip());
                break;
            case "3":
            case "6":
                System.out.println("course id");
                request.put("course_id", scanner.nextLine().strip());
                System.out.println("student id");
                request.put("student_id", scanner.nextLine().strip());
                break;
            case "4":
                System.out.println("course name");
                request.put("course_name", scanner.nextLine().strip());
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome");

        try {
            Socket socket = new Socket("localhost",7000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            request.put("id","-1");
            while(true) {

                if (request.get("id").equals("-1")) {
                    getUserData();
                    request.put("flag","0");
                    writer.println(getJsonString());
                    String Response = "";
                            do {
                                Response = reader.readLine().strip();
                            }while(reader.ready());
                    if (Response.equals("-1")) {
                        System.out.println("wrong username or password, try again please.");
                    } else {
                        System.out.println("you logged in successfully ");
                        request.put("id",Response);
                    }
                }
                    else{
                    if(request.get("admin").equals("0")) {
                        commandsAndQueriesStudents();
                        String input = new Scanner(System.in).nextLine().strip();
                        request.put("flag",input);

                        if(input.equals("3"))break;
                        getInput();
                        writer.println(getJsonString());

                        do
                        {
                            System.out.println(reader.readLine());
                        }while (reader.ready());
                    }else {
                        commandsAndQueriesAdmins();
                        String input = new Scanner(System.in).nextLine().strip();
                        request.put("flag",input);
                        if(input.equals("9"))break;
                        getInput();
                        writer.println(getJsonString());
                        do {
                            System.out.println(reader.readLine().strip());
                        }while(reader.ready());
                    }

                }

            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
}
