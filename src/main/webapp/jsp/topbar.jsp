<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/fontello.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/topbarStyle.css"/>" type="text/css">

    <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>

    <div id="fb-root"></div>
    <script>
        window.fbAsyncInit = function () {
            FB.init({
                appId: '760505277482869',
                xfbml: true,
                version: 'v2.10'
            });
            FB.AppEvents.logPageView();
        };

        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {
                return;
            }
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.10&appId=760505277482869";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>
</head>

<body>
<header id="headertop">
    <div id="topbar">
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <li class="active"><a href="#footer">About</a></li>
                <li><a href="/articles">Articles</a></li>
                <li>
                    <img src="<c:url value="/resources/images/java.png"/>" id="javaphoto"/>
                </li>
            </ul>
        </nav>
    </div>

    <div id="toptopbar">
        <sec:authorize access="isAnonymous()">

            <a href="/login"><input type="button" value="Login"></a><br/>


            <a href="/register"><input type="button" value="Sign up"></a>

            <form:form action="/signin/facebook" method="POST">
                <input type="hidden" name="scope" value="public_profile"/>
                <input type="submit" value="Login by Facebook"/>
            </form:form>
            <%-- <div class="fb-login-button" data-max-rows="1" data-size="medium" data-button-type="continue_with"
                  data-show-faces="false" data-auto-logout-link="false" data-use-continue-as="false" style="margin-top: 10px"></div>--%>

        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            <a href="<c:url value="/profile"/>">
                <img src="${mainUser.imgUrl}" class="profileImg"/>
                <p class="userName">
                        ${mainUser.username}
                </p>
                <div style="clear: both;"/>
            </a>

            <p class="madeArticles">Made ${articleCount} articles</p>

            <a href="/messages">

            <c:choose>
                    <c:when test="${newMsg > 0}">
                        <i class="demo-icon icon-mail-alt" style="color: #ab0d0d; display: inline;"></i>
                    </c:when>
                    <c:otherwise>
                        <i class="demo-icon icon-mail-alt"></i>
                    </c:otherwise>
            </c:choose>
            </a>

            <section id="logout">
                <form:form method="post" action="/logout" cssStyle="display: inline;">
                    <input type="submit" style="margin-left: 0;" value="Logout">
                </form:form>
            </section>


        </sec:authorize>

    </div>
    <div style="clear: both;"/>
</header>
</body>
</html>
