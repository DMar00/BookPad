package control.search;

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

@WebServlet(name = "Search", value = "/Search")
public class SearchServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        String tp = request.getParameter("type");
        if(tp!=null && tp.equals("tag")){
            String tag_name = request.getParameter("tag_name");
            request.setAttribute("word",tag_name);
            request.setAttribute("type",tp);
            if(tag_name==null || tag_name.equals("") ){
                request.setAttribute("notfounditem","Tag errato!");
                this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
            }else{
                StoryDAO st_dao = new StoryDAO(ds);
                try {
                    ArrayList<Story> stories = (ArrayList<Story>) st_dao.getByTagSearch(tag_name);
                    request.setAttribute("content","story");
                    request.setAttribute("results",stories);
                    this.getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else if(tp!=null && tp.equals("title")){
            String title = request.getParameter("title_name");
            request.setAttribute("word",title);
            request.setAttribute("type",tp);
            if( title==null || title.equals("")){
                request.setAttribute("notfounditem","Titolo errato!");
                this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
            }else{
                StoryDAO st_dao = new StoryDAO(ds);
                try {
                    ArrayList<Story> stories = (ArrayList<Story>) st_dao.getByTitleSearch(title);
                    request.setAttribute("content","story");
                    request.setAttribute("results",stories);
                    this.getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else if(tp!=null && tp.equals("user")){
            String username = request.getParameter("username");
            request.setAttribute("type",tp);
            if( username==null || username.equals("")){
                request.setAttribute("notfounditem","Username errato!");
                this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
            }else{
                UserDAO us_dao = new UserDAO(ds);
                request.setAttribute("word",username);
                try {
                    ArrayList<User> users = (ArrayList<User>) us_dao.getByUsernameSearch(username);
                    request.setAttribute("content","user");
                    request.setAttribute("results",users);
                    this.getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            request.setAttribute("notfounditem","Ricerca errata!");
            this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
