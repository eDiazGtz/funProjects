<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Events Dashboard</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>
<body>
<div class="container">

<h1>Events Dashboard</h1>

<hr>
<a href="/dashboard">Dashboard</a> | <a href="/logout">Logout</a>
<hr>

<h5>Here are some of the events in your state:</h5>
<table class="table table-hover">
<thead>
<tr>
<td>Name</td>
<td>Date</td>
<td>Location</td>
<td>Guests</td>
<td>Action/Status</td>
</tr>
</thead>
<tbody>
<c:forEach items="${inState}" var="event">
<tr>
	<td><a href="/events/${event.id}">${event.name}</a></td>
	<td>${event.date}</td>
	<td>${event.location}</td>
	<td>${event.attendees.size() }</td>
	<c:choose>

	<c:when test="${event.host.id == user_id }">
	<td><a href="/events/${event.id}/edit">Edit</a> | <a href="/delete/${event.id}">Delete</a></td>
	</c:when>

	<c:when test="${event.attendees.contains(user)}">
	<td><p>Joining</p> | <a href="/unjoin/${event.id}">Cancel</a></td>

	</c:when>
	<c:otherwise>
	<td><a href="/join/${event.id}">Join</a></td>
	</c:otherwise>	
	</c:choose>
	</tr>
	</c:forEach>
	</tbody>
</table>

<h5>Here are some of the events in other states:</h5>
<table class="table table-hover">
<thead>
<tr>
<td>Name</td>
<td>Date</td>
<td>Location</td>
<td>Guests</td>
<td>Action/Status</td>
</tr>
</thead>
<tbody>
<c:forEach items="${notInState}" var="event">
<tr>
	<td><a href="/events/${event.id}">${event.name}</a></td>
	<td>${event.date}</td>
	<td>${event.location}</td>
	<td>${event.attendees.size() }</td>
	<c:choose>
	<c:when test="${event.host.id == user_id }">
	<td><a href="/events/${event.id}/edit">Edit</a> | <a href="/delete/${event.id}">Delete</a></td>
	</c:when>
	<c:when test="${event.attendees.contains(user)}">
	<td><p>Joining</p> | <a href="/unjoin/${event.id}">Cancel</a></td>
	</c:when>
	<c:otherwise>
	<td><a href="/join/${event.id}">Join</a></td>
	</c:otherwise>	
	</c:choose>
	</tr>
	</c:forEach>
	</tbody>
</table>

<h3>Create an Event</h3>
<form:form action="/events/new" method="post" modelAttribute="event">
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

<button class="btn btn-primary">Plan Event!</button>
</form:form>

</div>
</body>
</html>