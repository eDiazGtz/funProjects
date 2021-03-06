<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<demo:title>DayPilot Scheduler Demo</demo:title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<demo:css />
</head>
<body style="margin:10px;">

<h1>Event Details</h1>
<form method="post" id="f" action="${pageContext.request.contextPath}/edit">


<div>Name</div>
<div><input type="text" id="new_name" name="new_name" value="${name}"></input></div>

<div>Start</div>
<div><input type="text" name="new_start" value="${start}"></input></div>

<div>End</div>
<div><input type="text" name="new_end" value="${end}"></input></div>

<div>Resource</div>
<div><input type="text" name="new_resource" value="${resource}"></input></div>

<input type="submit" value="Save" /> <a href="javascript:close();">Cancel</a>

</form>


 <script type="text/javascript">
        function close(result) {
        	parent && parent.DayPilot && parent.DayPilot.ModalStatic && parent.DayPilot.ModalStatic.close && parent.DayPilot.ModalStatic.close(result);
        }
        
        $("#f").submit(function() {
                var f = $("#f");
                $.post(f.action, f.serialize(), function(result) {
                    close(eval(result));
                });
                return false;
            });
            
        $(document).ready(function() {
            $("#new_name").focus();
        });
    
    </script>
    
</body>
</html>