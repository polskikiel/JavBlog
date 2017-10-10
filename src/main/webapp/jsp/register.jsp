<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Java Blog</title>
    <meta name="description"
          content="Jak zacząć przygodę z programowaniem w języku JAVA? W tym blogu przedstawiam swoją drogę na szczyt."/>
    <meta name="keywords"
          content="Nauka Java, Java, Spring, Hibernate, Nauka, Kurs, Blog, Michal, Kempski, Poczatki, programisty"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/registerStyle.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon" />

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>

<main>
    <article id="containerOp">
        <section id="joinUs">
            <div class="textCircle">
                Join us!
            </div>
            <br/>
            <c:forEach items="${img}" var="img">
                    <img src="${img}" class="smallImg"/>
            </c:forEach>
            <div style="clear: both;"></div>
            <br/>
            <hr id="underImg"/>
            <br/>
        </section>
        <section id="form">
        <form:form action="/register" modelAttribute="user" method="post">
            <div>
                <form:input path="login" id="login" placeholder="Login"/>
                ${faliure['login']}
                ${faliure['userExist']}
            </div>

            <div>
                <form:password path="password" id="password" placeholder="Password"/>
                ${faliure['pass']}
            </div>

            <div>
                <form:input path="email" id="email" placeholder="Email"/>
                ${faliure['email']}
                ${faliure['mailExist']}
            </div>

            <div>
                <form:input path="email2" id="email2" placeholder="Email again" autocomplete="false"/>
                ${faliure['emailCompare']}
            </div>

                <form:select path="day" id="day">
                    <c:forEach var="day" begin="1" end="31">
                        <form:option cssClass="option" value="${day}">${day}</form:option>
                    </c:forEach>
                </form:select>
                <form:select path="month" id="month">
                    <c:forEach items="${months}" var="item" varStatus="i">
                        <form:option cssClass="option" value="${i.count+1}">${item}</form:option>
                    </c:forEach>
                </form:select>
                <form:select path="year" id="year">
                    <c:forEach var="year" begin="1930" end="${thisyear}" varStatus="state">
                        <form:option cssClass="option" value="${state.end - state.count}">${state.end - state.count}</form:option>
                    </c:forEach>
                </form:select>
                ${age}

            <br/>
            <br/>
            <h3>Optional</h3>

            <div>
                <form:input path="imgUrl" id="imgUrl" placeholder="Profile photo url"/></div>

            <div><label>
                <form:input path="desc" id="desc" placeholder="Your profile description" autocomplete="false"/></label></div>

            <div><input type="submit" value="Sign In"/></div>
        </form:form>
        </section>
    </article>

</main>
<jsp:include page="sidebar.jsp"/>
<div style="clear: both;"/>

<jsp:include page="footer.jsp"/>

</body>
</html>