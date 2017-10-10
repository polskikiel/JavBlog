<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Add article</title>
    <meta name="description"
          content="Add your own article about java."/>
    <meta name="keywords"
          content="Learning Java, Java, Spring, Hibernate, Nauka, Kurs, Blog, Michal, Kempski, Poczatki, programisty, IT"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/addArticleStyle.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>
    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>

<main>
    <article id="containerOp">
        <h1>New Article</h1>
        <hr/>
        <br/>

        <form:form action="/newarticle" modelAttribute="article" method="post">
        <section id="categories">
            <p>Select article categories</p>
            <c:forEach items="${cat}" var="cat" varStatus="index">
                <section class="category">
                    <form:checkbox path="category" value="${cat}"/>
                        ${cat}
                    <c:if test="${(index.index % 2) == 0}">
                        <br/>
                    </c:if>
                </section>
            </c:forEach>
        </section>

        <section id="formContainer">
            <form:input path="title" id="title" placeholder="Title"/>
                    ${faliure['TITLEEMPTY']}
                    ${faliure['TITLESHORT']}
                    ${faliure['TITLELONG']}
            <form:input path="body" id="body" placeholder="Article body"/>
                    ${faliure['BODYSHORT']}
                    ${faliure['BODYEMPTY']}
            <form:input path="imgUrl" placeholder="Article Image"/>
            <input type="submit" value="Add article"/>
            </form:form>
        </section>
    </article>

</main>
<jsp:include page="sidebar.jsp"/>
<div style="clear: both;"/>

<jsp:include page="footer.jsp"/>

</body>
</html>