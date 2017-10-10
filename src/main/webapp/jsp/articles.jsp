<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Articles</title>
    <meta name="description"
          content="Articles"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/articlesStyle.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
    <script>

    </script>

</head>
<body>
<jsp:include page="topbar.jsp"/>

<div id="site">
    <article id="containerOp">
        <div class="inputContainer">
            <section class="searchSection">
                <form:form method="post" action="/articles" modelAttribute="text">
                    <form:input path="text"/>
                    <br/>
                    <c:forEach items="${tags}" var="tag" varStatus="i">
                        <c:choose>
                            <c:when test="${param['tag'].contains(tag)}">
                                <form:checkbox path="category" value="${tag}" checked="s"/>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox path="category" value="${tag}" checked=""/>
                            </c:otherwise>
                        </c:choose>
                        ${tag}
                        <br/>
                    </c:forEach>
                    <input type="submit" value="Search"/>
                </form:form>
            </section>
            <section class="buttonsSection">
                <section class="buttonContainer">
                    <a href="/newarticle">
                        <div class="linkContainer">
                            Add article
                        </div>
                    </a>
                </section>
                <section class="buttonContainer">
                    <a href="/profile#yourArticles">
                        <div class="linkContainer">
                            Your articles
                        </div>
                    </a>
                </section>
            </section>
            <div style="clear: both;"/>
            <section class="lastSearch">
                <a href="${lastSearch}">
                    Last search
                </a>
            </section>

            <br/>

            <section class="sortSection">
                <form:form method="post" action="/articles" modelAttribute="sort">
                <form:select path="sort" id="sort">
                <c:forEach items="${sortOptions}" varStatus="index" var="option">
                    <form:option value="${index.count}">${option}</form:option>
                </c:forEach>
                </form:select>

                <input type="submit" value="Sort"/>
                </form:form>
        </div>

        <c:set var="articleslength" value="${fn:length(sortedArticles)}"/>
        <p class="sortInfo">
            <c:choose>
                <c:when test="${articleslength > 0}">
                    Found ${articleslength} articles<br/>
                    <c:if test="${fn:length(param['search']) > 0}">
                        search: ${param['search']}<br/>
                    </c:if>
                    <c:if test="${fn:length(paramValues['tag']) > 0 && paramValues['tag'][0] != ''}">
                        tags:
                        <c:forEach items="${paramValues['tag']}" var="tag">
                            ${tag}
                        </c:forEach>
                        <br/>
                    </c:if>
                    <c:if test="${param['sort'] != null && param['sort'] != 0}">
                        sort option: ${sortOptions[param['sort'] - 1]}
                    </c:if>

                </c:when>
                <c:otherwise>
                    There is no articles like that, try something else
                </c:otherwise>
            </c:choose>
        </p>
        <c:if test="${articleslength > 0}">
            <section class="articlesSection">
                <c:forEach items="${sortedArticles}" var="article" varStatus="index"
                           begin="${param['page']*10}" end="${param['page']*10+10}">
                    <a href="/article/${article.id}">
                        <section class="wholeArticleContainer">
                            <section class="articleSection">

                                    ${article.header}

                                <br/>
                                <img src="https://d30y9cdsu7xlg0.cloudfront.net/png/7467-200.png"/>
                                    ${article.visitCounter}
                                <img src="https://d30y9cdsu7xlg0.cloudfront.net/png/33080-200.png"/>
                                    ${fn:length(article.comments)}

                                <section class="tagsSec">
                                    <c:forEach items="${article.category}" var="tag" varStatus="index">
                                        ${tag}
                                        <c:if test="${!(index.last)}">
                                            -
                                        </c:if>
                                    </c:forEach>
                                </section>
                            </section>
                            <section class="articleAuthor">
                                    ${article.user.username}
                                <img src="${article.user.imgUrl}"/>
                                <br/>
                                <section class="dateSection">
                                        ${fn:split(article.date, ' ')[0]}
                                        ${fn:substring(fn:split(article.date, ' ')[1],  0, fn:length(fn:split(article.date, ' ')[1]) - 5)}
                                </section>
                            </section>
                            <div style="clear: both;"/>
                        </section>
                    </a>
                </c:forEach>
                <c:if test="${articleslength > 9}">
                    <section id="pageNr">
                        <c:forEach begin="0" end="${(articleslength-1) / 10}" varStatus="index">
                            <a href="${requestScope['javax.servlet.forward.request_uri']}?${queryString}&page=${index.index}">
                                    ${index.count}
                            </a>
                        </c:forEach>
                    </section>
                </c:if>
            </section>
        </c:if>
        <div style="clear: both;"/>

    </article>
    <jsp:include page="sidebar.jsp"/>
</div>


</body>
</html>
