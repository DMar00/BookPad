package dao;

import bean.Genre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    private DataSource dataSource;

    public GenreDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Genre> getAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM genres");
            rs = st.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("name"));
                genre.setDescription(rs.getString("description"));
                genres.add(genre);
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
        return genres;
    }

    public Genre getByName(String name) throws SQLException {
        Genre genre = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT * FROM genres where name = ?");
            st.setString(1,name);
            rs = st.executeQuery();
            while (rs.next()) {
                genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("name"));
                genre.setDescription(rs.getString("description"));
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
        return genre;
    }

}
