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

<table class="table">
<thead>
<td><a href="/">HOME</a></td>
<td><a href="/checkout">ORDER(<c:out value="${burgQty + fryQty}"/>)</a></td>
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

<form:form action="/fries" method="post" modelAttribute="fries">
	<form:input type="hidden" value="${orderId}" path="fOrder" />

<div class="form form-group">
<form:label path="qty"> Fry Quantity: 
<form:errors path="qty"/>
<form:input path="qty" type="number" min="1"/>
</form:label>
</div>

<div class="form form-group">
<table class="table table-hover">
<form:label path="type">
<thead>
<td>Type</td>
<td>Select</td>
</form:label>
<form:errors path="type"/>
</thead>
<tbody>
<tr>
<td>Golden</td>
<td><form:radiobutton path="type" value="golden" checked="checked"/></td>
</tr>
<tr>
<td>Crispy</td>
<td><form:radiobutton path="type" value="crispy"/></td>
</tr>
<tr>
<td>Light</td>
<td><form:radiobutton path="type" value="light"/></td>
</tr>
<td>No Fry</td>
<td><form:radiobutton path="type"/></td>
</tr>
</table>
</div>

<div class="form form-group">
<table class="table table-hover">
<thead>
<td>Ingredients</td>
<td>Yes</td>
<td>No</td>
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


<input type="hidden" name="orderId" value="${ orderId }">
<button class="btn btn-primary">Order Fries!</button>

</form:form>

<hr>

<form action="/burger" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-primary">More Burgers!</button>
</form>

<hr>

<form action="/checkout" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-primary">Checkout!</button>
</form>




</div>
</body>
</html>