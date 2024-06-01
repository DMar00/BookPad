package control.user;

import com.google.gson.Gson;
import dao.UserDAO;
import bean.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "FollowServlet", value = "/FollowServlet")
public class FollowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");

        Map<String, Integer> object = new HashMap<>();
        System.out.println(userLogged);
        if (userLogged!=null){
            //seguo gia?
            String username_url= request.getParameter("username");
            System.out.println(username_url);
            UserDAO us_dao = new UserDAO(ds);
            try {
                boolean fl = us_dao.isFollowing(userLogged, username_url);
                User u2 = us_dao.getByUsername(username_url);
                System.out.println(u2);
                if(fl==true) {
                    //unfollow
                    us_dao.unfollow(userLogged,u2);
                    object.put("stat",0);
                }
                else{
                    //follow
                    us_dao.follow(userLogged,u2);
                    object.put("stat",1);
                }
                //update followings user loggato
                userLogged.setFollowings(us_dao.getFollowings(userLogged));
                //restituisci nuovo numero followers u2
                int followers = us_dao.getFollowers(u2).size();
                object.put("n_followers",followers);
                // Write the object as a JSON string
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.write(new Gson().toJson(object));
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            response.setStatus(312);
        }
    }
}
