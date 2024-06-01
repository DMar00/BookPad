package control.authentication;

import dao.UserDAO;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Reg", value = "/Reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged");
        if(userLogged!=null)
            response.sendRedirect(request.getContextPath() + "/Home");
        else
            this.getServletContext().getRequestDispatcher("/reg.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        UserDAO user_dao = new UserDAO(ds);

        String email = request.getParameter("email"); //name in input email
        String psw = request.getParameter("psw");	//name in input password
        String psw2 = request.getParameter("psw2");
        String username = request.getParameter("username");

        boolean usernameCheck = username.matches("^(?=.*[a-z])(?!.*(_)\\1)([a-z0-9_]+){4,15}$");
        boolean passwordCheck = psw.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
        boolean emailCheck = email.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$");

        if(usernameCheck && passwordCheck && emailCheck && (psw.equals(psw2))){
            try {
                boolean e = user_dao.checkEmail(email);
                if(e){
                    String errorEmail = "E-mail già registrata!";
                    request.setAttribute("errorEmail", errorEmail);
                }
                boolean u = user_dao.checkUsername(username);
                if(u){
                    String errorUser = "Username già utilizzato!";
                    request.setAttribute("errorUser", errorUser);
                }
                if(e || u) {
                    this.getServletContext().getRequestDispatcher("/reg.jsp").forward(request, response);
                }else{
                    User user = user_dao.register(email,psw,username);
                    if(user != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user_logged", user);
                        response.sendRedirect(request.getContextPath() + "/Home");
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
