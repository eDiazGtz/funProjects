<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<br>




<ul class="nav nav-pills justify-content-end">  
  <li class="nav-item">
	<form action="/new/order/burger" method="post">
	<input type="hidden" name="orderId" value="${ orderId }"/>
	<button class="btn btn-link bg-danger text-light shadow pb-2">ORDERNOW!</button>
	</form>
  </li>
  <li class="nav-item">
    <a class="nav-link active bg-light text-dark shadow" href="/">HOME</a>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light shadow" href="/account">ACCOUNT</a>
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

<div class="col m-n5" id="body"></div>

<div class="col">
<table class="table table-hover table-borderless table-sm bg-danger text-light rounded p-5 mr-5" style="height:100px; overflow:scroll">
<thead>
<th>Previous Orders</th>
</thead>
<tbody>
<c:choose>
<c:when test="${user_id != null }">

<c:forEach items="${ guestOrders }" var="order">
<c:if test="${ order.complete == true}">
<c:forEach items="${ order.burgers }" var="burger">
<tr>
<td>${ burger.qty } ${ burger.type }
<c:if test = "${ burger.sauce == true || burger.lettuce == true || burger.tomato == true || burger.onion == true || burger.ketchup == true || burger.pickles == true}">
-
</c:if>

<c:if test = "${  burger.sauce == true }">
Sauce 
</c:if>
<c:if test = "${  burger.lettuce == true }">
Lettuce 
</c:if>
<c:if test = "${  burger.tomato == true }">
Tomato 
</c:if>
<c:if test = "${  burger.onion == true }">
Onion 
</c:if>
<c:if test = "${  burger.ketchup == true }">
Ketchup 
</c:if>
<c:if test = "${  burger.pickles == true }">
Pickles 
</c:if>
- $<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ burger.qty * burger.price }"/>
</td>
</tr>
</c:forEach>

<c:forEach items="${ order.fries }" var="fry">
<tr>
<td>${ fry.qty } ${ fry.type } 
<c:if test = "${ fry.sauce == true || fry.onion == true || fry.ketchup == true || fry.cheese == true}">
-
</c:if>

<c:if test = "${  fry.sauce == true }">
Sauce 
</c:if>
<c:if test = "${  fry.onion == true }">
Onion 
</c:if>
<c:if test = "${  fry.ketchup == true }">
Ketchup 
</c:if>
<c:if test = "${  fry.cheese == true }">
Cheese 
</c:if>
- $<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ fry.qty * fry.price }"/>
</td>
</tr>
</c:forEach>

<tr>
<td>-------------------------------Total-></td>
<td>$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ order.total }"/></td>
</tr>

<td><hr></td>
<td>
<form action="/checkout" method="get">
<input type="hidden" name="orderId" value="${ order.id }"/>
<button class="btn btn-warning">Order Again!</button>
</form>
</td>

</c:if>

</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td>Register <a class="text-light" href="/register">here</a> so you can start saving your orders!</td>
</tr>
<tr>
<td>Already Burgered Up? Login <a class="text-light" href="/login">here</a> so you can get all your delicious orders!</td> 
</tr>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
</div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>