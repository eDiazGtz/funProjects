<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Event</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>
<body>
<div class="container">

<h3>Edit your Event: ${event.name}</h3>

<hr>

<form:form action="/events/${event.id}/edit" method="post" modelAttribute="event">
	<form:input type="hidden" value="${user_id}" path="host" />

<div class="form form-group">
<form:label path="name"> Name: 
<form:errors path="name"/>
<form:input path="name"/>
</form:label>
</div>

<div class="form form-group">
<form:label path="date"> Date: 
<form:errors path="date"/>
<form:input path="date"/>
</form:label>
</div>

		<div class="form-group">
			<form:label path="location">Location</form:label>
			<form:errors path="location" />
			<form:input path="location" />
		</div>
		
		<div class="form form-group">
			<form:label path="state"> State:        
			<form:errors path="state"/>
			<form:select path="state">
				<option value="CA">CA</option>
				<option value="UT">UT</option>
				<option value="TX">TX</option>
				<option value="CO">CO</option>
				<option value="WA">WA</option>
				<option value="NV">NV</option>
			</form:select>
			</form:label>
		</div>
		<p>Current State: ${event.state}</p>

<button class="btn btn-primary">Edit Event!</button>
</form:form>

</div>
</body>
</html>