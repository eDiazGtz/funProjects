<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Java Gantt Chart</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note"><b>Note:</b> Read more about <a href="http://java.daypilot.org/gantt/">Java Gantt Chart</a></div>

<div id="gantt"></div>

<script type="text/javascript">
var gantt = new DayPilot.Gantt("gantt");
gantt.backendUrl = '${pageContext.request.contextPath}/dpg';
gantt.rowMoveHandling = "Disabled";
gantt.columns = [
	{ title: "Task", property: "text", width: 100},
	{ title: "Duration", width: 100}
];
gantt.init();
</script>


<demo:footer />

</body>
</html>