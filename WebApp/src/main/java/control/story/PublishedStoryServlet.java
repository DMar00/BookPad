package control.story;

import bean.Story;
import bean.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PublishedStory", value = "/PublishedStory")
public class PublishedStoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Story story = (Story) request.getSession().getAttribute("story_saved");
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        if(userLogged!=null){
            request.setAttribute("story", story);
            request.getSession().removeAttribute("story_saved");
            this.getServletContext().getRequestDispatcher("/savedStory.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
