package dao;

import bean.Chapter;
import bean.Story;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {
    private DataSource dataSource;

    public ChapterDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Chapter addChapter(String title, String content, int n_chap, Story story) throws SQLException {
        Chapter ch = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO chapters (title,content,n_chap,ID_story) values (?,?,?,?)");
            st.setString(1, title);
            st.setString(2, content);
            st.setInt(3, n_chap);
            st.setInt(4, story.getId());
            st.executeUpdate();
            ch = new Chapter();
            ch.setTitle(title);
            ch.setContent(content);
            ch.setNumber(n_chap);
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
        return ch;
    }

    public List<Chapter> getAllChapters(Story story) throws SQLException {
        List<Chapter> chapters = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM chapters as c, stories as s\n" +
                    "where s.id=c.ID_story and s.id=?");
            st.setInt(1,story.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Chapter chap = new Chapter();
                chap.setNumber(rs.getInt("n_chap"));
                chap.setContent(rs.getString("content"));
                chap.setTitle(rs.getString("title"));
                chapters.add(chap);
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
        return chapters;
    }

    public Chapter getChapter(int id, int num) throws SQLException {
        Chapter ch = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM chapters as c, stories as s\n" +
                    "where s.id=c.ID_story and s.id=? and c.n_chap=?");
            st.setInt(1,id);
            st.setInt(2,num);
            rs = st.executeQuery();
            while (rs.next()) {
                ch = new Chapter();
                ch.setNumber(rs.getInt("n_chap"));
                ch.setContent(rs.getString("content"));
                ch.setTitle(rs.getString("title"));
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
        return ch;
    }
}
