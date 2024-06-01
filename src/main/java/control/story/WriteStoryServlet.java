package control.story;

import dao.ChapterDAO;
import dao.GenreDAO;
import dao.StoryDAO;
import dao.TagDAO;
import bean.Chapter;
import bean.Genre;
import bean.Story;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Util.checkExtImg;

@WebServlet(name = "Write", value = "/Write")
@MultipartConfig
public class WriteStoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        if(userLogged!=null){
            this.getServletContext().getRequestDispatcher("/scrivi.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/Login");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userLogged = (User) request.getSession().getAttribute("user_logged"); //user in sessione
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");

        String titolo=request.getParameter("titolo"); //name in input
        String trama=request.getParameter("trama");
        String[] tags = request.getParameterValues("tag_value");
        String genere = request.getParameter("genere");
        Part coverPart = request.getPart("cover");// cover = name of input file
        System.out.println(coverPart);
        //System.out.println(fileName);
        String[] chapters_titolo = request.getParameterValues("chap_title");
        String[] chapters_txt = request.getParameterValues("chap_text");

        /*******************/
        GenreDAO gen_dao = new GenreDAO(ds);
        StoryDAO st_dao = new StoryDAO(ds);
        String newTitolo = deleteMultipleSpaces(titolo);
        InputStream coverStream = null;
        if(coverPart!=null){
            String fileName = Paths.get(coverPart.getSubmittedFileName()).getFileName().toString();//nome immagine
            if(coverPart.getSize()>0){  //se l'immagine è stata inserita
                boolean check_img = checkExtImg(fileName);
                if (check_img) coverStream = coverPart.getInputStream();
            }
        }
        try {
            Genre genre = gen_dao.getByName(genere);
            boolean ckt = titolo.length()>0 && titolo.length()<=60;
            boolean cktr = trama.length()>0 && trama.length()<=30000;

            boolean ckChap=true;

            if(chapters_txt.length!=chapters_titolo.length){
                ckChap=false;
            }else{
                for(int i=0; i<chapters_titolo.length;i++){
                    if(!(chapters_titolo[i].length()>0 && chapters_titolo[i].length()<=60) || !(chapters_txt[i].length()>0 && chapters_txt[i].length()<=60000)){
                        ckChap=false;
                        break;
                    }
                }
            }

            if(genre!=null && ckt && cktr && chapters_txt.length>0 && ckChap) {
                System.out.println("condizioni soddisfatte!" + titolo);
                Story story = st_dao.addStory(newTitolo,trama,userLogged,genre,coverStream);
                story.setGenre(genre);
                //inserimento tags
                TagDAO tag_dao = new TagDAO(ds);
                if(tags!=null){
                    for(int i=0; i<tags.length;i++){
                        if(!tag_dao.checkTag(tags[i])){
                            tag_dao.addTag(tags[i]);
                        }
                        tag_dao.addTagToStory(story,tags[i]);
                        story.setTags(new ArrayList<String>());
                        story.addTag(tags[i]);
                    }
                }
                //inserimento capitoli
                ChapterDAO ch_dao= new ChapterDAO(ds);
                if(chapters_txt.length==chapters_titolo.length){
                    for(int i=0; i<chapters_titolo.length;i++){
                        //System.out.println(chapters_titolo[i]);
                        Chapter ch = new Chapter();
                        String newText = chapters_txt[i].replace("\r\n", "\n");
                        ch = ch_dao.addChapter(chapters_titolo[i],newText,i+1,story);
                        story.setChapters(new ArrayList<Chapter>());
                        story.addChapter(ch);
                    }
                }
                //reindirizzamento
                HttpSession session = request.getSession();
                session.setAttribute("story_saved", story);
                response.sendRedirect(request.getContextPath() + "/PublishedStory");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**private boolean checkExtImg(String fileName){
        boolean x = false;
        String[] fileNameSplit = fileName.split(Pattern.quote("."));
        String ext = fileNameSplit[fileNameSplit.length - 1];
        if(ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("bmp")){
            x = true;
        }
        return x;
    }*/

    private String deleteMultipleSpaces(String s){
        String removeSpaces = s.replaceAll("\\s+", " ");
        return removeSpaces;
    }
}
