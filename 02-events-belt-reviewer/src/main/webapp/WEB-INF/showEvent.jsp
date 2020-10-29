<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Event Page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>
<body>
<div class="container">


<hr>
<a href="/dashboard">Dashboard</a>
<hr>


<h1>${event.name}</h1>
<hr>
<div class="row">
<div class="col">
<p>Host: ${ hostName }</p>
<p>Date: ${ event.date }</p>
<p>Location: ${ event.location }</p>
<p>People who are attending this event: ${ event.attendees.size() }</p>


<table class="table table-dark table-hover">
<thead>
<td>Name</td>
<td>Location</td>
</thead>
<tbody>

<c:forEach items="${event.attendees}" var="user">
<tr>
<td>${user.firstName} ${user.lastName}</td>
<td>${user.location}</td>
</tr>
</c:forEach>


</tbody>
</table>

</div>

<div class="col">

<!-- MESSAGE WALL HERE -->
<h3>Message Wall</h3>
<table class="table table-striped table-bordered table-sm">
<tbody>
<c:forEach items="${event.messages}" var="message">
<tr>
<td>${message.content}</td>
</tr>
</c:forEach>
</tbody>
</table>

<h3>Add Comment: </h3>

<form:form action="/message/new" method="post" modelAttribute="message">
<form:hidden path="event" value="${ event.id }"/>

<div class="form form-group">
<form:errors path="content"/>
<form:textarea class="form-control" path="content"></form:textarea>
</div>

<button class="btn btn-danger">Submit</button>
</form:form>

</div>

</div>
</div>
</body>
</html>