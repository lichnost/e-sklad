<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.stablylab.webui.server.util.InitUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>web-Склад</title>
</head>
<body>
	<%
	String requestURL = request.getRequestURL().toString();
    String absolute = requestURL.substring(0,
    		requestURL.length()-request.getRequestURI().length());

	if (InitUtil.isNewServerNeedingSetup()) 
		response.sendRedirect(absolute + "/init/");
	else
		response.sendRedirect(absolute + "/portal/index.html");
		%>
</body>
</html>