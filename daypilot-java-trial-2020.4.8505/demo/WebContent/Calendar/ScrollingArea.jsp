<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Scrolling Area (Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />


Height:
<select onchange="changeHeightSpec(this);" id="height">
	<option value="BusinessHours">Business hours</option>
	<option value="Fixed">Fixed (200 pixels)</option>
	<option value="BusinessHoursNoScroll">Business hours (no scrolling)</option>
	<option selected="selected" value="Full">Full (no scrolling)</option>
</select>

<script type="text/javascript">
function changeHeightSpec(src) {
	dpc.commandCallBack('height', { value: src.value });
}
</script>

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
/*
dpn.year = 2010;
dpn.month = 5;
*/
dpn.selectMode = 'week';
dpn.cssClassPrefix = "navigator_silver_";
dpn.weekStarts = 0;
dpn.init();

var dpc = new DayPilot.Calendar("dpc");
dpc.cssOnly = false;
dpc.backendUrl = '../dpc';
dpc.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpc.heightSpec = 'BusinessHours';
dpc.height = 200;
dpc.shadow = 'Fill';
dpc.timeRangeSelectedHandling = 'CallBack';
dpc.eventMoveHandling = 'CallBack';
dpc.eventResizeHandling = 'CallBack';
dpc.bubble = new DayPilot.Bubble();
dpc.showToolTip = false;
dpc.initScrollPos = 9 * 40; 
dpc.days = 7;
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