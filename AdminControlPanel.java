package MiniTwitter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Here I implement the Singleton class to manage users and user groups;
 * Then I created methods to calculate various metrics
 */

public class AdminControlPanel {
    private static AdminControlPanel instance;
    private List<User> users;
    private List<UserGroup> groups;

    private AdminControlPanel() {
        users=new ArrayList<>();
        groups=new ArrayList<>();
    }

    public static AdminControlPanel getInstance() {
        if (instance==null) {
            instance=new AdminControlPanel();
        }
        return instance;
    }

    //Below we have a series of code, which adds new users, user groups, it also returns the amount of users and user groups

    public void addUser(User user) {
        users.add(user);
    }

    public void addGroup(UserGroup group) {
        groups.add(group);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public int getTotalUsers() {
        return users.size();
    }

    public int getTotalGroups() {
        return groups.size();
    }

    public int getTotalTweets() {
        int totalTweets=0;
        for (User user : users) {
            totalTweets += user.getNewsFeed().size();
        }
        return totalTweets;
    }

    //Calculation for Positive tweets (Honestly this feature would be at 0% for modern twitter XD)
    public double getPositiveTweetPercentage() {
        int totalTweets=0;
        int positiveTweets=0;
        for (User user : users) {
            List<String> newsFeed=user.getNewsFeed();
            totalTweets += newsFeed.size();
            positiveTweets += countPositiveTweets(newsFeed);
        }
        return (totalTweets==0) ? 0 : (double) positiveTweets/totalTweets * 100;
    }

    private int countPositiveTweets(List<String> tweets) {
        String[] positiveWords={"good", "great", "excellent"};
        int count=0;
        for (String tweet : tweets) {
            for (String word : positiveWords) {
                if (tweet.contains(word)) {
                    count++;
                }
            }
        }
        return count;
    }


    public String validateIDs() {
        Set<String> ids = new HashSet<>();
        StringBuilder result = new StringBuilder();
        boolean allValid = true;

        for (User user : users) {
            if (!ids.add(user.getId()) || user.getId().contains(" ")) {
                allValid = false;
                result.append("Invalid User ID: ").append(user.getId()).append("\n");
            }
        }

        for (UserGroup group : groups) {
            if (!ids.add(group.getId()) || group.getId().contains(" ")) {
                allValid = false;
                result.append("Invalid Group ID: ").append(group.getId()).append("\n");
            }
        }

        if (allValid) {
            result.append("All IDs are valid.");
        }

        return result.toString();
    }

    // Portion for last update time
    public User getLastUpdatedUser() {
        User lastUpdatedUser=null;
        long lastUpdateTime=0;

        for (User user : users) {
            if (user.getLastUpdateTime() > lastUpdateTime) {
                lastUpdatedUser=user;
                lastUpdateTime=user.getLastUpdateTime();
            }
        }

        return lastUpdatedUser;
    }
}



