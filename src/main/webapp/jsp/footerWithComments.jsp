<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/footerStyle.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/commentStyle.css"/>" type="text/css"/>

</head>
<body>
<div style="clear: both;"></div>
<br/>
<footer id="footer">
    <c:forEach items="${comments}" var="comment">
        <section class="commentSection">
            <section class="comment">
                <p class="commentAuthor">
                    <a href="/profile?id=${comment.user.id}">
                            ${comment.user.username}
                    </a>
                </p>

                <p class="commentDate">
                        ${fn:split(comment.date, ' ')[0]}
                        ${fn:substring(fn:split(comment.date, ' ')[1],  0, fn:length(fn:split(comment.date, ' ')[1]) - 5)}
                </p>
                <p class="commentBody">
                        ${comment.body}
                </p>
            </section>
            <section class="answerSection">
                <c:forEach items="${comment.comments}" var="answer">
                    <section class="answerContainer">
                        <p class="commentAuthor">
                            <a href="/profile?id=${answer.user.id}">
                                    ${answer.user.username}
                            </a>
                        </p>
                        <p class="commentDate">
                                ${fn:split(answer.date, ' ')[0]}
                                ${fn:substring(fn:split(answer.date, ' ')[1],  0, fn:length(fn:split(answer.date, ' ')[1]) - 5)}
                        </p>
                        <p class="commentBody">
                                ${answer.body}
                        </p>
                    </section>
                </c:forEach>
            </section>
            <section class="answerFormContainer">
                <form:form method="post" action="/article/${article.id}#footer" modelAttribute="commentComment">
                    <form:input path="body" autocomplete="false"/>
                    <form:hidden path="commentId" value="${comment.id}"/>
                    <input type="submit" value="Answer">
                </form:form>
            </section>
        </section>
    </c:forEach>

    <section class="newCommentSection">
        <form:form method="post" action="/article/${article.id}#footer" modelAttribute="comment">
            <form:input path="text" autocomplete="false"/><br/>
            <input type="submit" value="Add comment">
        </form:form>
    </section>
</footer>
<br/>
<br/>

</body>
</html>