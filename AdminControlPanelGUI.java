package MiniTwitter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * Took me awhile, but wanted to just make stuff simple, we have the GUI for AdminControlPanel
 * All of this allows the admin to add users and groups, and view metrics.
 */

public class AdminControlPanelGUI {
    private JFrame frame;
    private JTree userTree;
    private JTextArea userIdInput;
    private JButton addUserButton;
    private JTextArea groupIdInput;
    private JButton addGroupButton;
    private JButton totalUsersButton;
    private JButton totalGroupsButton;
    private JButton totalTweetsButton;
    private JButton positiveTweetsButton;
    private JButton openUserViewButton;
    private JButton validateIDsButton;
    private JButton lastUpdatedUserButton;

    public AdminControlPanelGUI() {
        frame=new JFrame("Admin Control Panel");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initializing the layout and buttons/components 
        userTree=new JTree();
        userIdInput=new JTextArea();
        addUserButton=new JButton("Add User");
        groupIdInput=new JTextArea();
        addGroupButton=new JButton("Add Group");
        totalUsersButton=new JButton("Show Total Users");
        totalGroupsButton=new JButton("Show Total Groups");
        totalTweetsButton=new JButton("Show Total Tweets");
        positiveTweetsButton=new JButton("Show Positive Tweet Percentage");
        openUserViewButton=new JButton("Open User View");
        validateIDsButton = new JButton("Validate IDs");
        lastUpdatedUserButton = new JButton("Show Last Updated User");

        JPanel panel=new JPanel(new BorderLayout());
        JPanel inputPanel=new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(userIdInput);
        inputPanel.add(new JLabel("Group ID:"));
        inputPanel.add(groupIdInput);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(userTree), BorderLayout.CENTER);

        JPanel buttonPanel=new JPanel(new GridLayout(2, 4));
        buttonPanel.add(addUserButton);
        buttonPanel.add(addGroupButton);
        buttonPanel.add(totalUsersButton);
        buttonPanel.add(totalGroupsButton);
        buttonPanel.add(totalTweetsButton);
        buttonPanel.add(positiveTweetsButton);
        buttonPanel.add(openUserViewButton);
        buttonPanel.add(validateIDsButton);
        buttonPanel.add(lastUpdatedUserButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        addUserButton.addActionListener(e -> addUser());
        addGroupButton.addActionListener(e -> addGroup());
        totalUsersButton.addActionListener(e -> showTotalUsers());
        totalGroupsButton.addActionListener(e -> showTotalGroups());
        totalTweetsButton.addActionListener(e -> showTotalTweets());
        positiveTweetsButton.addActionListener(e -> showPositiveTweets());
        openUserViewButton.addActionListener(e -> openUserView());
        validateIDsButton.addActionListener(e -> validateIDs());
        lastUpdatedUserButton.addActionListener(e -> showLastUpdatedUser());


        updateTreeView();
    }

    
    public JFrame getFrame() {
        return frame;
    }

    //Wanted to make some error/invalid scenarios to mimic actual software development 
    private void addUser() {
        String userId=userIdInput.getText().trim();
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (User user : AdminControlPanel.getInstance().getUsers()) {
            if (user.getId().equals(userId)) {
                JOptionPane.showMessageDialog(frame, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        User newUser =new User(userId);
        AdminControlPanel.getInstance().addUser(newUser);
        updateTreeView();
    }

    private void addGroup() {
        String groupId=groupIdInput.getText().trim();
        if (groupId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Group ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (UserGroup group : AdminControlPanel.getInstance().getGroups()) {
            if (group.getId().equals(groupId)) {
                JOptionPane.showMessageDialog(frame, "Group ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        UserGroup newGroup =new UserGroup(groupId);
        AdminControlPanel.getInstance().addGroup(newGroup);
        updateTreeView();
    }

    private void showTotalUsers() {
        int totalUsers=AdminControlPanel.getInstance().getTotalUsers();
        JOptionPane.showMessageDialog(frame, "Total Users: " + totalUsers);
    }

    private void showTotalGroups() {
        int totalGroups=AdminControlPanel.getInstance().getTotalGroups();
        JOptionPane.showMessageDialog(frame, "Total Groups: " + totalGroups);
    }

    private void showTotalTweets() {
        int totalTweets=AdminControlPanel.getInstance().getTotalTweets();
        JOptionPane.showMessageDialog(frame, "Total Tweets: " + totalTweets);
    }

    private void showPositiveTweets() {
        double positivePercentage= AdminControlPanel.getInstance().getPositiveTweetPercentage();
        JOptionPane.showMessageDialog(frame, "Positive Tweets: " + positivePercentage + "%");
    }

    private void openUserView() {
        String selectedUserId= userIdInput.getText().trim();
        if (selectedUserId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (User user : AdminControlPanel.getInstance().getUsers()) {
            if (user.getId().equals(selectedUserId)) {
                new UserViewGUI(user);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Username does not exist", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void validateIDs() {
        String result = AdminControlPanel.getInstance().validateIDs();
        JOptionPane.showMessageDialog(frame, result);
    }

    private void showLastUpdatedUser() {
        User lastUpdatedUser = AdminControlPanel.getInstance().getLastUpdatedUser();
        if (lastUpdatedUser != null) {
            JOptionPane.showMessageDialog(frame, "Last Updated User ID: " + lastUpdatedUser.getId());
        } else {
            JOptionPane.showMessageDialog(frame, "No users have updated yet.");
        }
    }


    private void updateTreeView() {
        DefaultTreeModel model=(DefaultTreeModel) userTree.getModel();
        DefaultMutableTreeNode root= new DefaultMutableTreeNode("Root");
        for (UserGroup group : AdminControlPanel.getInstance().getGroups()) {
            DefaultMutableTreeNode groupNode= new DefaultMutableTreeNode(group.getId());
            addGroupNodes(group, groupNode);
            root.add(groupNode);
        }
        for (User user : AdminControlPanel.getInstance().getUsers()) {
            root.add(new DefaultMutableTreeNode(user.getId()));
        }
        model.setRoot(root);
    }

    private void addGroupNodes(UserGroup group, DefaultMutableTreeNode groupNode) {
        for (TwitterComponent member : group.getMembers()) {
            if (member instanceof UserGroup) {
                DefaultMutableTreeNode subgroupNode = new DefaultMutableTreeNode(member.getId());
                addGroupNodes((UserGroup) member, subgroupNode);
                groupNode.add(subgroupNode);
            } else if (member instanceof User) {
                groupNode.add(new DefaultMutableTreeNode(member.getId()));
            }
            
        }
    }
}
