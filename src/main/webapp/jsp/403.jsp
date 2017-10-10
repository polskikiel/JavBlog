<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login page</title>
    <meta name="description"
          content="Login page."/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Refresh" content="3;url=/">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">


    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>

<div id="site">
    <main>
        <article id="containerOp">
            <p class="errorMsg">
                ${errorMsg}
            </p>
        </article>
    </main>
    <jsp:include page="sidebar.jsp"/>
</div>


</body>
</html>