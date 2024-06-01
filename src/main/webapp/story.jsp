<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
    <head>
        <title>BookPad-${story_found.title}</title>
        <jsp:include page="./jspComponents/head-tag.jsp" />
    </head>
    <body>
    <!---------------------Navbar----------------->
    <jsp:include page="./jspComponents/navbar-logged.jsp" />
    <!---------------------Page----------------->
    <div class="bp-single-page">
        <div class="container-fluid">
            <div class="container-page">
                <div class="box">
                    <h4 class="title-st">${story_found.title}</h4>
                    <div class="row">
                        <div class="col-lg-4 d-flex align-items-center justify-content-center">
                            <c:choose>
                                <c:when test="${story_found.cover!=null}">
                                    <img class="story-big-cover" src="${pageContext.request.contextPath}/Image?type=cover&id=${story_found.id}" alt="cover">
                                </c:when>
                                <c:otherwise>
                                    <img class="story-big-cover" src="./assets/img/no-image-cover.png" alt="cover">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-lg-8">
                            <h6>by @<a href="${pageContext.request.contextPath}/Profile?username=${story_found.author.username}" class="a-tag-story">${story_found.author.username}</a></h6>
                            <div class="genre-type">
                                <h6>Genere: </h6>
                                <a class="a-tag-story" href="${pageContext.request.contextPath}/Genres?genre_name=${story_found.genre.name}" style="margin-bottom: 8px">${story_found.genre.name}</a>
                            </div>
                            <div class="big-plot mt-2">${story_found.plot}</div>
                            <h6 class="mt-2">Tags</h6>
                            <div class="big-tags mb-2">
                                <c:set var="tags_list" scope="request" value='${story_found.tags}'/>
                                <c:choose>
                                    <c:when test="${tags_list.size()=='0'}">
                                        <span style="color: var(--bp-violet)">Nessun tag</span>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${tags_list}" var="tag">
                                            <span class="tag"><a href="${pageContext.request.contextPath}/Search?type=tag&tag_name=${tag}">${tag}</a></span>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="story-info-b mt-3">
                                <!--save-->
                                <div>
                                    <c:set var="isSave" scope="request" value='${isSave}'/>
                                    <c:choose>
                                        <c:when test="${isSave=='false'}">
                                            <i id="click-save" class="bi bi-bookmark"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i id="click-save" class="bi bi-bookmark-fill"></i>
                                        </c:otherwise>
                                    </c:choose>
                                    <span id="n_savings">${story_found.n_savings}</span>
                                </div>
                                <!--like-->
                                <div>
                                    <c:set var="isLike" scope="request" value='${isLike}'/>
                                    <c:choose>
                                        <c:when test="${isLike=='false'}">
                                            <i id="click-like" class="bi bi-suit-heart"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i id="click-like" class="bi bi-suit-heart-fill"></i>
                                        </c:otherwise>
                                    </c:choose>
                                    <span id="n_likes">${story_found.n_likes}</span>
                                </div>
                                <!--comments-->
                                <div>
                                    <i class="bi bi-chat-fill"></i>
                                    <span id="n_com">${story_found.n_comments}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-----------Second Box------------->
                <div class="box mt-4">
                    <c:set var="chapters" scope="request" value='${story_found.chapters}'/>
                    <div style="width: 100%; display: flex; justify-content: center; margin-bottom: 20px">
                        <h5 id="chap_title"></h5>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="nav nav-tabs" id="list-chaps">
                                <c:forEach items="${chapters}" var="chapter">
                                    <li class="nav-item">
                                        <a class="nav-link chap-found" id="c${chapter.number}">Capitolo ${chapter.number}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="col-lg-12">
                            <div id="chapter-content"></div>
                        </div>
                    </div>
                </div>
                <!-----------Comments------------->
                <div class="box mt-4">
                    <div class="row">
                        <div class="col-lg-5">
                            <form id="add-cm">
                                <textarea id="txt-commment" placeholder="Inserisci commento..."></textarea>
                                <span id="count-ch-com"><span>0</span>/500</span>
                                <a class="a-btn" id="add-comment">Commenta</a>
                            </form>
                        </div>
                        <div class="col-lg-7">
                            <div id="list-comments">
                                <c:set var="comments" scope="request" value='${story_found.comments}'/>
                                <c:choose>
                                    <c:when test="${comments.size()=='0'}">
                                        <span id="no_comments">Nessun Commento</span>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${comments}" var="comment">
                                            <div class="comment">
                                                <div class="info1">
                                                    <c:choose>
                                                        <c:when test="${comment.user.avatar!=null}">
                                                            <img src="${pageContext.request.contextPath}/Image?type=avatar&username=${comment.user.username}" alt="avatar">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="./assets/img/no-image-avatar.png" alt="cover">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a href="${pageContext.request.contextPath}/Profile?username=${comment.user.username}" class="username">${comment.user.username}</a>
                                                </div>
                                                <span class="cont">${comment.text}</span>
                                                <span class="date">${comment.date}</span>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!---------------------Footer----------------->
    <jsp:include page="./jspComponents/footer.jsp" />
    <!---------------------Script----------------->
    <script src="./js/switch.js"></script>
    <script src="js/story.js"></script>
    </body>
</html>
