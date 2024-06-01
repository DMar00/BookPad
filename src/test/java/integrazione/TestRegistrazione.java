package integrazione;
import com.mysql.cj.jdbc.MysqlDataSource;
import dao.UserDAO;
import bean.User;
import org.junit.jupiter.api.AfterEach;
import control.authentication.RegServlet;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestRegistrazione extends RegServlet{
    private static MysqlDataSource mds;
    private UserDAO userDao;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private RegServlet servlet;

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
        servlet= new RegServlet(){
            @Override
            public ServletContext getServletContext() {
                return ctx;
            }
        };

        userDao = new UserDAO(mds);
    }


    @Test
    public void TC_REG_1() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero");
        request.setParameter("username", "giovanni@libero");
        request.setParameter("psw", "giovanni@libero");
        request.setParameter("psw2", "giovanni@libero");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_2() throws ServletException, IOException, SQLException {
        User u = userDao.register("giovanni@libero.it","Giovannino!","giovy");

        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "giovanni_");
        request.setParameter("psw", "Anna2000!!");
        request.setParameter("psw2", "Anna2000!!");

        servlet.doPost(request, response);

        assertEquals("E-mail già registrata!",request.getAttribute("errorEmail"));
        removeUser(u.getUsername());
    }

    @Test
    public void TC_REG_3() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "giovanni_");
        request.setParameter("psw", "ciao");
        request.setParameter("psw2", "ciao");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_4() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "giovanni_");
        request.setParameter("psw", "ciaoooooo");
        request.setParameter("psw2", "ciaoooooo");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_5() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "giovanni_");
        request.setParameter("psw", "Anna2000!!");
        request.setParameter("psw2", "ciaoooooo");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_6() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "gio");
        request.setParameter("psw", "Anna2000!!");
        request.setParameter("psw2", "Anna2000!!");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_7() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovanni@libero.it");
        request.setParameter("username", "9999999999999999");
        request.setParameter("psw", "Anna2000!!");
        request.setParameter("psw2", "Anna2000!!");

        servlet.doPost(request, response);

        boolean t = userDao.checkEmail("giovanni@libero");
        assertFalse(t);
    }

    @Test
    public void TC_REG_8() throws ServletException, IOException, SQLException {
        request.setParameter("email", "giovannii@libero.it");
        request.setParameter("username", "giovannino_99");
        request.setParameter("psw", "Anna2000!!");
        request.setParameter("psw2", "Anna2000!!");

        servlet.doPost(request, response);

        User u = (User) request.getSession().getAttribute("user_logged");
        assertEquals("giovannino_99",u.getUsername());
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
