<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>BookPad-Login</title>
    <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
    <!---------------------Navbar----------------->
    <jsp:include page="./jspComponents/navbar.jsp" />
    <!---------------------Page----------------->
    <div class="bp-single-page">
        <div class="container d-flex justify-content-center">
            <div class="form-box">
                <h3>Login</h3>
                <hr>
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/Login">
                        <label for="email">E-mail</label>
                        <input type="email" id="email" name="email">
                        <label for="paswd">Password</label>
                        <input type="password" id="paswd" name="psw">
                        <c:set var = "error" scope="request" value='${error}'/>
                        <c:if test = "${error != null}">
                            <div class="error-mex" >
                                <i class="bi bi-exclamation-circle-fill" ></i >
                                <span>${error}</span>
                            </div >
                        </c:if>
                        <div>
                            <button class="btn-1" type="submit">Accedi</button>
                        </div>
                    </form>
                    <span>Non hai un account? <a href="${pageContext.request.contextPath}/Reg">Registrati</a></span>
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

