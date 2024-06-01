package control.user;

import com.google.gson.Gson;
import dao.UserDAO;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static utils.Util.checkExtImg;

@WebServlet(name = "SettingsServlet", value = "/SettingsServlet")
@MultipartConfig
public class SettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Change avatar");
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        String action = request.getParameter("setting_type");
        System.out.println("action -->"+action);
        UserDAO us_dao = new UserDAO(ds);

        // Define an object to return to ajax
        Map<String, Boolean> object = new HashMap<>();
        boolean c = false;

        if(action.equals("avatar")){
            Part avatarPart = request.getPart("new_avatar");
            String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();//nome immagine
            InputStream avatarStream = null;
            if(avatarPart.getSize()>0){
                boolean check_img = checkExtImg(fileName);
                if (check_img) {
                    avatarStream = avatarPart.getInputStream();
                    try {
                        us_dao.updateAvatar(userLogged,avatarStream);
                        userLogged=us_dao.getByUsername(userLogged.getUsername());
                        request.getSession().setAttribute("user_logged", userLogged);
                        c = true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            object.put("updated",c);
        }else if(action.equals("bio")){
            String newBio = request.getParameter("new_bio");
            if(newBio.length()<500 && newBio.length()>0){
                try {
                    us_dao.updateBio(userLogged, newBio);
                    userLogged=us_dao.getByUsername(userLogged.getUsername());
                    request.getSession().setAttribute("user_logged", userLogged);
                    c = true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            object.put("updated",c);
        }else if(action.equals("email")){
            String newEmail = request.getParameter("new_email");
            boolean emailCheck = newEmail.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$");
            if(emailCheck){
                try {
                    if(!us_dao.checkEmail(newEmail)){
                        us_dao.updateEmail(userLogged, newEmail);
                        userLogged=us_dao.getByUsername(userLogged.getUsername());
                        request.getSession().setAttribute("user_logged", userLogged);
                        c = true;
                        object.put("existEmail",false);
                    }else{
                        object.put("existEmail",true);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            object.put("updated",c);
        }else if(action.equals("password")){
            //todo old password check
            String newPassword = request.getParameter("new_pass");
            String newPassword2 = request.getParameter("new_pass2");
            boolean passwordCheck = newPassword.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
            if(passwordCheck && newPassword2.equals(newPassword)){
                try {
                    us_dao.updatePassword(userLogged, newPassword);
                    userLogged=us_dao.getByUsername(userLogged.getUsername());
                    request.getSession().setAttribute("user_logged", userLogged);
                    c = true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            object.put("updated",c);
        }
        // Write the object as a JSON string
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(new Gson().toJson(object));
        out.flush();
    }
}
