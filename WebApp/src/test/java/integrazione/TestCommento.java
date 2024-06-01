package integrazione;

import bean.Genre;
import bean.Story;
import bean.User;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import control.story.AddCommentServlet;
import control.story.WriteStoryServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.CommentDAO;
import dao.GenreDAO;
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
import java.util.Map;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class TestCommento {
    private static MysqlDataSource mds;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private AddCommentServlet servlet;
    private User user1, user2;
    private Story story;
    private Genre genere;
    private static UserDAO userDao;
    private static StoryDAO storyDao;
    private static GenreDAO genreDao;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse(){
            @Override
            public void setContentType(String contentType){

            }
        };

        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser("admin");
        mds.setPassword("adminPass");

        RequestDispatcher rd = mock(RequestDispatcher.class);
        doNothing().when(rd).forward(isA(HttpServletRequest.class), isA(HttpServletResponse.class));

        ServletContext ctx = mock(ServletContext.class);
        when(ctx.getAttribute("dataSource")).thenReturn(mds);
        when(ctx.getRequestDispatcher(isA(String.class))).thenReturn(rd);
        servlet = new AddCommentServlet() {
            @Override
            public ServletContext getServletContext() {
                return ctx;
            }
        };

        userDao = new UserDAO(mds);
        storyDao = new StoryDAO(mds);
        genreDao = new GenreDAO(mds);

        user1 = userDao.register("giovanni@libero.it","Giovannino!","giovy");
        user2 = userDao.register("commentatore@gmail.com","Prova2000!","commentatore");
        genere = genreDao.getByName("Horror");
        story = storyDao.addStory("Titolo Storia","trama...", user1, genere,null);
    }

    @Test
    public void TC_COM_1() throws ServletException, IOException {
        request.getSession().setAttribute("user_logged", user1);
        request.setParameter("comment", "Testo commento");
        request.setParameter("id_story", "99999");
        servlet.doPost(request, response);
        assertEquals(response.getContentAsString(), "");
    }

    @Test
    public void TC_COM_2() throws ServletException, IOException {
        request.getSession().setAttribute("user_logged", user1);
        request.setParameter("comment", "");
        request.setParameter("id_story", story.getId() + "");
        servlet.doPost(request, response);
        assertEquals(response.getContentAsString(), "");
    }

    @Test
    public void TC_COM_3() throws ServletException, IOException {
        request.getSession().setAttribute("user_logged", user1);
        request.setParameter("comment", "Testo commento");
        request.setParameter("id_story", story.getId() + "");
        servlet.doPost(request, response);
        Map<String, String> object = new Gson().fromJson(response.getContentAsString(), Map.class);
        assertEquals(object.get("txt"), "Testo commento");
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
