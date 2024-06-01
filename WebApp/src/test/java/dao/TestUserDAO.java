package dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import bean.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestUserDAO {
    private static MysqlDataSource mds;
    private UserDAO userDao;
    private User user, user2, user3;

    @BeforeEach
    public void setUp() throws SQLException, NamingException {
        mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/bookpadtest");
        mds.setUser( "admin" );
        mds.setPassword( "adminPass" );

        userDao = new UserDAO(mds);

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
    }


    @Test
    public void TestReg () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        assertNotNull(u);

        User u2 = userDao.getByUsername(u.getUsername());
        assertNotNull(u2);
    }

    @Test
    public void TestLogin () throws SQLException {
        userDao.register(user.getEmail(), user.getPassword(), user.getUsername());

        assertNotNull(userDao.login(user.getEmail(),user.getPassword()));
        assertNull(userDao.login("utente1@libero.it","ciao"));
    }

    @Test
    public void TestGetByUsername () throws SQLException {
        userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        User u = userDao.getByUsername(user.getUsername());
        assertNotNull(u);

        User u2 = userDao.getByUsername("pippo_");
        assertNull(u2);
    }

    @Test
    public void TestCheckEmail () throws SQLException {
        userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        boolean t = userDao.checkEmail(user.getEmail());
        assertTrue(t);

        boolean t2 = userDao.checkEmail("pippo@unisa.it");
        assertFalse(t2);
    }

    @Test
    public void TestCheckUsername () throws SQLException {
        userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        boolean t = userDao.checkUsername(user.getUsername());
        assertTrue(t);

        boolean t2 = userDao.checkUsername("paperinop");
        assertFalse(t2);
    }

    @Test
    public void TestGetFollowings () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        User u2 = userDao.register(user2.getEmail(), user2.getPassword(), user2.getUsername());
        User u3 = userDao.register(user3.getEmail(), user3.getPassword(), user3.getUsername());

        userDao.follow(u,u2);
        userDao.follow(u,u3);

        ArrayList<User> f = (ArrayList<User>) userDao.getFollowings(u);

        assertEquals(u2.getId(),f.get(0).getId());
        assertEquals(u3.getId(),f.get(1).getId());
        assertNotEquals(u2.getId(),u.getId());
    }

    @Test
    public void TestGetFollowers () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        User u2 = userDao.register(user2.getEmail(), user2.getPassword(), user2.getUsername());
        User u3 = userDao.register(user3.getEmail(), user3.getPassword(), user3.getUsername());

        userDao.follow(u2,u);
        userDao.follow(u3,u);

        ArrayList<User> f = (ArrayList<User>) userDao.getFollowers(u);

        assertEquals(u2.getId(),f.get(0).getId());
        assertEquals(u3.getId(),f.get(1).getId());
        assertNotEquals(u2.getId(),u.getId());
    }


    @Test
    public void TestGetByUsernameSearch () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());

        List<User> s = userDao.getByUsernameSearch(u.getUsername());

        assertEquals(u.getId(),s.get(0).getId());

        List<User> s2 = userDao.getByUsernameSearch("plutone");
        assertEquals(0,s2.size());
    }

    @Test
    public void TestUpdateEmail () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        userDao.updateEmail(u, "newemail@gmail.com");

        boolean t = userDao.checkEmail("newemail@gmail.com");
        assertTrue(t);

        boolean t2 = userDao.checkEmail("prova@gmail.com");
        assertFalse(t2);
    }

    @Test
    public void TestUpdateBio () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        userDao.updateBio(u, "ciaoMondo");

        User u2 = userDao.getByUsername(u.getUsername());
        assertEquals("ciaoMondo", u2.getAbout());
        assertNotEquals(u.getAbout(),u2.getAbout());
    }

    @Test
    public void TestUpdatePassword () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        userDao.updatePassword(u, "nuovaPassword88!!");

        User u2 = userDao.getByUsername(u.getUsername());
        assertNotEquals(u.getPassword(),u2.getPassword());

        User u3 = userDao.login(u2.getEmail(),"nuovaPassword88!!");
        assertNotNull(u3);
    }

    @Test
    public void TestFollow() throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        User u2 = userDao.register(user2.getEmail(), user2.getPassword(), user2.getUsername());

        userDao.follow(u,u2);

        boolean f = userDao.isFollowing(u, u2.getUsername());
        assertTrue(f);

        boolean f2 = userDao.isFollowing(u2, u.getUsername());
        assertFalse(f2);
    }

    @Test
    public void TestUnfollow () throws SQLException {
        User u = userDao.register(user.getEmail(), user.getPassword(), user.getUsername());
        User u2 = userDao.register(user2.getEmail(), user2.getPassword(), user2.getUsername());

        userDao.follow(u,u2);
        boolean f = userDao.isFollowing(u, u2.getUsername());
        assertTrue(f);

        userDao.unfollow(u,u2);

        boolean f2 = userDao.isFollowing(u, u2.getUsername());
        assertFalse(f2);
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
