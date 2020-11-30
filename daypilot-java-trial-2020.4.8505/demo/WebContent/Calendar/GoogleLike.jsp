<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>Google-Like Theme (Calendar)</demo:title>
	<demo:scripts />
	<demo:css />
	<demo:head />
</head>
<body>

<demo:header />
<div id="dpcg">
</div>

<script type="text/javascript">
var dpc = new DayPilot.Calendar("dpcg");
dpc.cssOnly = false;
dpc.backendUrl = '../dpc';
dpc.ajaxError = function(req) { new DayPilot.Modal().showHtml(req.responseText); };

dpc.cellBackColor = 'White';
dpc.cellBackColorNonBusiness = 'White';
dpc.cellBorderColor = '#DEDFDE';
dpc.cornerBackColor = '#F3F3F9';
dpc.eventBackColor = '#638EDE';
dpc.eventBorderColor = '#2951A5';
dpc.eventFontFamily = 'Tahoma, Verdana, sans-serif';
dpc.eventFontSize = '8pt';
dpc.eventFontColor = 'White';
dpc.eventSelectColor = 'Blue';
dpc.headerFontSize = '10pt';
dpc.headerFontFamily = 'Tahoma, Verdana, sans-serif';
dpc.headerFontColor = '#42658C';
dpc.hourHalfBorderColor = '#EBEDEB';
dpc.hourBorderColor = '#DEDFDE';
dpc.hourFontColor = '#42658C';
dpc.hourFontFamily = 'Tahoma, Verdana, sans-serif';
dpc.hourFontSize = '16pt';
dpc.hourNameBackColor = '#F3F3F9';
dpc.hourNameBorderColor = '#DEDFDE';
dpc.allDayEventBorderColor = '#638EDE';
dpc.allDayEventFontFamily = 'Tahoma, Verdana, sans-serif';
dpc.allDayEventFontSize = '8pt';
dpc.allDayEventFontColor = 'White';
dpc.allDayEventHeight = 16;
dpc.hourWidth = 45;

dpc.viewType = 'Week';
dpc.durationBarVisible = false;
dpc.roundedCorners = true;
dpc.eventArrangement = 'Cascade';
dpc.eventHeaderVisible = true;

dpc.initScrollPos = 9 * 40 + 1; 
dpc.heightSpec = 'BusinessHours';
dpc.height = 200;
dpc.shadow = 'Fill';
dpc.timeRangeSelectedHandling = 'CallBack';
dpc.eventMoveHandling = 'CallBack';
dpc.eventResizeHandling = 'CallBack';

dpc.init();
</script>

<demo:footer />

</body>
</html>