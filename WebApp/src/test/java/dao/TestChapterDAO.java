package dao;

import bean.Chapter;
import bean.Genre;
import bean.Story;
import bean.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestChapterDAO {
    private static MysqlDataSource mds;
    private static ChapterDAO chapterDao;
    private static UserDAO userDao;
    private static StoryDAO storyDao;
    private static GenreDAO genreDao;
    private static User user1;
    private Story story;
    private static Chapter chapter1, chapter2;

    @BeforeEach
    public void setUp() throws SQLException, NamingException {
        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser( "admin" );
        mds.setPassword( "adminPass" );

        chapterDao= new ChapterDAO(mds);
        userDao = new UserDAO(mds);
        storyDao = new StoryDAO(mds);
        genreDao = new GenreDAO(mds);

        user1 = new User();
        user1.setUsername("utente1");
        user1.setEmail("prova@gmail.com");
        user1.setPassword("Prova2000!");

        story = new Story();
        story.setTitle("Casa nel Bosco");
        story.setPlot("Plot prova1");

        chapter1 = new Chapter();
        chapter1.setTitle("Capitolo 1");
        chapter1.setNumber(1);
        chapter1.setContent("testo capitolo 1 ...");

        chapter2 = new Chapter();
        chapter2.setTitle("Capitolo 2");
        chapter2.setNumber(2);
        chapter2.setContent("testo capitolo 2 ...");
    }

    @Test
    public void testAddChapter() throws SQLException {
        User u = userDao.register(user1.getEmail(), user1.getPassword(), user1.getUsername());
        Genre g = genreDao.getByName("Horror");
        Story s = storyDao.addStory(story.getTitle(),story.getPlot(),u,g,null);

        Chapter c1 = chapterDao.addChapter(
            chapter1.getTitle(),
            chapter1.getContent(),
            chapter1.getNumber(),
            s
        );
        assertEquals(c1.getTitle(), chapter1.getTitle());
        assertEquals(c1.getContent(), chapter1.getContent());
        assertEquals(c1.getNumber(), chapter1.getNumber());

        Chapter c2 = chapterDao.addChapter(
            chapter2.getTitle(),
            chapter2.getContent(),
            chapter2.getNumber(),
            s
        );
        assertEquals(c2.getTitle(), chapter2.getTitle());
        assertEquals(c2.getContent(), chapter2.getContent());
        assertEquals(c2.getNumber(), chapter2.getNumber());

        ArrayList<Chapter> chapters = (ArrayList<Chapter>) chapterDao.getAllChapters(s);
        assertEquals(2, chapters.size());

        boolean found1 = false;
        for(Chapter chap: chapters) {
            if(
                chap.getTitle().equals(c1.getTitle()) &&
                chap.getContent().equals(c1.getContent()) &&
                chap.getNumber() == c1.getNumber()
            ) {
                found1 = true;
            }
        }
        assertTrue(found1);

        boolean found2 = false;
        for(Chapter chap: chapters) {
            if(
                chap.getTitle().equals(c2.getTitle()) &&
                chap.getContent().equals(c2.getContent()) &&
                chap.getNumber() == c2.getNumber()
            ) {
                found2 = true;
            }
        }
        assertTrue(found2);
    }
}