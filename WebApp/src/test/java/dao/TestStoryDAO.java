package dao;

import com.mysql.cj.jdbc.MysqlDataSource;
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
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestStoryDAO {
    private static MysqlDataSource mds;
    private UserDAO userDao;
    private StoryDAO storyDao;
    private GenreDAO genreDao;
    private TagDAO tagDao;
    private User user, user2, user3;
    private Story story, story2, story3;


    @BeforeEach
    public void setUp() throws SQLException, NamingException {
        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser( "admin" );
        mds.setPassword( "adminPass" );

        userDao = new UserDAO(mds);
        storyDao = new StoryDAO(mds);
        genreDao = new GenreDAO(mds);
        tagDao = new TagDAO(mds);

        user=new User();
        user.setUsername("utente1");
        user.setEmail("prova@gmail.com");
        user.setPassword("Prova2000!");

        user2= new User();
        user2.setUsername("utente2");
        user2.setEmail("prova2@gmail.com");
        user2.setPassword("Prova2000!");

        user3= new User();
        user3.setUsername("utente3");
        user3.setEmail("prova3@gmail.com");
        user3.setPassword("Prova2000!");

        story=new Story();
        story.setTitle("Casa nel Bosco");
        story.setPlot("Plot prova1");

        story2=new Story();
        story2.setTitle("Bosco incantato");
        story2.setPlot("Plot prova2");
    }


    @Test
    public void TestAddStory () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        Story s2 = storyDao.getById(s.getId());
        assertNotNull(s2);
    }

    @Test
    public void TestGetByTitleSearch () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);
        Story s2 = storyDao.addStory(story2.getTitle(),story2.getPlot(),u,g,null);

        ArrayList<Story> st = (ArrayList<Story>) storyDao.getByTitleSearch(s.getTitle());
        assertEquals(1,st.size());
        assertEquals(s.getTitle(),st.get(0).getTitle());

        ArrayList<Story> st2 = (ArrayList<Story>) storyDao.getByTitleSearch("Bosco");
        assertEquals(2,st2.size());
        assertEquals(s.getTitle(),st2.get(0).getTitle());
        assertEquals(s2.getTitle(),st2.get(1).getTitle());

        ArrayList<Story> st3 = (ArrayList<Story>) storyDao.getByTitleSearch("Zorro");
        assertEquals(0,st3.size());
    }

    @Test
    public void TestGetByGenreSearch () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Genre g2 = genreDao.getByName("Romantico");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);
        Story s2 = storyDao.addStory(story2.getTitle(),story2.getPlot(),u,g,null);

        ArrayList<Story> st = (ArrayList<Story>) storyDao.getByGenre(g);
        assertEquals(2,st.size());
        assertEquals(s.getTitle(),st.get(0).getTitle());
        assertEquals(s2.getTitle(),st.get(1).getTitle());

        ArrayList<Story> st3 = (ArrayList<Story>) storyDao.getByGenre(g2);
        assertEquals(0,st3.size());
    }

    @Test
    public void TestGetByTagSearch () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);
        if(!tagDao.checkTag("ciao"))
            tagDao.addTag("ciao");
        tagDao.addTagToStory(s,"ciao");
        if(!tagDao.checkTag("miao"))
            tagDao.addTag("miao");
        tagDao.addTagToStory(s,"miao");
        Story s2 = storyDao.addStory(story2.getTitle(),story2.getPlot(),u,g,null);
        tagDao.addTagToStory(s2,"ciao");

        ArrayList<Story> st = (ArrayList<Story>) storyDao.getByTagSearch("ciao");
        assertEquals(2,st.size());
        assertEquals(s.getTitle(),st.get(0).getTitle());
        assertEquals(s2.getTitle(),st.get(1).getTitle());

        ArrayList<Story> st2 = (ArrayList<Story>) storyDao.getByTagSearch("miao");
        assertEquals(1,st2.size());
        assertEquals(s.getTitle(),st.get(0).getTitle());

        ArrayList<Story> st3 = (ArrayList<Story>) storyDao.getByTagSearch("verde");
        assertEquals(0,st3.size());
    }

    @Test
    public void TestSaveStory () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        storyDao.saveStory(u,s.getId());

        boolean l = storyDao.isSaved(u,s.getId());
        assertTrue(l);
    }

    @Test
    public void TestRemoveSavedStory () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        storyDao.saveStory(u,s.getId());
        storyDao.removeSavedStory(u,s.getId());
        boolean l = storyDao.isSaved(u,s.getId());
        assertFalse(l);
    }

    @Test
    public void TestAddLike () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        storyDao.addLike(u,s.getId());

        boolean l = storyDao.isLike(u,s.getId());
        assertTrue(l);
    }

    @Test
    public void TestRemoveLike () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        storyDao.addLike(u,s.getId());
        storyDao.removeLike(u,s.getId());
        boolean l2 = storyDao.isLike(u,s.getId());
        assertFalse(l2);
    }



    @AfterEach
    public void tearDown () throws SQLException {
       removeUser(user.getUsername());
       removeUser(user2.getUsername());
       removeUser(user3.getUsername());
    }

    private void removeUser(String username) throws SQLException {
        Connection con = mds.getConnection();
        PreparedStatement st = con.prepareStatement("DELETE FROM users WHERE username = ?");
        st.setString(1,username);
        st.executeUpdate();
    }
}
