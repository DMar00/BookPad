package dao;

import bean.User;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DataSource dataSource;

    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User login(String email, String password) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users where email = ? and password=md5(?)");
            st.setString(1, email);
            st.setString(2, password);
            rs = st.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAbout(rs.getString("about"));
                user.setAvatar(rs.getBinaryStream("avatar"));
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return user;
    }

    public User register(String email, String password, String username) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO users (email,password,username) values (?,MD5(?),?)");
            st.setString(1, email);
            st.setString(2, password);
            st.setString(3, username);
            st.executeUpdate();
            user = getByUsername(username);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return user;
    }

    public User getByUsername(String username) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users where username = ?");
            st.setString(1, username);
            rs = st.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAbout(rs.getString("about"));
                user.setAvatar(rs.getBinaryStream("avatar"));
            }
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return user;
    }

    public boolean checkEmail(String email) throws SQLException {
        boolean c = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users where email = ?");
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                c = true;
            }
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return c;
    }

    public boolean checkUsername(String username) throws SQLException {
        boolean c = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users where username = ?");
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
                c = true;
            }
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return c;
    }

    public InputStream getAvatar(String username) throws SQLException {
        InputStream is = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users where username = ?");
            st.setString(1, username);
            rs = st.executeQuery();
            while (rs.next()) {
                is = rs.getBinaryStream("avatar");
            }
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return is;
    }

    public List<User> getFollowings(User u) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT u.username, u.avatar,u.about, u.email, u.id, u.password from follow as f, users as u\n" +
                    "         where f.ID_followed_by=? and f.ID_who_i_follow=u.id ORDER BY u.username");
            st.setInt(1,u.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAbout(rs.getString("about"));
                user.setAvatar(rs.getBinaryStream("avatar"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return users;
    }

    public List<User> getFollowers(User u) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT u.username, u.avatar,u.about, u.id from follow as f, users as u\n" +
                    "         where f.ID_who_i_follow=? and f.ID_followed_by=u.id");
            st.setInt(1,u.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAbout(rs.getString("about"));
                user.setAvatar(rs.getBinaryStream("avatar"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return users;
    }

    public List<User> getByUsernameSearch(String u) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users as u\n" +
                    "WHERE LOCATE(?,u.username,1)>0");
            st.setString(1,u);
            rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAbout(rs.getString("about"));
                user.setAvatar(rs.getBinaryStream("avatar"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return users;
    }

    public void updateAvatar(User u, InputStream avatarStream) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE users SET avatar = ? WHERE id = ?");
            st.setBlob(1,avatarStream);
            st.setInt(2, u.getId());
            st.executeUpdate();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public void updateBio(User u, String bio) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE users SET about = ? WHERE id = ?");
            st.setString(1,bio);
            st.setInt(2, u.getId());
            st.executeUpdate();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public void updateEmail(User u, String email) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE users SET email = ? WHERE id = ?");
            st.setString(1,email);
            st.setInt(2, u.getId());
            st.executeUpdate();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public void updatePassword(User u, String pass) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE users SET password = md5(?) WHERE id = ?");
            st.setString(1,pass);
            st.setInt(2, u.getId());
            st.executeUpdate();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public boolean isFollowing(User u, String username) throws SQLException {
        boolean l = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM users as u, follow as f\n" +
                    "WHERE f.ID_followed_by=? and u.username=? and f.ID_who_i_follow=u.id");
            st.setInt(1, u.getId());
            st.setString(2, username);
            rs = st.executeQuery();
            while (rs.next()) {
                l = true;
            }
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return l;
    }

    public void unfollow(User u, User u2) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("DELETE FROM follow WHERE ID_who_i_follow = ? AND ID_followed_by = ?");
            st.setInt(1,u2.getId());
            st.setInt(2, u.getId());
            st.executeUpdate();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public void follow(User u, User u2) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO follow values (?,?)");
            st.setInt(2, u.getId());
            st.setInt(1, u2.getId());
            st.executeUpdate();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }finally {
                if (con != null) {
                    con.close();
                }
            }
        }
    }

}
