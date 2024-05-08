import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/* SD2 Module 1 Assignment
 *
 * Design and write a Java program to read the file system beneath a particular folder,
 * and store it in a tree data structure.
 *
 * Requirements:
 *    - Your program should be able to look at a folder and its tree of subfolders,
 *      and construct a tree data structure to represent it.
 *      Each node in your tree corresponds to a folder in the file system,
 *      and should contain the number of files, the total size of the files,
 *      the folder's name, and a list of child folders
 *    - Your program should read the name of the folder to scan, scan the folder's subtree,
 *      then output the tree in a way that shows the tree hierarchy
 *      (e.g., one line per tree node, and indent each node appropriately)
 *    - Hint: you should use recursion both in the scanning part of your program,
 *      and the part that outputs the tree
 *
 * Nathan Perez
 * Created May 6, 2024
 */


public class Tree {

    static Node root;

    public static Node AddNode(String name, Node parent) {

        Node newNode = new Node(name);

        //first node added becomes root node
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

            //System.out.println(spacesForDepth(depth) + " > " + path.getFileName());
            parent = AddNode(String.valueOf(path.getFileName()), parent);

            for (Path temPath : paths) {
                GetDir(temPath, parent);
            }
            //if file, simply add file to tree (no traverse)
        } else {
            //System.out.println(spacesForDepth(depth) + " -- " + path.getFileName());
            AddNode(String.valueOf(path.getFileName()), parent);
        }
    }

    public static String spacesForDepth(int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append("    ");
        }
        return builder.toString();
    }

    public static void PrintTree(Node node, int indent) {

        for(int i = 0; i < indent; i++) {
            System.out.print("   ");
        }

        System.out.println(node.name);

        indent++;

        for(int i = 0; i < node.children.size(); i++) {
            PrintTree(node.children.get(i), indent);
        }
    }
}