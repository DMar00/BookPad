package dao;

import bean.Genre;
import bean.Story;
import bean.User;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoryDAO {
    private DataSource dataSource;

    public StoryDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Story> getByGenre(Genre genre) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT s.id,s.title,s.plot,s.n_like,s.n_comments,s.n_savings,s.cover,u.username FROM genres as g , stories as s, users as u" +
                    " WHERE g.id = ? && g.id=s.ID_genre && s.ID_user=u.id");
            st.setInt(1,genre.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                stories.add(story);
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
        return stories;
    }

    public List<Story> getByTagSearch(String tag) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories as s, tags as t, has_tag as h, genres as g, users as u\n" +
                    "WHERE h.text_tag=? and h.ID_story=s.id and h.text_tag=t.text_tag and s.ID_genre=g.id and s.ID_user=u.id");
            st.setString(1,tag);
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                stories.add(story);
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
        return stories;
    }

    public List<Story> getByTitleSearch(String title) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories as s, genres as g, users as u\n" +
                    "WHERE LOCATE(?,s.title,1)>0 and s.ID_genre=g.id and s.ID_user=u.id");
            st.setString(1,title);
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                stories.add(story);
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
        return stories;
    }

    public Story addStory(String title, String plot, User author, Genre genre , InputStream cover) throws SQLException {
        Story story = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO stories (title,plot,ID_user,ID_genre,cover,dt) values (?,?,?,?,?,now());");
            st.setString(1, title);
            st.setString(2, plot);
            st.setInt(3, author.getId());
            st.setInt(4, genre.getId());
            st.setBlob(5, cover);
            st.executeUpdate();
            story = getByTitle(title);
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
        return story;
    }

    public Story getById(int id) throws SQLException {
        Story story = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories as s, users as u, genres as g where s.id = ? and s.ID_user=u.id and s.ID_genre=g.id;");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                Genre genre =new Genre();
                genre.setName(rs.getString("name"));
                story.setGenre(genre);
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
        return story;
    }

    public Story getByTitle(String title) throws SQLException {
        Story story = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories as s, users as u, genres as g where s.title = ? and s.ID_user=u.id and s.ID_genre=g.id;");
            st.setString(1, title);
            rs = st.executeQuery();
            while (rs.next()) {
                story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                Genre genre =new Genre();
                genre.setName(rs.getString("name"));
                story.setGenre(genre);
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
        return story;
    }

    public InputStream getCover(int id) throws SQLException {
        InputStream is = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                is = rs.getBinaryStream("cover");
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

    public boolean isLike(User u, int id_story) throws SQLException {
        boolean l = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT s.title, s.n_like FROM stories as s, users as u, has_like as h\n" +
                    "where h.ID_user=? and h.ID_story=? and s.id=h.ID_story and u.id=h.ID_user");
            st.setInt(1, u.getId());
            st.setInt(2, id_story);
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

    public void addLike(User u, int id_story) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO has_like VALUES (?,?)");
            st.setInt(1, id_story);
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

    public void removeLike(User u, int id_story) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("DELETE FROM has_like WHERE ID_story = ? AND ID_user = ?");
            st.setInt(1,id_story);
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

    public void updateLikes(int id_story, int num) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE stories SET n_like = ? WHERE id = ?");
            st.setInt(1,num);
            st.setInt(2, id_story);
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

    public List<Story> getPublishedStories(User u) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM stories as s, users as u\n" +
                    "where s.ID_user=? and s.ID_user=u.id");
            st.setInt(1,u.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                stories.add(story);
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
        return stories;
    }

    public List<Story> getSavedStories(User u) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM library as l, stories as s, users as u, genres as g\n" +
                    "where l.ID_user=? and l.ID_story=s.id and s.ID_user=u.id and s.ID_genre=g.id");
            st.setInt(1,u.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setPlot(rs.getString("plot"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setN_likes(rs.getInt("n_like"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                stories.add(story);
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
        return stories;
    }

    public List<Story> getFollowingsStories(User u) throws SQLException {
        List<Story> stories = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT g.name,s.cover,s.id ,s.title, s.n_comments, s.n_like, s.n_savings, s.plot, u.username FROM follow as f, stories as s, genres as g, users as u\n" +
                    "where ID_followed_by=? and s.ID_user=f.ID_who_i_follow and s.ID_genre=g.id and s.ID_user=f.ID_who_i_follow and f.ID_who_i_follow=u.id ORDER BY dt DESC");
            st.setInt(1,u.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setId(rs.getInt("id"));
                story.setTitle(rs.getString("title"));
                story.setN_comments(rs.getInt("n_comments"));
                story.setN_savings(rs.getInt("n_savings"));
                story.setCover(rs.getBinaryStream("cover"));
                story.setN_likes(rs.getInt("n_like"));
                story.setPlot(rs.getString("plot"));
                User user = new User();
                user.setUsername(rs.getString("username"));
                story.setAuthor(user);
                Genre g = new Genre();
                g.setName(rs.getString("name"));
                story.setGenre(g);
                stories.add(story);
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
        return stories;
    }

    public void saveStory(User u, int id_storia) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO library values (?,?)");
            st.setInt(1, id_storia);
            st.setInt(2, u.getId());
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

    public boolean isSaved(User u, int id_story) throws SQLException {
        boolean l = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM library\n" +
                    "where ID_user=? and ID_story=?");
            st.setInt(1, u.getId());
            st.setInt(2, id_story);
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

    public void removeSavedStory(User u, int id_story) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("DELETE FROM library WHERE ID_story = ? AND ID_user = ?");
            st.setInt(1,id_story);
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

    public void updateSavings(int id_story, int num) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE stories SET n_savings = ? WHERE id = ?");
            st.setInt(1,num);
            st.setInt(2, id_story);
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

}
