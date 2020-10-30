<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BurgerNow!</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>
<body>
	<div class="container">
		<h1>Welcome To BurgerNow!</h1>
				<h4>Login</h4>
				
				<p>${ loginError }</p>
				<form method="post" action="/login">
					<div class="form-group">
						<label>Email:</label> <input class="form-control" type="email"
							name="lemail">
					</div>

					<div class="form-group">
						<label>Password:</label> <input class="form-control"
							type="password" name="lpassword">
					</div>

					<button class="btn btn-primary">Log In!</button>
				</form>
<a href="/register">Haven't Burgered Up? Register!</a>
		</div>
</body>
</html>