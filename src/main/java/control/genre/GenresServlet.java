package control.genre;

import dao.StoryDAO;
import bean.Genre;
import dao.GenreDAO;
import bean.Story;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Genres", urlPatterns = "/Genres", loadOnStartup=1)
public class GenresServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        GenreDAO genre_dao = new GenreDAO(ds);
        try {
            ArrayList<Genre> genres = (ArrayList<Genre>) genre_dao.getAllGenres();
            getServletContext().setAttribute("genres_list", genres);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String genre_url= request.getParameter("genre_name");  //genre in url
        if(genre_url == null || genre_url.equals("")){
            request.setAttribute("notfounditem","Genere non esistente!");
            this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
        }else{
            DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
            GenreDAO gen_dao = new GenreDAO(ds);
            try {
                Genre genre = gen_dao.getByName(genre_url);
                if(genre == null){
                    request.setAttribute("notfounditem","Genere non esistente!");
                    this.getServletContext().getRequestDispatcher("/page-not-found-item.jsp").forward(request, response);
                }else{
                    StoryDAO st_dao = new StoryDAO(ds);
                    ArrayList<Story> stories = (ArrayList<Story>) st_dao.getByGenre(genre);
                    if(stories.size() == 0){
                        request.setAttribute("no_stories", "Nessuna storia!");
                    }else{
                        genre.setStories(stories);
                    }
                    request.setAttribute("genre_found",genre);
                    this.getServletContext().getRequestDispatcher("/genere.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
