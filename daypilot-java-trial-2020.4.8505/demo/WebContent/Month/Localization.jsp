<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Localization (Monthly Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />

<div class="space">
<select onchange="changeLocale(this)">
	<option value="zh-CN">Chinese (zh-CN)</option>
	<option value="cs-CZ">Czech (cs-CZ)</option>
	<option value="nl-NL">Dutch (nl-NL)</option>
	<option selected="selected" value="en-US">English (en-US)</option>
	<option value="fr-FR">French (fr-FR)</option>
	<option value="de-DE">German (de-DE)</option>
	<option value="ja-JP">Japanese (ja-JP)</option>
	<option value="ru-RU">Russian (ru-RU)</option>
	<option value="es-ES">Spanish (es-ES)</option>
</select>
</div>

<script type="text/javascript">
function changeLocale(el) {
	dpm.commandCallBack("locale", { "value" : el.value });
}
</script>

	<div id="dpm"></div>


<script type="text/javascript">
var dpm = new DayPilot.Month("dpm");
dpm.cssOnly = false;
dpm.locale = "en-us";
dpm.backendUrl = "${pageContext.request.contextPath}/dpm";
dpm.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };
dpm.timeRangeSelectedHandling = 'CallBack';
dpm.eventMoveHandling = 'CallBack';
dpm.eventResizeHandling = 'CallBack';
dpm.eventStartTime = false;
dpm.eventEndTime = false;
dpm.cssClassPrefix = 'month_silver_';
dpm.eventStartTime = true;
dpm.eventEndTime = true;
dpm.eventHeight = 16;
dpm.init();
</script>

<demo:footer />

</body>
</html>