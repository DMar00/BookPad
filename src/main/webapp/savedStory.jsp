<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
  <head>
    <title>BookPad-Storia salvata con successo</title>
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
  <!---------------------Page----------------->
  <div class="bp-single-page">
    <div class="container-fluid d-flex justify-content-center">
      <div class="form-box">
        <div style="display: flex; width: 100%; align-items: center; margin-bottom: 20px">
          <i style="color:var(--bp-orange); font-size: 70px" class="bi bi-check-circle-fill"></i>
        </div>
        <h4>La tua storia "<span style="color: var(--bp-violet)">${story.title}</span>" è stata salvata con successo</h4>
        <div style="display: flex; width: 100%; align-items: center; margin-top: 50px">
          <a class="a-btn" href="${pageContext.request.contextPath}/Stories?id=${story.id}">Visualizza Storia</a>
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
