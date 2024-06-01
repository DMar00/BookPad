<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>BookPad-Registrazione</title>
    <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
    <!---------------------Navbar----------------->
    <jsp:include page="./jspComponents/navbar.jsp" />
    <!---------------------Page----------------->
    <div class="bp-single-page">
        <div class="container d-flex justify-content-center">
            <div class="form-box reg">
                <h3>Registrazione</h3>
                <hr>
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/Reg">
                        <label for="username">Username *</label>
                        <input type="text" id="username" name="username">
                        <span class="mex-err us">Formato username non valido!<br>Lunghezza 4-15<br>Almeno una lettera minuscola, nessun uderscore consecutivo, nessuna lettera maiuscola<br>Cifre consentite</span>
                        <c:set var = "errorUser" scope="request" value='${errorUser}'/>
                        <c:if test = "${errorUser != null}">
                            <div class="error-mex" >
                                <i class="bi bi-exclamation-circle-fill" ></i >
                                <span>${errorUser}</span>
                            </div >
                        </c:if>
                        <label for="email">E-mail *</label>
                        <input type="email" id="email" name="email">
                        <span class="mex-err em">Formato email non valido!</span>
                        <c:set var = "errorEmail" scope="request" value='${errorEmail}'/>
                        <c:if test = "${errorEmail != null}">
                            <div class="error-mex" >
                                <i class="bi bi-exclamation-circle-fill" ></i >
                                <span>${errorEmail}</span>
                            </div >
                        </c:if>
                        <label for="paswd">Password *</label>
                        <input type="password" id="paswd" name="psw">
                        <span class="mex-err pa">Formato password non valido!<br>Almeno una lettera minuscola, una maiuscola, una cifra<br>Lunghezza minima 8 caratteri</span>
                        <input type="password" id="paswd2" name="psw2" placeholder="Ripeti password">
                        <span class="mex-err pa2">Le password non coincidono!</span>
                        <div>
                            <button class="btn-1" type="submit" id="sub">Registrati</button>
                        </div>
                    </form>
                    <span>Hai già un account? <a href="${pageContext.request.contextPath}/Login">Accedi</a></span>
                </div>
            </div>
        </div>
    </div>
    <!---------------------Footer----------------->
    <jsp:include page="./jspComponents/footer.jsp" />
    <!---------------------Script----------------->
    <script src="./js/switch.js"></script>
    <script src="./js/check.js"></script>
</body>
</html>

