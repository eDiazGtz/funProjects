<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Event Links (Java Scheduler)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note"><b>Note:</b> You can create a theme using the online <strong>DayPilot Theme Designer</strong>: <a href="http://themes.daypilot.org/">http://themes.daypilot.org/</a></div>

<div id="dps_links"></div>

<script type="text/javascript">
var dps = new DayPilot.Scheduler("dps_links");
dps.backendUrl = '${pageContext.request.contextPath}/dps';
dps.onTimeRangeSelected = function(args) { createEvent(args.start, args.end, args.resource); };
dps.eventMoveHandling = 'CallBack';
dps.eventResizeHandling = 'CallBack';
dps.onEventClick = function(args) { editEvent(args.e); };
dps.scale = "Day";
dps.timeHeaders = [
	{ groupBy: "Month" },
	{ groupBy: "Day"}
];
dps.cellDuration = 1440;

dps.startDate = "2011-01-01";
dps.days = 365;
dps.treeEnabled = true;
dps.bubble = new DayPilot.Bubble();
dps.resourceBubble = new DayPilot.Bubble();
dps.contextMenu = new DayPilot.Menu({
	items: [
		{text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
		{text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
		{text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
		{text:"Go to google.com", href: "http://www.google.com/?q={0}"},
		{text:"CallBack: Delete this event", command: "delete"} 
	]});

dps.init();
</script>


<script type="text/javascript">

function createEvent(start, end, resource) {
    var modal = new DayPilot.Modal();
    modal.top = 60;
       modal.width = 300;
       modal.opacity = 0;
       modal.border = "1px solid #d0d0d0";
       modal.closed = function() { 
           if(this.result == "OK") { 
               dps.commandCallBack('refresh'); 
           }
           dps.clearSelection();
       };

       modal.height = 280;
    modal.showUrl("${pageContext.request.contextPath}/new?start=" + start.toStringSortable() + "&end=" + end.toStringSortable() + "&res=" + resource);
}

function editEvent(e) {
    var modal = new DayPilot.Modal();
    modal.top = 60;
    modal.width = 300;
    modal.opacity = 0;
    modal.border = "1px solid #d0d0d0";
    modal.closed = function() { 
   		if(this.result == "OK") { 
        	dps.commandCallBack('refresh'); 
        }
        dps.clearSelection();
    };
    modal.height = 280;
    modal.showUrl("${pageContext.request.contextPath}/edit?id=" + e.id());
}


</script>
<demo:footer />

</body>
</html>