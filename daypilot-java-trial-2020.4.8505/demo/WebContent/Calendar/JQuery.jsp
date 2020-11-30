<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>AJAX Event Calendar jQuery Plugin</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note">
	<b>Note:</b> The Calendar can be initialized using a jQuery plugin.
</div>

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

$(document).ready(function() {
	var dpn = $("#dpn").daypilotNavigator({
		bound : "dpc",
		selectMode : 'week',
		cssOnly : true,
		cssClassPrefix : "navigator_green",
		weekStarts : 0,
		showWeekNumbers : true,
		showMonths : 3,
		skipMonths : 3
	});
	
	var dpc = $("#dpc").daypilotCalendar({
		backendUrl : '../dpc',
		cssOnly : true,
		cssClassPrefix : "calendar_green",
		viewType : "Week",
		heightSpec : 'BusinessHours',
		height : 200,
		timeRangeSelectedHandling : 'CallBack',
		eventMoveHandling : 'CallBack',
		eventResizeHandling : 'CallBack',
		eventDeleteHandling : 'CallBack',
		eventClickHandling : 'Edit',
		eventEditHandling : 'CallBack',
		eventArrangement : "Full",
		showAllDayEvents : true,
		bubble : new DayPilot.Bubble(),
		showToolTip : false,
		initScrollPos : 9 * 40 + 1, 
		eventRightClickHandling : "ContextMenu",
		contextMenu : new DayPilot.Menu({
			items: [
				{text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
				{text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
				{text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
				{text:"Go to google.com", href: "http://www.google.com/?q={0}"},
				{text:"CallBack: Delete this event", command: "delete"} 
		]})
	});
});

</script>

<h2>Source</h2>

<pre>&lt;script type="text/javascript"&gt;

  $(document).ready(function() {
    var dpn = $("#dpn").daypilotNavigator({
      bound : "dpc",
      selectMode : 'week',
      cssOnly : true,
      cssClassPrefix : "navigator_green",
      weekStarts : 0,
      showWeekNumbers : true,
      showMonths : 3,
      skipMonths : 3
    });

    var dpc = $("#dpc").daypilotCalendar({
      backendUrl : '../dpc',
      cssOnly : true,
      cssClassPrefix : "calendar_green",
      viewType : "Week",
      heightSpec : 'BusinessHours',
      height : 200,
      timeRangeSelectedHandling : 'CallBack',
      eventMoveHandling : 'CallBack',
      eventResizeHandling : 'CallBack',
      eventDeleteHandling : 'CallBack',
      eventClickHandling : 'Edit',
      eventEditHandling : 'CallBack',
      eventArrangement : "Full",
      showAllDayEvents : true,
      bubble : new DayPilot.Bubble(),
      showToolTip : false,
      initScrollPos : 9 * 40 + 1, 
      moveBy : "Top",
      eventRightClickHandling : "ContextMenu",
      contextMenu : new DayPilot.Menu([
        {text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
        {text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
        {text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
        {text:"Go to google.com", href: "http://www.google.com/?q={0}"},
        {text:"CallBack: Delete this event", command: "delete"} 
      ])
    });
  });

&lt;/script&gt;
</pre>


<demo:footer />

</body>
</html>