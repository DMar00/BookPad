package integrazione;

import com.mysql.cj.jdbc.MysqlDataSource;
import control.authentication.LoginServlet;
import control.authentication.RegServlet;
import dao.UserDAO;
import bean.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class TestLogin extends RegServlet{
    private static MysqlDataSource mds;
    private UserDAO userDao;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private LoginServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser( "admin" );
        mds.setPassword( "adminPass" );

        RequestDispatcher rd = mock(RequestDispatcher.class);
        doNothing().when(rd).forward(isA(HttpServletRequest.class), isA(HttpServletResponse.class));

        ServletContext ctx = mock(ServletContext.class);
        when(ctx.getAttribute("dataSource")).thenReturn(mds);
        when(ctx.getRequestDispatcher(isA(String.class))).thenReturn(rd);
        servlet= new LoginServlet(){
            @Override
            public ServletContext getServletContext() {
                return ctx;
            }
        };

        userDao = new UserDAO(mds);
    }

    @Test
    public void TC_LOG_1() throws ServletException, IOException, SQLException {
        User u = userDao.register("anna@libero.it","Giovannino!","giovy");

        request.setParameter("email", "giovan@libero.it");
        request.setParameter("psw", "Giovannino!");
;
        servlet.doPost(request, response);

        assertEquals("Errore credenziali",request.getAttribute("error"));
        removeUser(u.getUsername());
    }

    @Test
    public void TC_LOG_2() throws ServletException, IOException, SQLException {
        User u = userDao.register("anna@libero.it","Giovannino!","giovy");

        request.setParameter("email", "anna@libero.it");
        request.setParameter("psw", "Giovannino");

        servlet.doPost(request, response);

        assertEquals("Errore credenziali",request.getAttribute("error"));
        removeUser(u.getUsername());
    }

    @Test
    public void TC_LOG_3() throws ServletException, IOException, SQLException {
        User u = userDao.register("anna@libero.it","Giovannino!","giovy");

        request.setParameter("email", "anna@libero.it");
        request.setParameter("psw", "Giovannino!");

        servlet.doPost(request, response);

        User u2 = (User) request.getSession().getAttribute("user_logged");
        assertEquals("giovy",u2.getUsername());
        removeUser(u.getUsername());
    }


    @AfterEach
    public void tearDown (){
        request = null;
        response = null;
    }

    private void setParameter(HttpServletRequest request, String key, String value){
        when(request.getParameter(key)).thenReturn(value);
    }

    private void removeUser(String username) throws SQLException {
        Connection con = mds.getConnection();
        PreparedStatement st = con.prepareStatement("DELETE FROM users WHERE username = ?");
        st.setString(1,username);
        st.executeUpdate();
    }

}
