package dao;

import bean.Comment;
import bean.Story;
import bean.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private DataSource dataSource;

    public CommentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Comment addComment(User u, int id_story, String comment, LocalDate date) throws SQLException {
        Comment cm = null;
        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        ResultSet rs = null;
        int id=0;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO comments (content, data_cm, ID_user, ID_story) VALUES (?, now(), ?, ?)");
            st.setString(1, comment);
            st.setInt(2, u.getId());
            st.setInt(3, id_story);
            st.executeUpdate();

            st2 = con.prepareStatement("SELECT LAST_INSERT_ID()");
            rs = st2.executeQuery();
            while(rs.next())
                id=rs.getInt(1);

            cm = new Comment();
            cm.setUser(u);
            cm.setId(id);
            cm.setText(comment);
            cm.setDate(date);
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
        return cm;
    }

    public List<Comment> getAllComments(Story story) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT c.content, c.data_cm, u.username, u.avatar, c.id FROM comments as c, users as u where c.ID_story=? and c.ID_user=u.id ORDER BY data_cm");
            st.setInt(1,story.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setText(rs.getString("content"));
                c.setDate(rs.getDate("data_cm").toLocalDate());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAvatar(rs.getBinaryStream("avatar"));
                c.setUser(user);

                comments.add(c);
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
        return comments;
    }

    public void updateComments(int id_story, int num) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("UPDATE stories SET n_comments = ? WHERE id = ?");
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
