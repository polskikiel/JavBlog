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

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/loginStyle.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon" />

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>

<div id="site">
    <main>
        <article id="containerOp">
            <section class="logininput">
                <c:set var="loginUrl"><c:url value="/login"/></c:set>
                <form method="post" action="${loginUrl}">
                    <p id="loginpagelabel">Login page</p>
                    <input type="text" name="username" placeholder="Login"/><br/>
                    <input type="password" name="password" placeholder="Password"/><br/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="checkbox" name="remember-me"/> <label class="remember">Remember me</label><br/>
                    <p id="noAcc">Don't have acc? <a href="/register" style="color: #195385">Sign up</a></p>
                    <input type="submit" value="Login"/>
                </form>
                <br/>
                <br/>
            </section>


            <div id="faliuresbox">${error}</div>
            <div style="clear: both;"/>
        </article>
    </main>

    <jsp:include page="sidebar.jsp"/>
</div>


</body>
</html>