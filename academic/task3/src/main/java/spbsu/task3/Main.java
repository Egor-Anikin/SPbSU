package spbsu.task3;

/** Main class. */
public class Main {
    /** Main function. */
    public static void main(String[] args) {
        RBTree<Integer, String> Tree = new RBTree<>();

        Tree.add(6, "asdfghj");
        System.out.println("info :" + Tree.show(6));
        Tree.remove(6);
    }
}
