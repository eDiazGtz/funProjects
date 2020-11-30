<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Navigator (Scheduler)</demo:title>
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
				<div id="dps_navigator"></div>
            </td>
		</tr>
</table>


<div style="clear:left"></div>

<script type="text/javascript">

var dpn = new DayPilot.Navigator("dpn");
dpn.bound = "dps";
dpn.selectMode = 'week';
dpn.weekStarts = 1;
dpn.showWeekNumbers = true;
dpn.showMonths = 3;
dpn.skipMonths = 3;
dpn.init();

var dps = new DayPilot.Scheduler("dps_navigator");
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
dps.cellGroupBy = "Week";
dps.cellWidth = 80;
dps.cellDuration = 1440;
dps.startDate = new DayPilot.Date().firstDayOfWeek(0);  // Sunday = 0
dps.days = 14;
dps.weekStarts = 1;
dps.init();
</script>

<demo:footer />

</body>
</html>