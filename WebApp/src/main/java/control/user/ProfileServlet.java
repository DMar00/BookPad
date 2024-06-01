package control.user;

import dao.StoryDAO;
import dao.UserDAO;
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

@WebServlet(name = "Profile", value = "/Profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged");
        String username_url= request.getParameter("username");  //username in url
        if(username_url==null || username_url.equals("")){
            request.setAttribute("notfounditem","Utente non esistente!");
            this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
        }else {
            DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
            UserDAO us_dao = new UserDAO(ds);
            StoryDAO st_dao = new StoryDAO(ds);

            if (userLogged != null && userLogged.getUsername().equals(username_url)){
                try {
                    ArrayList<User> followings = (ArrayList<User>) us_dao.getFollowings(userLogged);
                    userLogged.setFollowings(followings);
                    ArrayList<User> followers = (ArrayList<User>) us_dao.getFollowers(userLogged);
                    userLogged.setFollowers(followers);
                    ArrayList<Story> saved = (ArrayList<Story>) st_dao.getSavedStories(userLogged);
                    userLogged.setSaved_stories(saved);
                    ArrayList<Story> written = (ArrayList<Story>) st_dao.getPublishedStories(userLogged);
                    userLogged.setPublished_stories(written);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("user",userLogged);
                this.getServletContext().getRequestDispatcher("/profilo-log.jsp").forward(request, response);
            }else{
                User u = new User();
                try {
                    u = us_dao.getByUsername(username_url);
                    if(u!=null){
                        ArrayList<User> followings = (ArrayList<User>) us_dao.getFollowings(u);
                        u.setFollowings(followings);
                        ArrayList<User> followers = (ArrayList<User>) us_dao.getFollowers(u);
                        u.setFollowers(followers);
                        ArrayList<Story> saved = (ArrayList<Story>) st_dao.getSavedStories(u);
                        u.setSaved_stories(saved);
                        ArrayList<Story> written = (ArrayList<Story>) st_dao.getPublishedStories(u);
                        u.setPublished_stories(written);
                        //is follow
                        if(userLogged!=null) {
                            boolean fl = us_dao.isFollowing(userLogged, username_url);
                            if (fl == true) request.setAttribute("isFollowing", true);
                            else request.setAttribute("isFollowing", false);
                        }else{
                            request.setAttribute("isFollowing", false);
                        }
                        request.setAttribute("user_found",u);
                        this.getServletContext().getRequestDispatcher("/profilo-other.jsp").forward(request, response);
                    }else{
                        request.setAttribute("notfounditem","Utente non esistente!");
                        this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
