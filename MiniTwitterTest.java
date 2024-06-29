package MiniTwitter;

import javax.swing.*;

public class MiniTwitterTest {

    /**
    * Here we have our "main" class to test our the AdminPanel GUI, 
    I inputted some test group and blank data to make sure everything is running good
    Then we launch the GUI within the main
    */
    
 
    public static void main(String[] args) {
        
        testAdminControlPanel();
        testUser();
        SwingUtilities.invokeLater(() -> new AdminControlPanelGUI().getFrame().setVisible(true));
    }

    public static void testAdminControlPanel() {
        AdminControlPanel admin=AdminControlPanel.getInstance();

        // Here we Test adding Users 
        User user1=new User("Joe");
        admin.addUser(user1);
        assert admin.getUsers().contains(user1) : "User1 should be in the user list";

        // Here we have the test Group, again, with some filler data
        UserGroup group1=new UserGroup("Group1");
        admin.addGroup(group1);
        assert admin.getGroups().contains(group1) : "Group1 should be in the group list";

        // Finally, testing totally users than further total groups
        int initialUserCount= admin.getTotalUsers();
        User user2= new User("Ben");
        admin.addUser(user2);
        assert admin.getTotalUsers()==initialUserCount + 1 : "Total user count should have increased by 1";

        int initialGroupCount=admin.getTotalGroups();
        UserGroup group2= new UserGroup("Group2");
        admin.addGroup(group2);
        assert admin.getTotalGroups() ==initialGroupCount + 1 : "Total group count should have increased by 1";

    }

    public static void testUser() {
        // Here I tried to mimic actual Twitter but it is pretty iffy but I tried :/
        User user=new User("Joe");
        user.postTweet("Hello World");
        assert user.getNewsFeed().contains("Hello World") : "News feed should contain the tweet 'Hello World'";

        User user1= new User("Joe");
        User user2= new User("Ben");
        user1.follow(user2);
        assert user2.getFollowers().contains(user1) : "Ben's follow Joe";

        user2.postTweet("Hello Followers");
        assert user1.getNewsFeed().contains("Hello Followers") : "Joe's news feed should be 'Hello Followers'";

        
    }
}
