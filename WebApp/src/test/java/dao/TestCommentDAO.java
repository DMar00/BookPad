package dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import bean.Comment;
import bean.Genre;
import bean.Story;
import bean.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCommentDAO {
    private static MysqlDataSource mds;
    private static CommentDAO commentDao;
    private static UserDAO userDao;
    private static StoryDAO storyDao;
    private static GenreDAO genreDao;
    private static User user1, user2;
    private static Story story;
    private static Comment comment;

    @BeforeEach
    public void setUp() throws SQLException, NamingException {
        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser( "admin" );
        mds.setPassword( "adminPass" );

        commentDao= new CommentDAO(mds);
        userDao = new UserDAO(mds);
        storyDao = new StoryDAO(mds);
        genreDao = new GenreDAO(mds);

        user1 = new User();
        user1.setUsername("utente1");
        user1.setEmail("prova@gmail.com");
        user1.setPassword("Prova2000!");

        user2 = new User();
        user2.setUsername("commentatore");
        user2.setEmail("commentatore@gmail.com");
        user2.setPassword("Prova2000!");

        story=new Story();
        story.setTitle("Casa nel Bosco");
        story.setPlot("Plot prova1");

        comment = new Comment();
        comment.setText("testo commento");
        comment.setUser(user2);
        comment.setDate(LocalDate.of(2020, 1, 8));
    }

    @Test
    public void testAddComment() throws SQLException {
        User u = userDao.register(user1.getEmail(), user1.getPassword(), user1.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        Comment c = commentDao.addComment(u, s.getId(), comment.getText(), comment.getDate());

        ArrayList<Comment> comments = (ArrayList<Comment>) commentDao.getAllComments(s);

        boolean found = false;

        for(Comment com:comments) {
            if(com.getId() == c.getId())
                found = true;
        }
        assertTrue(found);
    }


    @AfterEach
    public void tearDown () throws SQLException {
        removeUsers();
    }

    private void removeUsers() throws SQLException {
        Connection con = mds.getConnection();
        PreparedStatement st = con.prepareStatement("DELETE FROM users WHERE id>0");
        st.executeUpdate();
    }
}