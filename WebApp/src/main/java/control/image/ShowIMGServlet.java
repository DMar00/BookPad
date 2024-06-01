package control.image;

import dao.StoryDAO;
import dao.UserDAO;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet(name = "Image", value = "/Image")
public class ShowIMGServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        String type = request.getParameter("type");
        if(type.equals("cover")) {
            int id = Integer.parseInt(request.getParameter("id"));
            StoryDAO st_dao = new StoryDAO(ds);
            try {
                InputStream str = st_dao.getCover(id);
                response.setContentType("image/jpeg");
                response.setContentLength((int) str.available());
                response.getOutputStream().write(IOUtils.toByteArray(str));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(type.equals("avatar")){
            String username = request.getParameter("username");
            UserDAO u_dao = new UserDAO(ds);
            try {
                InputStream str = u_dao.getAvatar(username);
                response.setContentType("image/jpeg");
                response.setContentLength((int) str.available());
                response.getOutputStream().write(IOUtils.toByteArray(str));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
