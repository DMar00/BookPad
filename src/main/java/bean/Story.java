package bean;

import java.io.InputStream;
import java.util.List;

public class Story {
    private int id;
    private String title;
    private String plot;
    private InputStream cover;
    private int n_likes;
    private int n_comments;
    private int n_savings;
    private Genre genre;
    private User author;
    private List<Chapter> chapters;
    private List<Comment> comments;
    private List<String> tags;

    public Story() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public InputStream getCover() {
        return cover;
    }

    public void setCover(InputStream cover) {
        this.cover = cover;
    }

    public int getN_likes() {
        return n_likes;
    }

    public void setN_likes(int n_likes) {
        this.n_likes = n_likes;
    }

    public int getN_comments() {
        return n_comments;
    }

    public void setN_comments(int n_comments) {
        this.n_comments = n_comments;
    }

    public int getN_savings() {
        return n_savings;
    }

    public void setN_savings(int n_savings) {
        this.n_savings = n_savings;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String t){
        this.tags.add(t);
    }

    public void addComment(Comment c){
        this.comments.add(c);
    }

    public void addChapter(Chapter c){
        this.chapters.add(c);
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", plot='" + plot + '\'' +
                ", cover='" + cover + '\'' +
                ", n_likes=" + n_likes +
                ", n_comments=" + n_comments +
                ", n_savings=" + n_savings +
                ", genre=" + genre +
                ", author=" + author +
                ", chapters=" + chapters +
                ", comments=" + comments +
                ", tags=" + tags +
                '}';
    }
}
