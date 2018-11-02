package spbsu.test3;

public class Main {
    /** Main function */
    public static void main(String[] args) {
        RBTree Tree = new RBTree();

        Tree.add(6, "asdfghj");
        Tree.see(6);
        Tree.remove(6);
    }
}
