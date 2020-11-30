<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Outlook 2000 Theme (Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>


<demo:header />
<div id="dpc">
</div>

<script type="text/javascript">
var dpc = new DayPilot.Calendar("dpc");
dpc.cssOnly = false;
dpc.backendUrl = '../dpc';
dpc.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpc.heightSpec = 'BusinessHours';
dpc.height = 200;
dpc.shadow = 'Fill';
dpc.initScrollPos = 9 * 40 + 1; 
dpc.timeRangeSelectedHandling = 'CallBack';
dpc.eventMoveHandling = 'CallBack';
dpc.eventResizeHandling = 'CallBack';
dpc.mac = false;
dpc.init();
</script>

<demo:footer />

</body>
</html>