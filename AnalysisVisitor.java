package MiniTwitter;

import java.util.List;

public class AnalysisVisitor implements Visitor {
    private int userCount;
    private int groupCount;
    private int tweetCount;
    private int positiveTweetCount;

    @Override
    public void visit(User user) {
        userCount++;
        tweetCount+= user.getNewsFeed().size();
        positiveTweetCount+= countPositiveTweets(user.getNewsFeed());
    }

    @Override
    public void visit(UserGroup group) {
        groupCount++;
    }

    private int countPositiveTweets(List<String> tweets) {
        String[] positiveWords={"good", "great", "excellent"};
        int count = 0;
        for (String tweet : tweets) {
            for (String word : positiveWords) {
                if (tweet.contains(word)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public int getPositiveTweetCount() {
        return positiveTweetCount;
    }
}

