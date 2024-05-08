import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/* SD2 Module 1 Assignment
 * Nathan Perez
 * Created May 6, 2024
 */


public class Tree {

    static Node root;

    public static Node AddNode(String name, boolean isDirectory, Node parent) {

        Node newNode = new Node(name, isDirectory);

        //first node added always becomes root node
        if (parent == null) {
            root = newNode;
        }
        //all other nodes are assigned as children to the given parent node
        else {
            parent.children.add(newNode);
            newNode.name = name;
        }

        return newNode;
    }

    public static void main(String[] args) throws IOException {

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        GetDir(currentPath, null);

        PrintTree(root, 0);
    }

    public static void GetDir(Path path, Node parent) throws IOException {

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        //if directory, list files, and traverse down inside each of those
        if (attr.isDirectory()) {

            DirectoryStream<Path> paths = Files.newDirectoryStream(path);

            parent = AddNode(String.valueOf(path.getFileName()), true, parent);

            for (Path temPath : paths) {
                GetDir(temPath, parent);
            }
        }
        //if file, simply add file to tree (no traverse)
        else {
            AddNode(String.valueOf(path.getFileName()),false, parent);
        }
    }

    public static void PrintTree(Node node, int indent) {

        for(int i = 0; i < indent; i++) {
            System.out.print("   ");
        }

        if(node.isDirectory) {
            //this is done for proper grammar only.
            if(node.children.size() == 1) {
                System.out.println("> " + node.name + " (" + node.children.size() + " file)");
            }
            else {
                System.out.println("> " + node.name + " (" + node.children.size() + " files)");
            }
        }
        else{
            System.out.println("- " + node.name);
        }

        //System.out.println(node.name);

        indent++;

        for(int i = 0; i < node.children.size(); i++) {
            PrintTree(node.children.get(i), indent);
        }
    }
}