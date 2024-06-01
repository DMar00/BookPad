package dao;

import bean.Story;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {
    private DataSource dataSource;

    public TagDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addTag(String tag) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO tags (text_tag) values (?)");
            st.setString(1, tag);
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

    public void addTagToStory(Story story, String tag) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO has_tag (ID_story,text_tag) values (?,?)");
            st.setInt(1, story.getId());
            st.setString(2, tag);
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

    public boolean checkTag(String tag) throws SQLException {
        boolean c = false;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM tags where text_tag = ?");
            st.setString(1, tag);
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

    public List<String> getAllTags(Story story) throws SQLException{
        List<String> tags = new ArrayList<String>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * from has_tag where ID_story=?");
            st.setInt(1,story.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                String tag = rs.getString("text_tag");
                tags.add(tag);
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
        return tags;
    }

}
