<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>AJAX Scheduler jQuery Plugin</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note">
	<b>Note:</b> The Scheduler can be initialized using a jQuery plugin.
</div>

<div id="dps"></div>

<script type="text/javascript">
$(document).ready(function() {
	$("#dps").daypilotScheduler({
		backendUrl : '${pageContext.request.contextPath}/dps',
		timeRangeSelectedHandling : 'JavaScript',
		onTimeRangeSelected : function(args) { createEvent(args.start, args.end, args.resource); },
		eventMoveHandling : 'CallBack',
		eventResizeHandling : 'CallBack',
		eventRightClickHandling : 'ContextMenu',
		onEventClick : function(args) { editEvent(args.e); },
		cellGroupBy : "Month",
		cellDuration : 1440,
		cellWidth : 60,
		startDate : "2011-01-01",
		days : 365,
		treeEnabled : true,
		bubble : new DayPilot.Bubble(),
		resourceBubble : new DayPilot.Bubble(),
		contextMenu : new DayPilot.Menu({
			items: [
					{text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
					{text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
					{text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
					{text:"Go to google.com", href: "http://www.google.com/?q={0}"},
					{text:"CallBack: Delete this event", command: "delete"} 
				]
		})
	});
});
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
    modal.showUrl("${pageContext.request.contextPath}/edit?id=" + e.value());
}
</script>

<h2>Source</h2>

<pre>&lt;script type="text/javascript"&gt;
  $(document).ready(function() {
    $("#dps").daypilotScheduler({
      backendUrl : '${pageContext.request.contextPath}/dps',
      onTimeRangeSelected : function(args) { createEvent(args.start, args.end, args.resource); },
      eventMoveHandling : 'CallBack',
      eventResizeHandling : 'CallBack',
      onEventClick : function(args) { editEvent(args.e); },
      cellGroupBy : "Month",
      cellDuration : 1440,
      cellWidth : 60,
      startDate : "2011-01-01",
      days : 365,
      treeEnabled : true,
      bubble : new DayPilot.Bubble(),
      resourceBubble : new DayPilot.Bubble(),
      contextMenu : new DayPilot.Menu({
      items: [
        {text:"Show event ID", onclick: function() {alert("Event value: " + this.source.value());} },
        {text:"Show event text", onclick: function() {alert("Event text: " + this.source.text());} },
        {text:"Show event start", onclick: function() {alert("Event start: " + this.source.start().toStringSortable());} },
        {text:"Go to google.com", href: "http://www.google.com/?q={0}"},
        {text:"CallBack: Delete this event", command: "delete"} 
        ]
      })
    });
  });
&lt;/script&gt;</pre>


<demo:footer />

</body>
</html>