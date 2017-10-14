<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <link rel="stylesheet" href="<c:url value="/resources/css/profileStyle.css"/>" type="text/css">
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>

    <script src="<c:url value="/resources/js/JSTextToHtml.js"/>"></script>
    <%--<-- MUST BE--%>

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
    <style>
        .post:hover {
            background-color: transparent;
        }

        .post:hover .articleDesc {
            font-weight: normal;
        }

    </style>
</head>
<body>
<jsp:include page="topbar.jsp"/>
<div id="site">
    <main>
        <article id="containerOp" style="text-align: center;">
            <c:if test="${context == null && someprofile == null}">
                <h2>${pageContext.request.userPrincipal.name}'s profile</h2>
                <hr/>
                <a href="/profile/edit/0">
                    <img src="<c:url value ="${profile.imgUrl}"/>" class="profilephoto"/>
                </a>
            </c:if>
            <c:choose>
                <c:when test="${context != null}">
                    <h2>${title}</h2>
                    <hr/>
                    <section id="editsection">
                        <c:choose>
                            <c:when test="${context == 2}">
                                <form:form method="post" action="/profile/edit/2" modelAttribute="pass">
                                    <form:password path="pass" placeholder="Your password"/><br/>
                                    <form:password path="pass1" placeholder="New password"/><br/>
                                    <form:password path="pass2" placeholder="Repeat"/><br/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="submit" value="Edit"/>
                                </form:form>
                            </c:when>
                            <c:otherwise>

                                <form:form method="post" action="/profile/edit/${context}" modelAttribute="text">
                                    <c:choose>
                                        <c:when test="${context == 6}">
                                            <form:password path="text" placeholder="${phlabel[context]}"/>
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}"/>
                                            <input type="submit" value="Delete"/>
                                        </c:when>

                                        <c:otherwise>
                                            <form:input path="text" placeholder="${phlabel[context]}"/>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="submit" value="Edit"/>
                                        </c:otherwise>
                                    </c:choose>
                                </form:form>
                            </c:otherwise>
                        </c:choose>
                            ${error}
                    </section>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${someprofile == null}">
                            <section id="profileinfo">

                                <div class="profileinfo2">
                                    <a href="/profile/edit/1">
                                        <c:if test="${profile.nick != null}">
                                            ${profile.nick}
                                        </c:if>
                                        <c:if test="${profile.nick == null}">
                                            Nick
                                        </c:if>
                                    </a>
                                </div>
                                <sec:authorize access="!hasRole('ROLE_FACEBOOK')">
                                    <div class="profileinfo2">
                                        <a href="/profile/edit/2">
                                            Password
                                        </a>
                                    </div>
                                </sec:authorize>


                                <div class="profileinfo2">
                                    <a href="/profile/edit/3">
                                            ${profile.mail}
                                    </a>
                                </div>


                                <div class="profileinfo2">
                                    <a href="/profile/edit/4">
                                        Description
                                    </a>
                                </div>

                                <div class="profileinfo2">
                                    <a href="/profile/edit/5">
                                        Messages
                                    </a>
                                </div>
                                <sec:authorize access="!hasRole('ROLE_FACEBOOK')">
                                <div class="profileinfo2" style="background-color: indianred; margin-top: 10px">
                                        <a href="/profile/edit/6">
                                            Delete account
                                        </a>
                                </div>
                                </sec:authorize>
                            </section>
                            <br/>
                            <hr/>
                            <section id="profileDesc">

                                <c:choose>
                                    <c:when test="${fn:length(profile.description) > 0}">
                                        <script>htmlText("${profile.description}");</script>
                                    </c:when>
                                    <c:otherwise>
                                        Description
                                    </c:otherwise>
                                </c:choose>

                            </section>
                            <section class="myArticles">
                                <h2>
                                    Your Articles
                                </h2>
                            </section>
                        </c:when>
                        <c:otherwise>
                            <h2>${someprofile.username}'s profile</h2>
                            <hr/>
                            <img src="<c:url value ="${someprofile.imgUrl}"/>" class="profilephoto"/>
                            <br/>
                            <div class="profileinfo2" style="margin-left: 30%;">
                                    ${someprofile.mail}
                            </div>
                            <div class="profileinfo2" style="margin-left: 30%;">
                                <a href="/message?id=${someprofile.id}">
                                    Send message
                                </a>
                            </div>

                            <section class="myArticles">
                                <h1>
                                        __Articles__
                                </h1>
                            </section>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
            <br/>

            <section class="yourArticles">
                <c:if test="${(fn:length(userArticle)) == 0}">
                    You don't have any articles yet
                </c:if>
                <c:forEach items="${userArticle}" var="article" varStatus="index"
                           begin="${param['page']*6}" end="${param['page']*6+5}">

                    <div class="post">
                        <a href="/article/${article.id}">
                            <img src="${article.imgUrl}" class="articleImg"/>
                        </a>

                        <section class="articleDesc">
                            <p class="header">
                                <a href="/article/${article.id}">${article.header}</a>
                            </p>
                            <c:if test="${someprofile == null}">
                            <p class="editPanel">
                                <a href="/article/${article.id}?edit">
                                    <img src="http://www.freeiconspng.com/uploads/edit-new-icon-22.png">
                                </a>
                                <a href="/article/${article.id}?delete">
                                    <img src="https://cdn4.iconfinder.com/data/icons/colicon/24/close_delete-128.png">
                                </a>
                            </p>
                            </c:if>
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


                    <c:if test="${(index.count % 2) == 0 && index.index != 0}">
                        <div style="clear: both;"/>
                    </c:if>
                </c:forEach>
                <div style="clear: both;"/>
                <br/>
            </section>
            <section id="pages">
                <c:if test="${fn:length(userArticle) > 5}">
                    <c:forEach end="${fn:length(userArticle) / 6}" begin="0" varStatus="index">
                        <c:choose>
                            <c:when test="${param['page'] == index.index}">
                                <a href="/profile?page=${index.index}">
                                    <p style="color: #ac2925; display: inline;">${index.count}</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="/profile?page=${index.index}">${index.count}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </section>

        </article>
    </main>
    <jsp:include page="sidebar.jsp"/>
</div>

</body>
</html>