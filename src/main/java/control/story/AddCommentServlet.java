package control.story;

import com.google.gson.Gson;
import dao.CommentDAO;
import dao.StoryDAO;
import bean.Comment;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCommentServlet", value = "/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("Start add comment");
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        String comment= request.getParameter("comment");
        //System.out.println("comment --> "+comment);
        int id_story= Integer.parseInt(request.getParameter("id_story"));
        //System.out.println("id story--> "+id_story);
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        StoryDAO st_dao = new StoryDAO(ds);
        Story st = null;
        try {
            st = st_dao.getById(id_story);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(comment.length()>0 && comment.length()<=500 && st != null){
            //System.out.println("ok length");
            CommentDAO cm_dao = new CommentDAO(ds);
            Comment cm = new Comment();
            //data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now();
            //System.out.println("data --> "+date.format(formatter));
            try {
                cm = cm_dao.addComment(userLogged,id_story,comment, date);

                cm_dao.updateComments(id_story, st.getN_comments()+1);
                //System.out.println(cm);
                // Define an object to return to ajax
                Map<String, String> object = new HashMap<>();
                object.put("username", userLogged.getUsername());
                object.put("data", date.format(formatter));
                object.put("txt", cm.getText());
                object.put("num_comm", String.valueOf(st.getN_comments()+1));
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
}
