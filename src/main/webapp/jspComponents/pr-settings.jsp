<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="sett-section">
    <div id="header">
        <div>
            <i class="bi bi-gear-fill"></i>
            <span>Impostazioni</span>
        </div>
    </div>
    <div id="menu">
        <div id="change-avatar">
            <i class="bi bi-person-circle"></i>
            <span>Modifica avatar</span>
        </div>
        <div id="change-bio">
            <i class="bi bi-text-paragraph"></i>
            <span>Modifica biografia</span>
        </div>
        <div id="change-email">
            <i class="bi bi-envelope-fill"></i>
            <span>Modifica E-mail</span>
        </div>
        <div id="change-pass">
            <i class="bi bi-lock-fill"></i>
            <span>Modifica Password</span>
        </div>
    </div>
    <!--avatar form-->
    <div id="form-avatar" class="settings-form">
        <form>
            <input type="file" id="new-avatar">
            <a id="save-avatar" class="a-btn"><i class="bi bi-upload"></i> Salva</a>
        </form>
    </div>
    <!--bio form-->
    <div id="form-bio" class="settings-form">
        <form>
            <div>
                <textarea id="new_bio" placeholder="Nuova biografia..."></textarea>
                <span class="count bio-count"><span>0</span>/500</span>
            </div>
            <a id="save-bio" class="a-btn"><i class="bi bi-upload"></i> Salva</a>
        </form>
    </div>
    <!--email form-->
    <div id="form-email" class="settings-form">
        <span>Email attuale: <span style="color: var(--bp-orange)">${user_logged.email}</span></span>
        <hr>
        <form>
            <div>
                <input type="email" id="new_email" placeholder="Nuova Email...">
                <span class="mex-err em">Formato email non valido!</span>
            </div>
            <a id="save-email" class="a-btn"><i class="bi bi-upload"></i> Salva</a>
        </form>
    </div>
    <!--password form-->
    <!--todo change password-->
    <div id="form-pas" class="settings-form">
        <form>
            <div>
                <input type="password" id="new_pass" placeholder="Nuova Password...">
                <input type="password" id="new_pass2" placeholder="Ripeti Password...">
            </div>
            <a id="save-pass" class="a-btn"><i class="bi bi-upload"></i> Salva</a>
        </form>
    </div>
</div>

