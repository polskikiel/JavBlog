<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>
    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>

<main>
    <article id="containerOp">
        <header class="headerLabel">
            ${article.header}
        </header>
        <br>
        <hr/>
        <br/>
        <p class="articleBody">
            ${article.body}
        </p>
        <br/>

        <jsp:include page="articleLabel.jsp"/>

        <section id="lastPosts">
            <header class="headerLabel">
                Ostatnie wpisy na blogu
            </header>
            <br/>
            <hr/>
            <br/>

            <c:forEach items="${newestArticles}" var="article" varStatus="index">

                <a href="/article/${article.id}">
                    <div class="post">
                        <img src="${article.imgUrl}" class="articleImg"/>
                        <section class="articleDesc">
                                ${article.header}
                            <br/>
                            <section class="imgComments">
                                <img src="https://d30y9cdsu7xlg0.cloudfront.net/png/7467-200.png"/>
                                    ${article.visitCounter}
                                <img src="https://d30y9cdsu7xlg0.cloudfront.net/png/33080-200.png"/>
                                    ${fn:length(article.comments)}
                            </section>
                            <p class="authorLabel">
                                    ${fn:split(article.date, ' ')[0]}<br/>
                                    ${fn:substring(fn:split(article.date, ' ')[1],  0, fn:length(fn:split(article.date, ' ')[1]) - 5)}
                            </p>
                            <div style="clear: both;"/>
                        </section>
                    </div>
                </a>

                <c:if test="${(index.count % 2) == 0 && index.index != 0}">
                    <div style="clear: both;"/>
                </c:if>

            </c:forEach>
            <div style="clear: both;"/>
    </article>

</main>
<jsp:include page="sidebar.jsp"/>
<div style="clear: both;"/>

<jsp:include page="footer.jsp"/>

</body>
</html>