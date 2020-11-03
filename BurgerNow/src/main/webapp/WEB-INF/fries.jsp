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
  	<form action="/checkout" method="post">
	<input type="hidden" name="orderId" value="${ orderId }"/>
	<button class="btn btn-link bg-danger text-light shadow">ORDER(<c:out value="${burgQty + fryQty}"/>)</button>
	</form>
  </li>
  <li class="nav-item">
    <a class="nav-link bg-danger text-light shadow" href="/">HOME</a>
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


<div class="form-inline bg-danger text-light rounded p-2 d-flex justify-content-around">
<form:form action="/fries" method="post" modelAttribute="fries">
	<form:input type="hidden" value="${orderId}" path="fOrder" />

<div class="row">
<div class="col">
<form:label path="qty"> Fries: 
<form:errors path="qty"/>
<form:input path="qty" class="form-control" type="number" min="1" max="500" value="1" />
</form:label>
</div>

<div class="col">
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
<td>No Fry</td>
<td><form:radiobutton path="type"/></td>
</tr>
<tr>
<td>Golden</td>
<td><form:radiobutton path="type" value="Golden Fries" checked="checked"/></td>
</tr>
<tr>
<td>Crispy</td>
<td><form:radiobutton path="type" value="Crispy Fries"/></td>
</tr>
<tr>
<td>Light</td>
<td><form:radiobutton path="type" value="Light Fries"/></td>
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
<form:errors path="onion"/>
<form:errors path="ketchup"/>
<form:errors path="cheese"/>
</thead>
<tbody>
<tr>
<td>Sauce</td>
<td><form:radiobutton path="sauce" value="true"/></td>
<td><form:radiobutton path="sauce" value="false" checked="checked"/></td>
</tr>
<tr>
<td>Onion</td>
<td><form:radiobutton path="onion" value="true" /></td>
<td><form:radiobutton path="onion" value="false" checked="checked"/></td>
</tr>
<tr>
<td>Ketchup</td>
<td><form:radiobutton path="ketchup" value="true"/></td>
<td><form:radiobutton path="ketchup" value="false" checked="checked"/></td>
</tr>
<tr>
<td>Cheese</td>
<td><form:radiobutton path="cheese" value="true"/></td>
<td><form:radiobutton path="cheese" value="false" checked="checked"/></td>
</tr>
</tbody>
</table>
</div>

<div class="col">
<input type="hidden" name="orderId" value="${ orderId }">
<button class="btn btn-warning">OrderNow!</button>
</div>

</form:form>
<hr>

<div class="col">
<form action="/order/burger" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Burgers!</button>
</form>
</div>

<hr>

<form action="/order/checkout" method="post">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-warning">Checkout!</button>
</form>
</div>




</div>
</body>
</html>