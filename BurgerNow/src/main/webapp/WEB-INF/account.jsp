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
<h1 class="bg-danger text-light mt-2 pb-2 d-flex justify-content-around rounded shadow-lg">BurgerNow!</h1>
</div>
<div class="col"></div>
<div class="col"></div>
</div>
<hr>


<ul class="nav nav-pills justify-content-end">  
  <li class="nav-item">
	<form action="/new/order/burger" method="post">
	<input type="hidden" name="orderId" value="${ orderId }"/>
	<button class="btn btn-link bg-danger text-light shadow pb-2">ORDERNOW!</button>
	</form>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light shadow" href="/">HOME</a>
  </li>
  <li class="nav-item">
    <a class="nav-link active bg-light text-dark shadow" href="/account">ACCOUNT</a>
  </li>
  <li class="nav-item">
<c:choose>
<c:when test="${user_id != null }">
<li class="nav-item">
<a class="nav-link bg-danger text-light shadow" href="/logout">LOGOUT</a>
</li>
</c:when>
<c:otherwise>
<li class="nav-item">
<a class="nav-link bg-danger text-light shadow" href="/login">LOGIN</a>
</li>
</c:otherwise>
</c:choose>
</ul>
<hr>

<div class="row">
<div class="col"></div>
<div class="col bg-danger text-light mt-2 pb-2 rounded">
<h3 class="d-flex justify-content-around">Your Account!</h3>

<p>${ errorMessages }</p>

  <div class="form-row align-items-center d-flex justify-content-around">
    <div class="col-auto">
<form method="post" action="/account/first/${ user.id }">
<label for="firstName">First name:</label>
<input type="text" class="form-control" id="firstName" name="firstName" value="${ user.firstName }" required>
<button class="btn btn-warning">Edit First Name</button>
</form>
</div>
</div>
<div class="form-row align-items-center d-flex justify-content-around">
<div class="col-auto">
<form method="post" action="/account/last/${ user.id }" >
<label for="lastName">Last name:</label>
<input type="text" class="form-control" id="lastName" name="lastName" value="${ user.lastName }" required>
<button class="btn btn-warning">Edit Last Name</button>
</form>
</div>
</div>
<div class="form-row align-items-center d-flex justify-content-around">
<div class="col-auto">
<form method="post" action="/account/email/${ user.id }" >
<label for="email">Email:</label>
<input type="email" class="form-control" id="email" name="email" value="${ user.email }" required>
<button class="btn btn-warning">Edit Email</button>
</form>
</div>
</div>
</div>
<div class="col"></div>
</div>
<br>
<br>

</div>
</body>
</html>