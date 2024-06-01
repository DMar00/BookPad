<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>BookPad-Scrivi</title>
  <jsp:include page="./jspComponents/head-tag.jsp" />
</head>
<body>
  <!---------------------Navbar----------------->
  <jsp:include page="./jspComponents/navbar-logged.jsp" />
  <!---------------------Page----------------->
  <div class="bp-single-page">
    <div class="container-fluid">
      <div class="container-page">
        <form method="post" action="${pageContext.request.contextPath}/Write" enctype='multipart/form-data'>
          <!--Info storia-->
          <div class="box">
            <h4 class="title-st">Dati storia</h4>
            <hr>
            <div class="row">
              <!--Cover-->
              <div class="col-lg-4 cover-section">
                <img src="./assets/img/no-image-cover.png" id="cover-preview" alt="cover-preview">
                <label for="cover">Cover</label>
                <input name="cover" id="cover" type="file" accept="image/*">
                <span class="mex-err img-err">Errore formato! Formati file accettati : jpg, jpeg, png, bmp</span>
              </div>
              <!--info-->
              <div class="col-lg-8 info-section">
                <!--Titolo-->
                <label for="titolo">Titolo*</label>
                <input type="text" name="titolo" id="titolo">
                <span class="count tit"><span>0</span>/60</span>
                <!--Trama-->
                <label for="trama">Trama*</label>
                <textarea name="trama" id="trama"></textarea>
                <span class="count tr"><span>0</span>/30000</span>
                <!--Trama-->
                <label for="tags">Tags</label>
                <div id="tagSection">
                  <input type="text" name="tags" id="tags" placeholder="Separa tags con uno spazio">
                  <div></div>
                </div>
                <label for="genere">Genere*</label>
                <select name="genere" id="genere" class="mb-3">
                  <c:set var="genres_list" scope="application" value='${genres_list}'/>
                  <c:forEach items="${genres_list}" var="genre">
                    <option value='${genre.name}'>${genre.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <!--Capitoli-->
          <div class="box mt-2">
            <h4 class="title-st">Capitoli</h4>
            <hr>
            <div class="chaps">
              <div class="capitolo">
                <div>
                  <span class="c">
                    <i class="bi bi-filter-left"></i>
                    Capitolo (Obbligatorio)
                  </span>
                </div>
                <label>Titolo*</label>
                <input type="text" name="chap_title" class="chap_title">
                <span class="count ch_tit"><span>0</span>/60</span>
                <label>Testo*</label>
                <textarea name="chap_text" class="chap_text"></textarea>
                <span class="count ch_txt"><span>0</span>/60000</span>
              </div>
            </div>
            <button class="btn-1 addChap" type="button">
              <i class="bi bi-plus-circle"></i>
              Aggiungi capitolo
            </button>
          </div>
          <!--Save-->
          <div class="box mt-2 save-section">
            <div class="row d-flex justify-content-center">
              <button class="btn-1" type="submit" id="pb_story">
                <i class="bi bi-arrow-up"></i>
                Pubblica storia!
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!---------------------Footer----------------->
  <jsp:include page="./jspComponents/footer.jsp" />
  <!---------------------Script----------------->
  <script src="./js/switch.js"></script>
  <script src="js/writestory.js"></script>
</body>
</html>
