<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Month View (Scheduler)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />
<div id="dps">
</div>

<div style="clear:left"></div>


<script type="text/javascript">
var dps = new DayPilot.Scheduler("dps");
dps.resources = [
                 { 	name:"Room A",
                    id:"A" 
                 },
                 { 	name:"Room B",
                    id:"B" 
                 },
                 { 	name:"Room C",
                    id:"C" 
                 }
                ];
dps.backendUrl = '${pageContext.request.contextPath}/dps';
dps.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dps.timeRangeSelectedHandling = 'CallBack';
dps.timeHeaders = [
	{groupBy: "Month"},
	{groupBy: "Week"},
	{groupBy: "Day"}
];
dps.scale = "Day";
dps.startDate = new DayPilot.Date().firstDayOfMonth();
dps.days = dps.startDate.daysInMonth();
dps.init();
</script>

<demo:footer />

</body>
</html>