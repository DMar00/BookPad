<%@ page contentType="text/html;charset=UTF-8" language="java" import="bean.*" %>
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
      <div class="page-box">
        <c:set var = "stories" scope="request" value='${stories}'/>
        <c:choose>
          <c:when test="${stories.size() == 0}">
            <h4 class="no-result">Ancora nessuna storia da visualizzare</h4>
          </c:when>
          <c:otherwise>
            <h4 style="text-align: center; margin-bottom: 20px">Le storie degli utenti che segui ...</h4>
            <div class="row" id="container-stories">
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
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
  <!---------------------Footer----------------->
  <jsp:include page="./jspComponents/footer.jsp" />
  <!---------------------Script----------------->
  <script src="./js/switch.js"></script>
</body>
</html>
