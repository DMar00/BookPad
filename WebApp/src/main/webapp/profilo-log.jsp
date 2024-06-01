<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>BookPad-${user_logged.username}</title>
    <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
    <!---------------------Navbar----------------->
    <jsp:include page="./jspComponents/navbar-logged.jsp" />
    <!---------------------Page----------------->
    <div class="bp-single-page">
        <div class="container-fluid">
            <div class="container-page">
                <!--row1-->
                <div class="row">
                    <!--left box-->
                    <div class="col-lg-7 col-md-12 mt-2">
                        <div class="box d-flex flex-row h-100">
                            <!--AVATAR-->
                            <div class="left-side-user-info">
                                <c:choose>
                                    <c:when test="${user_logged.avatar!=null}">
                                        <img class="avatar-l" src="${pageContext.request.contextPath}/Image?type=avatar&username=${user_logged.username}" alt="avatar">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="avatar-l" src="./assets/img/no-image-avatar.png" alt="cover">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <!--INFO 1-->
                            <div>
                                <span class="username">${user_logged.username}</span>
                                <div class="about">
                                    <span class="title">About:</span>
                                    <c:set var="user" scope="session" value='${user_logged}'/>
                                    <c:choose>
                                        <c:when test="${user.about != null}">
                                            <span id="bio">${user_logged.about}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span id="bio">Nessuna biografia.</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="mt-3">
                                    <button class="btn-1" id="settings">
                                        <i class="bi bi-gear-fill"></i>
                                        Impostazioni
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--right box-->
                    <div class="col-lg-5 col-md-12 mt-2">
                        <div class="box d-flex flex-column h-100">
                            <div class="info-user">
                                <div>
                                    <div id="click-pub-stories">
                                        <i class="bi bi-book-fill"></i>
                                        <span class="num">${user_logged.published_stories.size()}</span>
                                        <span class="desc">Storie</span>
                                    </div>
                                    <div id="click-saved-stories">
                                        <i class="bi bi-bookmarks-fill"></i>
                                        <span class="num">${user_logged.saved_stories.size()}</span>
                                        <span class="desc">Salvate</span>
                                    </div>
                                    <div id="click-followings">
                                        <i class="bi bi-person-fill"></i>
                                        <span class="num">${user_logged.followings.size()}</span>
                                        <span class="desc">Followings</span>
                                    </div>
                                    <div id="click-followers">
                                        <i class="bi bi-person-fill"></i>
                                        <span class="num">${user_logged.followers.size()}</span>
                                        <span class="desc">Followers</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--row2-->
                <div class="row">
                    <div class="col-12">
                        <div class="page-box mt-3" id="content">
                           <!--contents-->
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
    <script src="./js/profile.js"></script>
</body>
</html>
