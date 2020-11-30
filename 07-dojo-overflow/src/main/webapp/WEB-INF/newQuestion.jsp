<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>What will you ask Us?</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
</head>
<body>

<div class="container">

<hr>
<a href="/questions">Dashboard</a>
<hr>


<h1>What is your question?</h1>

<c:forEach items="${ errors }" var="err">
${ err }
</c:forEach>

<form:form action="/questions/new" method="POST" modelAttribute="questions">

<div class="form form-group">
<form:label path="question"> Question: 
<form:errors path="question"/>
<form:textarea path="question"/>
</form:label>
</div>

<label>Tags:</label>
<input type="text" name="tag">

<button class="btn btn-primary">Ask</button>
</form:form>

</div>
</body>
</html>