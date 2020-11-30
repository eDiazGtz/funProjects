<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>AJAX Monthly Calendar jQuery Plugin</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="note">
	<b>Note:</b> The Month can be initialized using a jQuery plugin.
</div>

<div id="dpm"></div>

<script type="text/javascript">
$(document).ready(function() {
	var dpm = $("#dpm").daypilotMonth({
		locale : 'en-us',
		backendUrl : "${pageContext.request.contextPath}/dpm",
		timeRangeSelectedHandling : 'CallBack',
		eventMoveHandling : 'CallBack',
		eventResizeHandling : 'CallBack',
		eventStartTime : false,
		eventEndTime : false,
		cssClassPrefix : 'month_green',
		eventHeight : 25
	});
});
</script>

<h2>Source</h2>

<pre>&lt;script type="text/javascript"&gt;
$(document).ready(function() {
  var dpm = $("#dpm").daypilotMonth({
    locale : 'en-us',
    backendUrl : "${pageContext.request.contextPath}/dpm",
    timeRangeSelectedHandling : 'CallBack',
    eventMoveHandling : 'CallBack',
    eventResizeHandling : 'CallBack',
    eventStartTime : false,
    eventEndTime : false,
    cssClassPrefix : 'month_green',
    eventHeight : 25
  });
});
&lt;/script&gt;
</pre>

<demo:footer />

</body>
</html>