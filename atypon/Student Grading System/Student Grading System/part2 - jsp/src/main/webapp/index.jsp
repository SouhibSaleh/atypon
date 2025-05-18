<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
</head>

<body>
    <form action="login" method="POST" id="login">
        <label>Username:</label> <input type="text" name="username"><br>
        <label>Password:</label> <input type="password" name="password"><br>
        <label>Member Type:</label>
        <br>
        <input type="radio" name="member_type" value="admin"> Admin
        <input type="radio" name="member_type" value="student"> Student
        <br>
        <input type="submit" value="Login">
    </form>


    <script src="script.js"></script>

</body>

</html>