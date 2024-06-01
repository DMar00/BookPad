<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>BookPad-Error 404</title>
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
        <div class="error-page-img">
          <div class="error404"></div>
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
