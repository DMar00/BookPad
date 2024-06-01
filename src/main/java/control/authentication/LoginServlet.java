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

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged");
        if(userLogged!=null)
            response.sendRedirect(request.getContextPath() + "/Home");
        else
            this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        UserDAO user_dao = new UserDAO(ds);
        String email = request.getParameter("email"); //name in input email
        String psw = request.getParameter("psw");	//name in input password

        try {
            User user = user_dao.login(email,psw);
            if(user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user_logged", user);
                response.sendRedirect(request.getContextPath() + "/Home");
            }else{
                String message = "Errore credenziali";
                request.setAttribute("error", message);
                this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
