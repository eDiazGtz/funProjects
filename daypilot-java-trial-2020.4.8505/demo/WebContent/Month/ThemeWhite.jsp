<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>White Theme (Monthly Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />
<div id="dpm">
</div>

<script type="text/javascript">
var dpm = new DayPilot.Month("dpm");
dpm.cssOnly = false;
dpm.locale = 'en-us';
dpm.backendUrl = "${pageContext.request.contextPath}/dpm";
dpm.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpm.timeRangeSelectedHandling = 'CallBack';
dpm.eventMoveHandling = 'CallBack';
dpm.eventResizeHandling = 'CallBack';
dpm.eventStartTime = false;
dpm.eventEndTime = false;
dpm.cssOnly = true;
dpm.cssClassPrefix = 'month_white';
dpm.eventHeight = 25;
dpm.init();
</script>

<demo:footer />

</body>
</html>