<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Crosshair (Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note"><b>Note:</b> Two Crosshair modes are supported: "Header" (headers only) and "Full" (full crosshair).</div>

<table style="width:100%">
        <tr>
            <td valign="top" style="width:150px">
				<div id="dpn"></div>
            </td>
            <td valign="top">
				<div id="dpc"></div>
            </td>
		</tr>
</table>
            

<script type="text/javascript">


var dpn = new DayPilot.Navigator("dpn");
dpn.bound = "dpc";
dpn.selectMode = 'week';
dpn.cssOnly = true;
dpn.cssClassPrefix = "navigator_green";
dpn.weekStarts = 0;
dpn.showWeekNumbers = true;
dpn.showMonths = 3;
dpn.skipMonths = 3;
dpn.init();

var dpc = new DayPilot.Calendar("dpc");
dpc.backendUrl = '../dpc';
dpc.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpc.crosshairType = "Full";
dpc.cssOnly = true;
dpc.cssClassPrefix = "calendar_green";
dpc.viewType = "Week";
dpc.heightSpec = 'BusinessHours';
dpc.height = 200;
dpc.shadow = 'Fill';
dpc.timeRangeSelectedHandling = 'CallBack';
dpc.eventMoveHandling = 'CallBack';
dpc.eventResizeHandling = 'CallBack';
dpc.eventDeleteHandling = 'CallBack';
dpc.eventClickHandling = 'Edit';
dpc.eventEditHandling = 'CallBack';
dpc.eventArrangement = "Full";
dpc.showAllDayEvents = true;
dpc.bubble = new DayPilot.Bubble({ cssClassPrefix: "bubble_default" });
dpc.showToolTip = false;
dpc.initScrollPos = 9 * 40 + 1; 
dpc.eventRightClickHandling = "ContextMenu";
dpc.contextMenu = new DayPilot.Menu({


	items: [
		{text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
		{text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
		{text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
		{text:"Go to google.com", href: "http://www.google.com/?q={0}"},
		{text:"CallBack: Delete this event", command: "delete"} 
	]});
dpc.init();

</script>

<demo:footer />

</body>
</html>