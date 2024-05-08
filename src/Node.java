import java.sql.Array;
import java.util.ArrayList;

public class Node {

    String name;
    ArrayList<Node> children = new ArrayList<Node>();

    Node(String name) {
        this.name = name;
    }
}
