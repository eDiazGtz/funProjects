<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Navigator (Monthly Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<table style="width:100%">
        <tr>
            <td valign="top" style="width:150px">
				<div id="dpn"></div>
            </td>
            <td valign="top">
				<div id="dpm"></div>
            </td>
		</tr>
</table>



<script type="text/javascript">

var dpn = new DayPilot.Navigator("dpn");
dpn.bound = "dpm";
dpn.selectMode = 'month';
dpn.cssOnly = true;
dpn.cssClassPrefix = "navigator_white";
dpn.weekStarts = 0;
dpn.showMonths = 3;
dpn.skipMonths = 3;
dpn.init();

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
dpm.init();
</script>

<demo:footer />

</body>
</html>