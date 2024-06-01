package integrazione;

import bean.Story;
import bean.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import control.authentication.RegServlet;
import control.search.SearchServlet;
import control.story.WriteStoryServlet;
import dao.StoryDAO;
import dao.UserDAO;
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
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestRicerca extends RegServlet {
    private static MysqlDataSource mds;
    private UserDAO userDao;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private SearchServlet servlet;
    private StoryDAO storyDao;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser("admin");
        mds.setPassword("adminPass");

        RequestDispatcher rd = mock(RequestDispatcher.class);
        doNothing().when(rd).forward(isA(HttpServletRequest.class), isA(HttpServletResponse.class));

        ServletContext ctx = mock(ServletContext.class);
        when(ctx.getAttribute("dataSource")).thenReturn(mds);
        when(ctx.getRequestDispatcher(isA(String.class))).thenReturn(rd);
        servlet = new SearchServlet() {
            @Override
            public ServletContext getServletContext() {
                return ctx;
            }
        };

        userDao = new UserDAO(mds);
        storyDao = new StoryDAO(mds);
    }

    @Test
    public void TC_RIC_1() throws ServletException, IOException {
        // request.setParameter("type", "title");
        request.setParameter("title_name", "pippo baudo");
        servlet.doGet(request, response);
        assertEquals("Ricerca errata!", request.getAttribute("notfounditem"));
    }

    @Test
    public void TC_RIC_2() throws ServletException, IOException {
        request.setParameter("type", "tipononvalido");
        request.setParameter("title_name", "pippo baudo");
        servlet.doGet(request, response);
        assertEquals("Ricerca errata!", request.getAttribute("notfounditem"));
    }

    @Test
    public void TC_RIC_3() throws ServletException, IOException {
        request.setParameter("type", "title");
        // request.setParameter("title_name", "pippo baudo");
        servlet.doGet(request, response);
        assertEquals("Titolo errato!", request.getAttribute("notfounditem"));
    }

    @Test
    public void TC_RIC_4() throws ServletException, IOException {
        request.setParameter("type", "title");
        request.setParameter("title_name", "");
        servlet.doGet(request, response);
        assertEquals("Titolo errato!", request.getAttribute("notfounditem"));
    }

    @Test
    public void TC_RIC_5() throws ServletException, IOException {
        request.setParameter("type", "title");
        request.setParameter("title_name", "pippo baudo");
        servlet.doGet(request, response);
        assertNotNull(request.getAttribute("results"));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        request = null;
        response = null;
        removeUsers();
    }

    private void setParameter(HttpServletRequest request, String key, String value) {
        when(request.getParameter(key)).thenReturn(value);
    }

    private void removeUsers() throws SQLException {
        Connection con = mds.getConnection();
        PreparedStatement st = con.prepareStatement("DELETE FROM users WHERE id>0");
        st.executeUpdate();
    }

}
