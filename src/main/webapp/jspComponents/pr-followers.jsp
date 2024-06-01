<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="followers" scope="request" value='${user.followers}'/>
<div id="followings-list">
    <h4 style="text-align:center;margin-bottom:20px;">Followers</h4>
    <div class="row" id="container-stories">
        <c:choose>
            <c:when test="${followers.size()==0}">
                <h4 class="no-result">Nessun utente ti segue!</h4>
            </c:when>
            <c:otherwise>
                <c:forEach items="${followers}" var="user">
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
    </div>
</div>
