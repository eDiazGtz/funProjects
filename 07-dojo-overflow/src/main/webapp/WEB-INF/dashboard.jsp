<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ask Us Anything</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
</head>
<body>
<div class="container">
<hr>
<h1>Questions For Us</h1>

<hr>
<a href="/questions/new">Ask Us Anything</a>
<hr>


<table class="table table-dark table-hover">
<thead>
<tr>
<td>Question</td>
<td>Tags</td>
</tr>
</thead>
<tbody>
<c:forEach items="${ question }" var="question">
<tr>
<td><a href="/questions/${question.id}">${ question.question }</a></td>

<td>
<c:forEach items="${ question.tags }" var="tag">
${ tag.tag }<c:if test="${ question.tags.indexOf(tag) != question.tags.size() - 1 }">, </c:if>
</c:forEach>
</td>

</tr>
</c:forEach>
</tbody>
</table>

</div>
</body>
</html>