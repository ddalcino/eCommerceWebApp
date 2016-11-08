<%-- 
    Document   : header.jsp
    Created on : Feb 2, 2016, 7:13:20 PM
    Author     : dave

    To be used in the head of an HTML document.
    Specifies stylesheets and javascript include libraries

--%>
<%-- 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
--%>


        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

        <title>Webpage</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="stylesheet" href="<c:url value="/resources/include/styles/bootstrap.min.css" />" type="text/css"/>
        <link rel="stylesheet" href="<c:url value="/resources/include/styles/main.css" />" type="text/css"/>
        <link rel="stylesheet" href="<c:url value="/resources/include/styles/sticky-footer-navbar.css" />" type="text/css"/>
        <script src="<c:url value="/resources/include/js/jquery-1.12.0.js" />"></script>
        <script src="<c:url value="/resources/include/js/bootstrap.min.js" />"></script>
        
