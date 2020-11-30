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
  	<form action="/checkout" method="get">
	<input type="hidden" name="orderId" value="${ orderId }"/>
	<button class="btn btn-warning bg-light text-dark border border-white pb-2">ORDER(<c:out value="${burgQty + fryQty}"/>)</button>
	</form>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light" href="/">HOME</a>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light" href="/account">ACCOUNT</a>
  </li>
  <li class="nav-item">
<c:choose>
<c:when test="${user_id != null }">
<li class="nav-item">
<a class="nav-link bg-danger text-light" href="/logout">LOGOUT</a>
</li>
</c:when>
<c:otherwise>
<li class="nav-item">
<a class="nav-link bg-danger text-light" href="/login">LOGIN</a>
</li>
</c:otherwise>
</c:choose>
</ul>
<hr>

<table class="table table-hover table-borderless bg-danger rounded text-light">
<thead>
<th>Your Order!</th>
<th>Action</th>
</thead>
<tbody>
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
 - $<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ burger.price * burger.qty }"/>
</td>


<td>
<div class="form-inline">
<form action="/edit/burger/${ burger.id }" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Edit</button>
</form>

<form action="/delete/burger/${ burger.id }" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-secondary">Delete</button>
</form>
</div>
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

<td>
<div class="form-inline">
<form action="/edit/fry/${ fry.id }" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Edit</button>
</form>

<form action="/delete/fry/${ fry.id }" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-secondary">Delete</button>
</form>
</div>
</td>

</tr>
</c:forEach>

<tr>
<td>--------------------------Sub-Total-></td>
<td>$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ subtotal }"/></td>
</tr>

<tr>
<td>---------------------------------Tax-></td>
<td>$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ tax }"/></td>
</tr>

<tr>
<td>-------------------------------Total-></td>
<td>$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ total }"/></td>
</tr>

</tbody>
</table>

<hr>
<ul class="nav nav-justified">
  <li class="nav-item">
<form action="/burger" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">More Burgers!</button>
</form>
</li>
  <li class="nav-item">
<form action="/fries" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">More Fries!</button>
</form>
</li>
  <li class="nav-item">
<form action="/purchase" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Purchase!</button>
</form>
</li>
</ul>


<hr>

</div>
</body>
</html>