package spbsu.test3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RBTreeTest {
    /** Creation test of RBTree class. */
    @Test
    public void creationTest() {
        RBTree<Integer, String> tree = new RBTree<>();
    }

    /** Function test add and remove.*/
    @Test
    public void addRemovTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.add(6, "asdfghj");
        tree.add(1, "asdfghj");
        tree.add(7, "asdfghj");
        tree.add(5, "asdfghj");
        tree.add(9, "asdfghj");
        tree.add(3, "asdfghj");
        tree.add(2, "asdfghj");
        tree.add(8, "asdfghj");
        tree.add(4, "asdfghj");
        for (int i=1; i < 10; i++) {
            assertEquals("find isn't work!", "asdfghj",tree.show(i));
        }

        tree.remove(6);
        tree.remove(1);
        tree.remove(4);
        tree.remove(3);
        tree.remove(2);
        tree.remove(8);
        tree.remove(9);
        tree.remove(1);
        tree.remove(5);

        for (int i=1; i < 10; i++) {
            assertEquals("find isn't work!", null,tree.show(i));
        }

    }

    /** Empty tree test. */
    @Test
    public void nullTest() {
        RBTree<Integer, String> tree = new RBTree<>();
        assertEquals("find nonexisten element", null,tree.show(6));
        tree.remove(6);
    }

    /** String as key. */
    @Test
    public void strigTest() {
        RBTree<String, String> tree = new RBTree<>();
        tree.add("ab", "asdfghj");
        tree.add("a", "asdfghj");
        tree.add("bca", "asdfghj");
        assertEquals("not found element", "asdfghj",tree.show("ab"));
        assertEquals("not found element", "asdfghj",tree.show("a"));
        assertEquals("not found element", "asdfghj",tree.show("bca"));
        tree.remove("ab");
        tree.remove("a");
        tree.remove("bca");
    }

    /** Rotate left test. */
    @Test
    public void rotateLeftTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.remove(6);
        tree.remove(3);
        tree.remove(7);
        tree.remove(4);
        tree.remove(5);
    }

    /** Rotate right test. */
    @Test
    public void rotateRightTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.remove(6);
        tree.remove(3);
        tree.remove(7);
        tree.remove(2);
        tree.remove(1);
    }

}

