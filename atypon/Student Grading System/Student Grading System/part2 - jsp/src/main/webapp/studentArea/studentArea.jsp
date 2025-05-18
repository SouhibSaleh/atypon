<!DOCTYPE html>
<html>

<head>
    <title>STUDENTS AREA</title>
</head>

<body>
    <form action="average" method="post">
        get course average: <input type="text" name="course_id" id="course_id">
        <input type="submit">
    </form>
    <br>
    <form action="courses" method="get">
        get my courses:
        <input type="submit">
    </form>
    <%@ page import="java.util.Base64" %>

<%
         String encodedAuth = (String) session.getAttribute("auth");
          String decodedAuth = "";
             if (encodedAuth != null) {
                 decodedAuth = new String(Base64.getDecoder().decode(encodedAuth.split(" ")[1]), "UTF-8");
                 String args[] = decodedAuth.split(" ");
                 if(!args[2].equals("student")){
                          out.println("<script>");
                                    out.println("window.location.replace('/part2/index.jsp');");
                                    out.println("</script>");
                    return;
                 }
             }

%>
    <br>

</body>

</html>