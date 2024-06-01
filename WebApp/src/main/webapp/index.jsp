<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>BookPad</title>
    <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
    <!---------------------Navbar----------------->
    <jsp:include page="./jspComponents/navbar.jsp" />
    <!---------------------Header----------------->
    <div class="bp-header">
        <div class="container">
            <div>
                <h1><strong>B</strong>ook<strong>P</strong>ad</h1>
                <h4>La piattaforma social di narrativa che permette agli utenti di leggere ed esprimere la loro creatività</h4>
            </div>
        </div>
    </div>
    <!---------------------Section 1----------------->
    <div class="bp-section">
        <h4>Come funziona BookPad ...</h4>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-4 d-flex justify-content-center">
                    <div class="bp-card-1">
                        <img src="./assets/svg/reading.svg" alt="reading">
                        <div class="txt">
                            <b>Leggi</b>
                            <span>Trova centinaia di storie da leggere ed amare ovunque tu sia!</span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 d-flex justify-content-center">
                    <div class="bp-card-1">
                        <img src="./assets/svg/pen.svg" alt="pen">
                        <div class="txt">
                            <b>Scrivi</b>
                            <span>Mettiti in gioco e dai libero sfogo alla tua fantasia di scrittura!</span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 d-flex justify-content-center">
                    <div class="bp-card-1">
                        <img src="./assets/svg/people.svg" alt="people">
                        <div class="txt">
                            <b>Interagisci</b>
                            <span>Fai sapere agli autori delle tue storie preferite cosa ne pensi!</span>
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
</body>
</html>
