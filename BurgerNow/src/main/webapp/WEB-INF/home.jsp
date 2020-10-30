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

<h1>BurgerNow!</h1>

<table class="table">
<thead>
<td><a href="/">HOME</a></td>
<td>ACCOUNT</td>
<c:choose>
<c:when test="${user_id != null }">
<td><a href="/logout">LOGOUT</a></td>
</c:when>
<c:otherwise>
<td><a href="/login">LOGIN</a></td>
</c:otherwise>	
</c:choose>
</thead>
</table>
<hr>


<div class="row">
<div class="col">
<h4>Let's Burger!</h4>
<form action="/order/burger" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-primary">Order Now!</button>
</form>
</div>

<div class="col">
<table class="table table-hover">
<thead>
<td>Previous Orders</td>
</thead>
<tbody>
<tr>
<td>Orders By Complete</td>
</tr>
</tbody>
</table>
</div>

</div>
</div>
</body>
</html>