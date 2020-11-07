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

</head>
<body>
<div class="container">
<div class="row">
<div class="col">
<h1 class="bg-danger text-light mt-2 pb-2 d-flex justify-content-around rounded shadow-lg">BurgerNow!</h1>
</div>
<div class="col"></div>
<div class="col"></div>
</div>
<hr>

<ul class="nav nav-pills justify-content-end">
  <li class="nav-item">
  	<form action="/checkout" method="get">
	<input type="hidden" name="orderId" value="${ orderId }"/>
	<button class="btn btn-link bg-danger text-light shadow pb-2">ORDER(<c:out value="${burgQty + fryQty}"/>)</button>
	</form>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light shadow" href="/">HOME</a>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light shadow" href="/account">ACCOUNT</a>
  </li>
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


<div class="form-inline bg-danger text-light rounded p-2 d-flex justify-content-around">
<form:form class="form-inline" action="/burger" method="post" modelAttribute="burger">
	<input type="hidden" name="orderId" value="${ orderId }">
	<form:errors path="bOrder"/>
	<form:input type="hidden" value="${orderId}" path="bOrder" />

<div class="row">
<div class="col">
<form:label path="qty"> Burgers:
<form:errors path="qty"/>
<form:input class="form-control ml-2" path="qty" type="number" min="1" max="500" value="1"/>
</form:label>
</div>


<div class="col mr-2 ml-2">
<table class="table table-hover text-light">
<form:label path="type">
<thead>
<th>Type</th>
<th>Select</th>
</form:label>
<form:errors path="type"/>
</thead>
<tbody>
<tr>
<td>No Burger</td>
<td><form:radiobutton path="type"/></td>
</tr>
<tr>
<td>Double-Double [$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ dblPrice }"/>]</td>
<td><form:radiobutton path="type" value="Double-Double" checked="checked"/></td>
</tr>
<tr>
<td>Cheeseburger [$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ chzPrice }"/>]</td>
<td><form:radiobutton path="type" value="Cheeseburger"/></td>
</tr>
<tr>
<td>Hamburger [$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ hamPrice }"/>]</td>
<td><form:radiobutton path="type" value="Hamburger"/></td>
</tr>
<tr>
<td>Grilled Cheese [$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${ grchzPrice }"/>]</td>
<td><form:radiobutton path="type" value="Grilled Cheese"/></td>
</tr>
</table>
</div>

<div class="col">
<table class="table table-hover text-light">
<thead>
<th>Ingredients</th>
<th>Yes</th>
<th>No</th>
<form:errors path="sauce"/>
<form:errors path="lettuce"/>
<form:errors path="tomato"/>
<form:errors path="onion"/>
<form:errors path="ketchup"/>
<form:errors path="pickles"/>
</thead>
<tbody>
<tr>
<td>Sauce</td>
<td><form:radiobutton path="sauce" value="true" checked="checked"/></td>
<td><form:radiobutton path="sauce" value="false"/></td>
</tr>
<tr>
<td>Lettuce</td>
<td><form:radiobutton path="lettuce" value="true" checked="checked"/></td>
<td><form:radiobutton path="lettuce" value="false"/></td>
</tr>
<tr>
<td>Tomato</td>
<td><form:radiobutton path="tomato" value="true" checked="checked"/></td>
<td><form:radiobutton path="tomato" value="false"/></td>
</tr>
<tr>
<td>Onion</td>
<td><form:radiobutton path="onion" value="true" checked="checked"/></td>
<td><form:radiobutton path="onion" value="false"/></td>
</tr>
<tr>
<td>Ketchup</td>
<td><form:radiobutton path="ketchup" value="true"/></td>
<td><form:radiobutton path="ketchup" value="false" checked="checked"/></td>
</tr>
<tr>
<td>Pickles</td>
<td><form:radiobutton path="pickles" value="true"/></td>
<td><form:radiobutton path="pickles" value="false" checked="checked"/></td>
</tr>
</tbody>
</table>
</div>

<form:hidden path="price" value="0.01" />

<div class="col mr-n3">
<button class="btn btn-warning">OrderNow!</button>
</div>
</form:form>

<div class="col mr-n2 ml-n2">
<form action="/fries" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Next, Fries!</button>
</form>
</div>

<div class="col mr-n2 ml-n3">
<form action="/checkout" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Checkout!</button>
</form>
</div>

</div>

</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>