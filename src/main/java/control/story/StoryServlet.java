package control.story;

import dao.ChapterDAO;
import dao.CommentDAO;
import dao.StoryDAO;
import dao.TagDAO;
import bean.Chapter;
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

@WebServlet(name = "Stories", value = "/Stories")
public class StoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        String id_url= request.getParameter("id");  //username in url

        if(userLogged!=null){
            if(id_url==null || id_url.equals("")){
                request.setAttribute("notfounditem","Storia non esistente!");
                this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
            }else{
                DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
                StoryDAO str_dao = new StoryDAO(ds);
                try {
                    Story story = str_dao.getById(Integer.parseInt(id_url));
                    if (story==null){
                        request.setAttribute("notfounditem","Storia non esistente!");
                        this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
                    }else{
                        //add tags
                        TagDAO tag_dao = new TagDAO(ds);
                        ArrayList<String> tags = (ArrayList<String>) tag_dao.getAllTags(story);
                        story.setTags(tags);
                        //add chapters
                        ChapterDAO ch_dao = new ChapterDAO(ds);
                        ArrayList<Chapter> chapters = (ArrayList<Chapter>) ch_dao.getAllChapters(story);
                        story.setChapters(chapters);
                        //control if user liked story
                        boolean isLike = false;
                        isLike = str_dao.isLike(userLogged,story.getId());
                        //control if user saved story
                        boolean isSave = false;
                        isSave = str_dao.isSaved(userLogged,story.getId());
                        //add comments
                        CommentDAO cm_dao = new CommentDAO(ds);
                        story.setComments(cm_dao.getAllComments(story));
                        //
                        request.setAttribute("story_found",story);
                        request.setAttribute("isLike",isLike);
                        request.setAttribute("isSave",isSave);
                        this.getServletContext().getRequestDispatcher("/story.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
