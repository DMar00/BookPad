package control.story;

import com.google.gson.Gson;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SaveStoryServlet", value = "/SaveStoryServlet")
public class SaveStoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");

        int id_story= Integer.parseInt(request.getParameter("id"));

        StoryDAO st_dao = new StoryDAO(ds);
        try {
            Story st = st_dao.getById(id_story);
            Map<String, String> object = new HashMap<>();
            boolean isSaved = st_dao.isSaved(userLogged, id_story);
            if(isSaved){
                st_dao.removeSavedStory(userLogged, id_story);
                st_dao.updateSavings(id_story, st.getN_savings()-1);
                object.put("status", "false");
                object.put("num", String.valueOf(st.getN_savings()-1));
            }else{
                st_dao.saveStory(userLogged, id_story);
                st_dao.updateSavings(id_story, st.getN_savings()+1);
                object.put("status", "true");
                object.put("num", String.valueOf(st.getN_savings()+1));
            }
            // Write the object as a JSON string
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write(new Gson().toJson(object));
            out.flush();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
