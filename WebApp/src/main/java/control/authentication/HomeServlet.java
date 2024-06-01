package control.authentication;

import dao.StoryDAO;
import bean.Story;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Home", value = "/Home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged");
        if(userLogged!=null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
            StoryDAO st_dao = new StoryDAO(ds);
            try {
                ArrayList<Story> followings_stories = (ArrayList<Story>) st_dao.getFollowingsStories(userLogged);
                request.setAttribute("stories",followings_stories);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.getServletContext().getRequestDispatcher("/index-log.jsp").forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
