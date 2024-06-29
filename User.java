package MiniTwitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we have the implementation of an individual user in the program
 * Implements TwitterComponent, Subject, and Observer.
 */
public class User implements TwitterComponent, Subject, Observer {
    private String id;
    private List<Observer> followers;
    private List<String> followings;
    private List<String> newsFeed;
    private UserViewGUI userViewGUI;
    private long creationTime;
    private long lastUpdateTime; 

   
    public User(String id) {
        this.id = id;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = creationTime;
    }

    
    public void setUserViewGUI(UserViewGUI userViewGUI) {
        this.userViewGUI = userViewGUI;
    }

    
    public UserViewGUI getUserViewGUI() {
        return userViewGUI;
    }

    
    public String getId() {
        return id;
    }

    
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    
    public List<String> getFollowings() {
        return followings;
    }

    
    public List<Observer> getFollowers() {
        return followers;
    }

    
    public int getFollowerCount() {
        return followers.size();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    //Set that allows another user to follow each other
    public void follow(User user) {
        followings.add(user.getId());
        user.addObserver(this);
        if (user.getUserViewGUI() != null) {
            user.getUserViewGUI().updateFollowerCount(user); //Update the GUI follower count
        }
    }

    //Posts a tweet and notifies followers
    
    public void postTweet(String tweet) {
        newsFeed.add(tweet);
        lastUpdateTime = System.currentTimeMillis();
        notifyObservers(tweet);
        if (userViewGUI != null) {
            userViewGUI.updateNewsFeed(this);
            userViewGUI.updateLastUpdateTime(this);
        }
    }

    //Adds an observer (follower)
    @Override
    public void addObserver(Observer observer) {
        followers.add(observer);
        if (userViewGUI != null) {
            userViewGUI.updateFollowerCount(this); 
        }
    }

   
    @Override
    public void removeObserver(Observer observer) {
        followers.remove(observer);
        if (userViewGUI != null) {
            userViewGUI.updateFollowerCount(this); 
        }
    }

    //Notifies all followers of a new tweet
    @Override
    public void notifyObservers(String tweet) {
        for (Observer follower : followers) {
            follower.update(tweet);
        }
    }

    //Updates the user's news feed with a new tweet
    @Override
    public void update(String tweet) {
        newsFeed.add(tweet);
        if (userViewGUI != null) {
            userViewGUI.updateNewsFeed(this); 
            userViewGUI.updateLastUpdateTime(this); //updated portion
        }
    }

    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}

