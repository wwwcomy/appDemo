<%@page import="org.springframework.security.core.Authentication"%>
<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<h2>Hello World!</h2>
	<p>
		<a href="login.jsp"> Login page</a>
	</p>
	<p>
		<a href="secure/index.jsp">User page</a>
	</p>
	<p>
		<a href="secure/extreme/index.jsp">Super user page</a>
	</p>
	<p>
		<a href="j_spring_security_logout">Logout</a>
	</p>
	<p>The current user is</p>
	<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if(auth == null)
		out.print("null");
	else
		out.print(auth.getName());%>
</body>
</html>
