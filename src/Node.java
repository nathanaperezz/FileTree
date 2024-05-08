import java.sql.Array;
import java.util.ArrayList;

public class Node {

    String name;
    boolean isDirectory;
    ArrayList<Node> children = new ArrayList<Node>();

    Node(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
    }
}
