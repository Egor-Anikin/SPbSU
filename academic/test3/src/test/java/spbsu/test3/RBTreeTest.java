package spbsu.test3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RBTreeTest {
    /** Creation test of RBTree class. */
    @Test
    public void creationTest() {
        RBTree<Integer, String> tree = new RBTree<>();
    }

    /** Function test add and remove. */
    @Test
    public void addRemovTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.add(6, "test");
        tree.add(1, "test");
        tree.add(7, "test");
        tree.add(5, "test");
        tree.add(9, "test");
        tree.add(3, "test");
        tree.add(2, "test");
        tree.add(8, "test");
        tree.add(4, "test");

        for (int i=1; i < 10; i++) {
            assertEquals("Add isn't work!", "test", tree.show(i));
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
            assertEquals("Remove isn't work!", null, tree.show(i));
        }
    }

    /** Empty tree test. */
    @Test
    public void nullTest() {
        RBTree<Integer, String> tree = new RBTree<>();
        assertEquals("Find nonexisten element", null, tree.show(6));
        tree.remove(6);
    }

    /** String as key. */
    @Test
    public void strigTest() {
        RBTree<String, String> tree = new RBTree<>();
        tree.add("ab", "test");
        tree.add("a", "test");
        tree.add("bca", "test");

        assertEquals("Not found element", "test", tree.show("ab"));
        assertEquals("Not found element", "test", tree.show("a"));
        assertEquals("Not found element", "test", tree.show("bca"));
        
        tree.remove("ab");
        tree.remove("a");
        tree.remove("bca");
    }

    /** Rotate left test. */
    @Test
    public void rotateLeftTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.add(6, "test");
        tree.add(3, "test");
        tree.add(7, "test");
        tree.add(4, "test");
        tree.add(5, "test");
    }

    /** Rotate right test. */
    @Test
    public void rotateRightTest() {
        RBTree<Integer, String> tree = new RBTree<>();

        tree.add(6, "test");
        tree.add(3, "test");
        tree.add(7, "test");
        tree.add(2, "test");
        tree.add(1, "test");
    }

}

