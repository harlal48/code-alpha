<%-- 
    Document   : logout
    Created on : Feb 11, 2025, 2:04:35?PM
    Author     : ACER
--%>

<%@ page session="true" %>
<%
    session.invalidate();
    response.sendRedirect("login.jsp"); 
%>

