import java.sql.Array;
import java.util.ArrayList;

public class Node {

    String name;
    boolean isDirectory;
    double size;
    ArrayList<Node> children = new ArrayList<Node>();

    Node(String name, boolean isDirectory, long size) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
    }
}
