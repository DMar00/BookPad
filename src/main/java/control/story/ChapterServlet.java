package control.story;

import com.google.gson.Gson;
import dao.ChapterDAO;
import bean.Chapter;

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

@WebServlet(name = "ChapterServlet", value = "/ChapterServlet")
public class ChapterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("start chap");
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        int id = Integer.parseInt(request.getParameter("id"));
        int numero = Integer.parseInt(request.getParameter("numero"));
        ChapterDAO ch_dao = new ChapterDAO(ds);

        Chapter ch = new Chapter();
        try {
            ch = ch_dao.getChapter(id,numero);

            // Define an object to return to ajax
            Map<String, String> object = new HashMap<>();
            object.put("titolo", ch.getTitle());
            object.put("content", ch.getContent());
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
