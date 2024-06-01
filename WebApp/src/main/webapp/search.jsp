<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>BookPad-Ricerca</title>
    <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
    <!---------------------Navbar----------------->
    <c:set var="user_logged" scope="session" value='${user_logged}'/>
    <c:choose>
        <c:when test="${user_logged != null}">
            <jsp:include page="./jspComponents/navbar-logged.jsp" />
        </c:when>
        <c:otherwise>
            <jsp:include page="./jspComponents/navbar.jsp" />
        </c:otherwise>
    </c:choose>
    <!---------------------Pagina----------------->
    <div class="bp-single-page">
        <div class="container-fluid">
            <div class="page-box-fit">
                <div>
                    <h3 class="title" style="margin-bottom: 20px">Risultati per "<span style="color: var(--bp-orange)">${word}</span>"</h3>
                    <div class="row">
                        <div class="col-12">
                            <span>Ricerca per: </span>
                        </div>
                        <div class="col-12 menu-search">
                            <c:if test="${type=='title'}">
                                <a id="sort-title" class="a-btn search-bt active" href="${pageContext.request.contextPath}/Search?type=title&title_name=${word}"><i class="bi bi-filter-left"></i> Titolo</a>
                                <a id="sort-tag" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=tag&tag_name=${word}"><i class="bi bi-hash"></i> Tag</a>
                                <a id="sort-user" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=user&username=${word}"><i class="bi bi-person-fill"></i> Utente</a>
                            </c:if>
                            <c:if test="${type=='tag'}">
                                <a id="sort-title" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=title&title_name=${word}"><i class="bi bi-filter-left"></i> Titolo</a>
                                <a id="sort-tag" class="a-btn search-bt active" href="${pageContext.request.contextPath}/Search?type=tag&tag_name=${word}"><i class="bi bi-hash"></i> Tag</a>
                                <a id="sort-user" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=user&username=${word}"><i class="bi bi-person-fill"></i> Utente</a>
                            </c:if>
                            <c:if test="${type=='user'}">
                                <a id="sort-title" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=title&title_name=${word}"><i class="bi bi-filter-left"></i> Titolo</a>
                                <a id="sort-tag" class="a-btn search-bt" href="${pageContext.request.contextPath}/Search?type=tag&tag_name=${word}"><i class="bi bi-hash"></i> Tag</a>
                                <a id="sort-user" class="a-btn search-bt active" href="${pageContext.request.contextPath}/Search?type=user&username=${word}"><i class="bi bi-person-fill"></i> Utente</a>
                            </c:if>
                        </div>
                    </div>
                    <div class="row mt-2" id="container-stories">
                        <c:choose>
                            <c:when test="${results.size()==0}">
                                <h4 style="text-align: center">Nessun risultato!</h4>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${content=='story'}">
                                        <c:set var="stories" scope="request" value='${results}'/>
                                        <c:forEach items="${stories}" var="story">
                                            <div class="col-lg-6 col-md-6 d-flex justify-content-center story-cont">
                                                <div class="story-card">
                                                    <c:choose>
                                                        <c:when test="${story.cover!=null}">
                                                            <img src="${pageContext.request.contextPath}/Image?type=cover&id=${story.id}" alt="cover">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="./assets/img/no-image-cover.png" alt="cover">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="txt">
                                                        <span class="title">${story.title}</span>
                                                        <a class="username" href="${pageContext.request.contextPath}/Profile?username=${story.author.username}">${story.author.username}</a>
                                                        <div class="info">
                                                            <div>
                                                                <i class="bi bi-suit-heart-fill"></i>
                                                                <span>${story.n_likes}</span>
                                                            </div>
                                                            <div>
                                                                <i class="bi bi-chat-fill"></i>
                                                                <span>${story.n_comments}</span></div>
                                                            <div>
                                                                <i class="bi bi-bookmark-fill"></i>
                                                                <span>${story.n_savings}</span>
                                                            </div>
                                                        </div>
                                                        <span class="plot">${story.plot}</span>
                                                        <div class="read-row">
                                                            <a href="${pageContext.request.contextPath}/Stories?id=${story.id}">Leggi</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>

                                        <c:set var="users" scope="request" value='${results}'/>
                                        <c:forEach items="${users}" var="user">
                                            <div class="col-lg-3 col-md-4 d-flex justify-content-center story-cont">
                                                <div class="user-card">
                                                    <c:choose>
                                                        <c:when test="${user.avatar!=null}">
                                                            <img src="${pageContext.request.contextPath}/Image?type=avatar&username=${user.username}" alt="avatar">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="./assets/img/no-image-avatar.png" alt="avatar">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <span class="usnm">${user.username}</span>
                                                    <c:choose>
                                                        <c:when test="${user.about!=null}">
                                                            <span>${user.about}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span>Nessuna biografia</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a class="a-btn" href="${pageContext.request.contextPath}/Profile?username=${user.username}">Profilo</a>
                                                </div>
                                            </div>
                                        </c:forEach>



                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!---------------------Footer----------------->
    <jsp:include page="./jspComponents/footer.jsp" />
    <!---------------------Script----------------->
    <script src="./js/switch.js"></script>
</body>
</html>
