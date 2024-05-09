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

    /**
     * Adds a node to the tree
     * @param name the file name
     * @param isDirectory true = directory/folder, false = file
     * @param size the size in bytes
     * @param parent the parent node
     * @return the newly added node
     */
    public static Node AddNode(String name, boolean isDirectory, long size, Node parent) {

        Node newNode = new Node(name, isDirectory, size);

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

    /**
     * Recursively stores file system in a tree
     * @param path The file system to use
     * @param parent Parent node (Null for root node).
     * @throws IOException
     */
    public static void GetDir(Path path, Node parent) throws IOException {

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        //long size = attr.size();
        long size = GetSize(path, attr);

        //if directory, list files, and traverse down inside each of those
        if (attr.isDirectory()) {

            DirectoryStream<Path> paths = Files.newDirectoryStream(path);

            parent = AddNode(String.valueOf(path.getFileName()), true, size, parent);

            for (Path temPath : paths) {
                GetDir(temPath, parent);
            }
        }
        //if only a file, simply add file to tree (no traverse)
        else {
            AddNode(String.valueOf(path.getFileName()),false, size, parent);
        }
    }

    /**
     * Recursively gets the size of a node by adding the sizes of its children.
     * @param path The file system to use
     * @param attr The file you want to calculate the size of
     * @return The size in bytes of the given file
     * @throws IOException
     */
    private static long GetSize(Path path, BasicFileAttributes attr) throws IOException {

        if(attr.isDirectory()) {

            long totalSize = 0;

            DirectoryStream<Path> paths = Files.newDirectoryStream(path);

            for (Path tempPath : paths) {
                BasicFileAttributes tempAttr = Files.readAttributes(tempPath, BasicFileAttributes.class);
                totalSize += GetSize(tempPath, tempAttr);;
            }
            return totalSize;

        } else return attr.size();
    }

    /**
     * Recursively prints tree with indents for clarity.
     * @param node The head or subhead that you want to print
     * @param indent Records indentation throughout the recursive call. If using root, indent should be 0
     */
    public static void PrintTree(Node node, int indent) {

        for(int i = 0; i < indent; i++) {
            System.out.print("   ");
        }

        //uses > for directory and - for file
        if(node.isDirectory) {
            //for proper grammar.
            if(node.children.size() == 1) {
                System.out.println("> " + node.name + " (" + node.children.size() + " file, " + node.size + " bytes)");
            }
            else {
                System.out.println("> " + node.name + " (" + node.children.size() + " files, " + node.size + " bytes)");
            }
        }
        else{
            System.out.println("- " + node.name + " (" + node.size + " bytes)");
        }

        indent++;

        for(int i = 0; i < node.children.size(); i++) {
            PrintTree(node.children.get(i), indent);
        }
    }
}