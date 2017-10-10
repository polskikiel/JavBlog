<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/sidebarStyle.css"/>" type="text/css">
</head>
<section id="sidebar">

    <c:forEach items="${sidebarArticles}" var="article">
            <a href="/article/${article.id}">
                <section id="articleTitle">
                    <p class="imgLabel">
                            ${article.header}
                        <br/>
                    </p>
                    <p class="articleLabel">
                            ${body[article.id]}..
                    </p>
                </section>
            </a>
    </c:forEach>
</section>
