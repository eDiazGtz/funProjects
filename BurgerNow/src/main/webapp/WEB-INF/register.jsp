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
<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<div class="container">
		<div class="row" id="header">
			<div class="col">
				<h1
					class="bg-danger text-light mt-2 pb-2 d-flex justify-content-around rounded shadow-lg">BurgerNow!</h1>
			</div>
			<div class="col"></div>
			<div class="col"></div>
		</div>
		<br>

		<ul class="nav nav-pills justify-content-end">
			<li class="nav-item">
				<form action="/new/order/burger" method="post">
					<input type="hidden" name="orderId" value="${ orderId }" />
					<button class="btn btn-link bg-danger text-light shadow pb-2">ORDERNOW!</button>
				</form>
			</li>
			<li class="nav-item"><a
				class="nav-link bg-danger text-light shadow" href="/">HOME</a></li>
			<li class="nav-item"><a
				class="nav-link bg-danger text-light shadow" href="/account">ACCOUNT</a>
			</li>
			<li class="nav-item"><c:choose>
					<c:when test="${user_id != null }">
						<li class="nav-item"><a
							class="nav-link bg-danger text-light shadow" href="/logout">LOGOUT</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a
							class="nav-link bg-danger text-light shadow" href="/login">LOGIN</a>
						</li>
					</c:otherwise>
				</c:choose>
		</ul>
		<hr>


		<div class="row">
			<div class="col"></div>
			<div class="col bg-danger text-light mt-2 pb-2 rounded">

				<h2 class="d-flex justify-content-around">Register</h2>
				<form:form action="/register" method="post" modelAttribute="user">

					<div class="form-group">
						<form:label path="firstName">First Name</form:label>
						<form:errors path="firstName" />
						<form:input class="form-control col-form-label" path="firstName" />
					</div>

					<div class="form-group">
						<form:label path="lastName">Last Name</form:label>
						<form:errors path="lastName" />
						<form:input class="form-control col-form-label" path="lastName" />
					</div>

					<div class="form-group">
						<form:label path="email">Email</form:label>
						<form:errors path="email" />
						<form:input type="email" class="form-control col-form-label"
							path="email" />
					</div>


					<div class="form-group">
						<form:label path="password">Password</form:label>
						<form:errors path="password" />
						<form:input type="password" class="form-control col-form-label"
							path="password" />
					</div>

					<div class="form-group">
						<form:label path="confirmPassword">Confirm Password</form:label>
						<form:errors path="confirmPassword" />
						<form:input type="password" class="form-control col-form-label"
							path="confirmPassword" />
					</div>

					<input type="hidden" name="rewards" value=0 />
					<input class="btn btn-warning" type="submit" value="Register" />
				</form:form>
				<a class="text-warning" href="/login">Already Ready for Burgers?
					Log In!</a>
			</div>
			<div class="col"></div>
		</div>
		<br> <br>

	</div>
</body>
</html>