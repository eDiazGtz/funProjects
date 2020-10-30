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

<form:form action="/burger" method="post" modelAttribute="burger">
	<form:input type="hidden" value="${orderId}" path="bOrder" />

<div class="form form-group">
<form:label path="qty"> Burger Quantity: 
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
<td>Double-Double</td>
<td><form:radiobutton path="type" value="Double-Double" checked="checked"/></td>
</tr>
<tr>
<td>Cheeseburger</td>
<td><form:radiobutton path="type" value="Cheeseburger"/></td>
</tr>
<tr>
<td>Hamburger</td>
<td><form:radiobutton path="type" value="Hamburger"/></td>
</tr>
<tr>
<td>Grilled Cheese</td>
<td><form:radiobutton path="type" value="Grilled Cheese"/></td>
</tr>
<tr>
<td>No Burger</td>
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


<input type="hidden" name="orderId" value="${ orderId }">
<button class="btn btn-primary">Order Burger!</button>

</form:form>

<hr>

<form action="/fries" method="get">
<input type="hidden" name="orderId" value="${ orderId }"/>
<button class="btn btn-primary">Next, Fries!</button>
</form>


<!-- <div class="form form-group"> -->
<%-- <form:label path="ingredients"> Ingredients: --%>
<%-- <form:errors path="ingredients"/> --%>
<%-- <c:forEach items="${ allIngredients }" var="ingredient"> --%>
<%-- <p>${ ingredient.name } <form:checkbox path="ingredients" value="${ ingredient.id }"/></p> --%>
<%-- </c:forEach> --%>
<%-- </form:label> --%>
<!-- </div> -->

</div>
</body>
</html>