<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Date Switching (Calendar)</demo:title>
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

					
                    <div style="margin-bottom: 5px">
                    
                    Go to:
                    
                    <a href="javascript:dpc.commandCallBack('previous');" style="margin-right:2px"
                    >Previous</a>
                    
                    <a href="javascript:dpc.commandCallBack('next');"
                    >Next</a>
                    
                    <a href="javascript:dpc.commandCallBack('today');"
                    >Today</a>
                    
                    Switch view: <a href="javascript:dpc.commandCallBack('day');">Day view</a> <a href="javascript:dpc.commandCallBack('week');">Week view</a>
                    </div>


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
dpn.weekStarts = 0;
dpn.init();

var dpc = new DayPilot.Calendar("dpc");
dpc.cssOnly = false;
dpc.backendUrl = '../dpc';
dpc.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };

dpc.cssOnly = true;
dpc.cssClassPrefix = "calendar_white";
dpc.borderColor = "#a0a0a0";

dpc.viewType = "Week";
dpc.heightSpec = 'BusinessHours';
dpc.height = 200;
dpc.shadow = 'Fill';
dpc.timeRangeSelectedHandling = 'CallBack';
dpc.eventMoveHandling = 'CallBack';
dpc.eventResizeHandling = 'CallBack';
dpc.bubble = new DayPilot.Bubble();
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