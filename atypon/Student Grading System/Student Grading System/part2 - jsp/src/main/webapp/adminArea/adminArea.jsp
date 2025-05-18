<!DOCTYPE html>
<html>

<head>
    <title>ADMIN AREA</title>
</head>

<body>
    <form action="handler" method="post">
        <label for="course_id">Course ID:</label>
        <input type="text" id="course_id" name="course_id"><br>
        <label for="course_id">Course Name:</label>
        <input type="text" id="course_name" name="course_name"><br>
        <label for="student_id">Student ID:</label>
        <input type="text" id="student_id" name="student_id"><br>
        <label for="grade">Grade:</label>
        <input type="text" id="grade" name="grade"><br>

        <p>Select an action:</p>
        <label><input type="radio" name="admin_choice" value="getAllStudentsGrades"> Get All Students'
            Grades</label><br>
        <label><input type="radio" name="admin_choice" value="updateStudentGrade"> Update Student Grade</label><br>
        <label><input type="radio" name="admin_choice" value="addCourseToStudent"> Add Course to Student</label><br>
        <label><input type="radio" name="admin_choice" value="addCourse"> Add Course</label><br>
        <label><input type="radio" name="admin_choice" value="getCourseAverage"> Get Course Average</label><br>
        <label><input type="radio" name="admin_choice" value="deleteCourse"> Delete Course</label><br>
        <label><input type="radio" name="admin_choice" value="getAllCourses"> Get All Courses</label><br>
        <label><input type="radio" name="admin_choice" value="deleteStudentCourse"> Delete Student Course</label><br>
        <label><input type="radio" name="admin_choice" value="getAdminCourses"> Get Your Courses</label><br>

        <button type="submit">Submit</button>
    </form>
    <%@ page import="java.util.Base64" %>

<%
         String encodedAuth = (String) session.getAttribute("auth");
          String decodedAuth = "";
             if (encodedAuth != null) {
                 decodedAuth = new String(Base64.getDecoder().decode(encodedAuth.split(" ")[1]), "UTF-8");
                 String args[] = decodedAuth.split(" ");
                 if(!args[2].equals("admin")){
                          out.println("<script>");
                                    out.println("window.location.replace('/part2/index.jsp');");
                                    out.println("</script>");
                    return;
                 }
             }

%>

</body>

</html>