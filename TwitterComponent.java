package MiniTwitter;

public interface TwitterComponent {
    String getId();
    void accept(Visitor visitor);
}
