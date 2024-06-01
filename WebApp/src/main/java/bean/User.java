package bean;

import java.io.InputStream;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String about;
    private InputStream avatar;
    private List<User> followers;
    private List<User> followings;
    private List<Story> saved_stories;
    private List<Story> published_stories;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(InputStream avatar) {
        this.avatar = avatar;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public List<Story> getSaved_stories() {
        return saved_stories;
    }

    public void setSaved_stories(List<Story> saved_stories) {
        this.saved_stories = saved_stories;
    }

    public List<Story> getPublished_stories() {
        return published_stories;
    }

    public void setPublished_stories(List<Story> published_stories) {
        this.published_stories = published_stories;
    }

    public void addFollower(User u){
        this.followers.add(u);
    }

    public void addFollowing(User u){
        this.followings.add(u);
    }

    public void addStoryToLibrary(Story s){
        this.saved_stories.add(s);
    }

    public void addStoryToWritten(Story s){
        this.published_stories.add(s);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                ", avatar=" + avatar +
                ", followers=" + followers +
                ", followings=" + followings +
                ", saved_stories=" + saved_stories +
                ", published_stories=" + published_stories +
                '}';
    }

}
