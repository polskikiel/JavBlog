<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/articleLabelStyle.css"/>" type="text/css">

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
</head>
<body>
<br/>
<div class="containerOp">
    <section class="labelContainer">
        <p class="label">
            <a href="/profile?id=${article.user.id}">
                ${article.user.username}<br/>
                ${fn:split(article.date, ' ')[0]}<br/>
                ${fn:substring(fn:split(article.date, ' ')[1],  0, fn:length(fn:split(article.date, ' ')[1]) - 5)}
            </a>
        </p>
        <section class="imgContainer">
            <a href="/profile?id=${article.user.id}">
                <img src="${article.user.imgUrl}" class="imgAuthor"/>
            </a>
        </section>
        <div style="clear: both;"/>
    </section>
</div>


</body>
</html>
