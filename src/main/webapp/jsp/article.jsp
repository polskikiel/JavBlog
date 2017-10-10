<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Java Blog</title>
    <meta name="description"
          content="Java learning spring articles tutorials."/>
    <meta name="keywords"
          content="Nauka Java, Java, Spring, Hibernate, learning, Kurs, Blog, Michal, Kempski, Poczatki, programmer"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/articleStyle.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>

    <script src="<c:url value="/resources/js/JSTextToHtml.js"/>"></script>
    <script src="<c:url value="/resources/js/JSTextEdit.js"/>"></script>

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>
<article id="containerOp">
    <c:choose>
        <c:when test="${editMode == true}">
            <form:form method="post" action="/article/${article.id}?edit" modelAttribute="articleToEdit">
                <c:forEach items="${cat}" var="cat" varStatus="index">
                    <section class="category">
                        <form:checkbox path="category" value="${cat}"/>
                            ${cat}
                        <c:if test="${(index.index % 2) == 0}">
                            <br/>
                        </c:if>
                    </section>
                </c:forEach>
                <form:input path="imgUrl" value="${article.imgUrl}"/>
                <form:input path="title" value="${article.header}"/>
                <script>articleTextReturn('${article.body}', ${pageNr})</script>
                <input type="submit" value="Edit">
            </form:form>
        </c:when>
        <c:otherwise>
            <section id="article">
                <header class="articleHeader">
                    <script>htmlText("${article.header}");</script>
                </header>
                <section class="tagsContainer">
                    <c:forEach items="${article.category}" var="category">
                        <p class="tags">
                            <a href="/articles?sort=0&search=&tag=${category}">${category}</a>
                        </p>
                    </c:forEach>
                </section>
                <br/>
                <section id="articleBody">
                    <script>articleText('${article.body}', ${pageNr});</script>
                </section>
                <jsp:include page="articleLabel.jsp"/>
                <c:if test="${fn:length(article.body) > 3000}">
                    <section class="pageNr">
                        <c:forEach begin="0" end="${fn:length(article.body)/3000}" varStatus="index">
                            <a href="/article/${article.id}?page=${index.index}#article">${index.count}</a>
                        </c:forEach>
                    </section>
                </c:if>
            </section>
        </c:otherwise>
    </c:choose>
</article>
<jsp:include page="sidebar.jsp"/>
<div style="clear: both;"/>
<jsp:include page="footerWithComments.jsp"/>


</body>
</html>
