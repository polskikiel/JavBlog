<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Messages</title>
    <meta name="description"
          content="Login page."/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link href="<c:url value="/resources/images/favicon.ico"/>" rel="icon" type="image/x-icon"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/messageStyle.css"/>" type="text/css">

    <script src="<c:url value="/resources/js/JSTextToHtml.js"/>"></script>


    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<jsp:include page="topbar.jsp"/>
<article id="messages">

    <section id="lastMessages">
        <header class="messagesLabel">
            last messages
        </header>
        <hr>
        <br/>
        <c:choose>
            <c:when test="${fn:length(contacts) > 0}">
                <c:forEach items="${contacts}" var="contact" varStatus="i">
                    <div class="contact">
                        <a href="/messages?id=${contact.id}">
                            <img class="contactImg" src="${contact.imgUrl}">
                            <br/>
                            <p class="contactName">${contact.username}</p><br/><br/>
                        </a>
                        <p class="messageShort">

                            <c:choose>
                                <c:when test="${lastMessage[contact.username].messageOpened == false}">
                                    <c:choose>
                                        <c:when test="${lastMessage[contact.username].author.username != pageContext.request.userPrincipal.name}">
                                            <b>New: ${lastMessage[contact.username].title}</b>
                                        </c:when>
                                        <c:otherwise>
                                            You: ${lastMessage[contact.username].title}
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${lastMessage[contact.username].author.username != pageContext.request.userPrincipal.name}">
                                            They: ${lastMessage[contact.username].title}
                                        </c:when>
                                        <c:otherwise>
                                            You: ${lastMessage[contact.username].title}
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <br/>
                    <hr/>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="noMsg">
                    You have no messages
                </p>
            </c:otherwise>
        </c:choose>
        <br/>
        <section id="buttons">
        <a href="/messages">
            <p class="searchButton">
                Search
            </p>
        </a>
        <a href="/message?id=1">
            <p class="wantContact">
                Contact with site
            </p>
        </a>
        </section>

    </section>
    <section id="sentMessage">
        <header class="messagesLabel">
            <c:choose>
                <c:when test="${param['id'] != null}">
                    <a href="/profile?id=${param['id']}">
                        <img class="contactImg" src="${addressee.imgUrl}"/> ${addressee.username}
                    </a>
                    <hr style="margin-left: 32%"/>
                </c:when>
                <c:otherwise>
                    <header id="searchTitle">Find the user</header>
                    <hr style="margin-left: 23%"/>
                </c:otherwise>
            </c:choose>

        </header>
        <br/>
        <c:choose>
            <c:when test="${param['id'] != null}">
                <c:choose>
                    <c:when test="${searchUser == null}">
                        <c:forEach items="${messagesWith}" var="message">
                            <c:choose>
                                <c:when test="${message.author.username == pageContext.request.userPrincipal.name}">
                                    <section class="msg1">
                                        <b>${message.title}</b>
                                        <br/>
                                        <script>htmlText("${message.body}");</script>
                                    </section>
                                </c:when>
                                <c:otherwise>
                                    <section class="msg2">
                                        <b>${message.title}</b>
                                        <br/>
                                        <script>htmlText("${message.body}");</script>
                                    </section>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <br/>
                        <c:if test="${param['id'] != 2}">
                            <form:form method="post" modelAttribute="message" action="/messages?id=${param['id']}">
                                <form:input path="title" placeholder="Title"/><br/>
                                <form:input path="message" placeholder="Message"></form:input><br/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="submit" class="submit" value="Send">
                            </form:form>
                        </c:if>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>

                <section id="search">
                    <section class="searchSection">
                        <form:form method="post" modelAttribute="message" action="/messages">
                            <form:input path="search" placeholder="Login"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <br/>
                            <input type="submit" class="submit" value="Search">
                        </form:form>
                    </section>
                    <br/>
                    <c:forEach items="${searchUser}" var="user">
                        <section class="searchUsers">
                            <a href="/messages?id=${user.id}">
                                <div class="contact">
                                    <img class="contactImg" src="${user.imgUrl}">
                                    <p class="contactName">${user.username}</p>
                                    <div style="clear: both;"/>
                                </div>
                            </a>
                        </section>
                        <br/>
                    </c:forEach>
                </section>
            </c:otherwise>
        </c:choose>
        ${searchError}


    </section>
    <div style="clear: both;"></div>
</article>

<jsp:include page="sidebar.jsp"/>

</body>
</html>