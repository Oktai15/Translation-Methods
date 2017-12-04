import java.util.ArrayList;
import java.util.List;

public class Tree {
    private String node;
    private List<Tree> children;

    public Tree(String nodeName) {
        this(nodeName, new ArrayList<>());
    }

    public Tree(String node, List<Tree> children) {
        this.node = node;
        this.children = children;
    }

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }
}