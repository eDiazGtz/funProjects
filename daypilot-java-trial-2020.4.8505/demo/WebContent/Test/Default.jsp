<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<script src="../js/daypilot/daypilot-all.min.js"></script>

<div id="dps"></div>

<script>
var dps = new DayPilot.Scheduler("dps");
dps.backendUrl = "../Backend";
dps.startDate = "2015-03-01";
dps.scale = "Manual";
dps.timeHeaders = [
	{groupBy: "Day", format: "MMMM d, yyyy"},
	{ groupBy: "Hour", format: "h a"}
];
dps.init();

</script>

</body>
</html>