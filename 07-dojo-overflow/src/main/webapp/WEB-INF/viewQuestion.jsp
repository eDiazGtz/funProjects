<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>The Question</title>
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


<h1>${ question.question }</h1>

<h3>Tags:</h3>
</h3><c:forEach items="${ question.tags }" var="tag">
<h3>${ tag.tag }</h3>
</c:forEach>

<hr>

<table class="table table-dark table-hover">
<thead>
<td>Answers</td>
</thead>
<tbody>

<c:forEach items="${ question.answers }" var="answer">
<tr>
<td>${ answer.answer }</td>
</tr>
</c:forEach>

</tbody>
</table>

<hr>

<h3>We answer: </h3>

<form:form action="/questions/answers" method="POST" modelAttribute="ans">
<form:hidden path="question" value="${ question.id }"/>

<div class="form form-group">
<form:label path="answer"> Answer: 
<form:errors path="answer"/>
<form:textarea class="form-control" path="answer"></form:textarea>
</form:label>
</div>

<button class="btn btn-primary">Answer it!</button>
</form:form>

</div>
</body>
</html>