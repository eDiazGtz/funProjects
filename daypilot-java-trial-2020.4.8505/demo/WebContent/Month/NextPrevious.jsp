<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Next, Previous Navigation (Monthly Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

    <div id="toolbar" style="width:100%">
        <a href="javascript:dpm.commandCallBack('previous');">&#x25c4;</a>
        <a href="javascript:dpm.commandCallBack('next');">&#x25ba;</a>
        <a href="javascript:dpm.commandCallBack('this');">This Month</a>
    </div>

	<div id="dpm"></div>


<script type="text/javascript">
var dpm = new DayPilot.Month("dpm");
dpm.cssOnly = false;
dpm.locale = "en-us";
dpm.backendUrl = "${pageContext.request.contextPath}/dpm";
dpm.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpm.timeRangeSelectedHandling = 'CallBack';
dpm.eventMoveHandling = 'CallBack';
dpm.eventResizeHandling = 'CallBack';
dpm.eventStartTime = false;
dpm.eventEndTime = false;
dpm.cssClassPrefix = 'month_silver_';
dpm.showWeekend = false;
dpm.eventHeight = 16;
dpm.init();
</script>

<demo:footer />

</body>
</html>