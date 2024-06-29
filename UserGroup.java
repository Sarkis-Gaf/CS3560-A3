package MiniTwitter;

import java.util.ArrayList;
import java.util.List;

public class UserGroup implements TwitterComponent {
    private String id;
    private List<TwitterComponent> members;
    private long creationTime;

    public UserGroup(String id) {
        this.id = id;
        this.members = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.members = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<TwitterComponent> getMembers() {
        return members;
    }

    public void addMember(TwitterComponent member) {
        members.add(member);
    }
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public void accept(Visitor visitor) {
        for (TwitterComponent member : members) {
            member.accept(visitor);
        }
        visitor.visit(this);
    }
}


