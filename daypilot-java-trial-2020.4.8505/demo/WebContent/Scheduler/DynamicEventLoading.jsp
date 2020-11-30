<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Dynamic Event Loading (Scheduler)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />
<div id="dps">
</div>


<script type="text/javascript">
var dps = new DayPilot.Scheduler("dps");
dps.backendUrl = '${pageContext.request.contextPath}/dps';
dps.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dps.timeRangeSelectedHandling = 'CallBack';
dps.cellGroupBy = "Month";
dps.cellDuration = 1440;
dps.startDate = "2010-01-01";
dps.days = 365;
dps.dynamicLoading = true;
dps.init();
</script>

<demo:footer />

</body>
</html>