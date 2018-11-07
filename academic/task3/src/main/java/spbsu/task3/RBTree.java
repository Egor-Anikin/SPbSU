package spbsu.task3;

/** Realise Red-Black Tree. */
public class RBTree<T extends Comparable, E> {
    private Element head;

    /**
     * Add element to Red-Black Tree.
     *
     * @param key Key.
     * @param info Information.
     * */
    public void add(T key, E info) {
        Element node = find(key);

        if (node == null) {
            head = new Element(info, key, null);
            head.red = false;
            return;
        } else if (node.key.equals(key)) {
            return;
        } else if (node.key.compareTo(key) < 0) {
            node.right = new Element(info, key, node);
            node = node.right;
        } else {
            node.left = new Element(info, key, node);
            node = node.left;
        }

        addBalance(node);
    }

    /**
     * Remove element from Red-Black Tree.
     *
     * @param key Key.
     * */
    public void remove(T key) {
        Element node = find(key);
        Element next;

        if (node == null || node.key != key) {
            return;
        }
        if (node.parent == null) {
            head = null;
            return;
        }
        if (node.right != null && node.left != null) {
            next = node.right;
            while (next.left != null) {
                next = next.left;
            }
            node.key = next.key;
            node.info = next.info;
            node = next;
        }
        next = child(node);
        if (next == null) {
            removeBalance(node);
        }
        if (next != null) {
            node.key = next.key;
            node.info = next.info;
            node.right = next.right;
            node.left = next.left;
        } else if (node.equals(node.parent.left)) {
            node.parent.left = null;
        } else if (node.equals(node.parent.right)) {
            node.parent.right = null;
        }
    }

    /**
     * Show a Red-Black Tree element.
     *
     * @param key Key.
     *
     * @return Return element information.
     * */
    public E show(T key) {
        Element node = find(key);
        return node == null || !node.key.equals(key) ? null : node.info;
    }


    private Element grandfather(Element node) {
        return node != null && node.parent != null? node.parent.parent : null;

    }

    private Element uncle(Element node) {
        return node != null && node.parent != null && node.parent.parent != null ?
                (node.parent.key.compareTo(node.parent.parent.key) > 0 ? node.parent.parent.left
                        : node.parent.parent.right) : null;
    }

    private Element child(Element node) {
        return node.right != null ? node.right : node.left;
    }

    private Element brother(Element node) {
        return node.equals(node.parent.left) ? node.parent.right : node.parent.left;
    }

    private void rotateLeft(Element elem) { //    p                      p
        Element node = elem.right;     //        n                   t
        node.parent = elem.parent;     //     C    t               n     B
        if (elem.parent != null) {       //        A     B       C      A
            if (elem.equals(elem.parent.left)) {
                elem.parent.left = node;
            } else {
                elem.parent.right = node;
            }
        }

        elem.right = node.left;
        if (node.left != null) {
            node.left.parent = elem;
        }

        elem.parent = node;
        node.left = elem;
    }

    private void rotateRight(Element elem) { //    p               p
        Element node = elem.left;     //        n               t
        node.parent = elem.parent;     //     t    C         A    n
        if (elem.parent != null) {       //  A     B              B    C
            if (elem.equals(elem.parent.left)) {
                elem.parent.left = node;
            } else {
                elem.parent.right = node;
            }
        }

        elem.left = node.right;
        if (node.right != null) {
            node.right.parent = elem;
        }

        elem.parent = node;
        node.right = elem;
    }

    private Element find(T key) {
        if (head == null) {
            return null;
        } else {
            Element node = head;

            while (node.left != null || node.right != null) {
                if (node.key.compareTo(key) < 0 && node.right != null) {
                    node = node.right;
                } else if (node.key.compareTo(key) > 0 && node.left != null) {
                    node = node.left;
                } else {
                    break;
                }
            }

            return node;
        }
    }

    private void addBalance(Element node) {
        Element uncle = uncle(node);
        Element grad = grandfather(node);

        if (node.parent == null) {
            node.red = false;
        }else if(!isRed(node.parent)) {
            return;
        } else if (isRed(uncle)) {
            node.parent.red = false;
            uncle.red = false;
            grad.red = true;
            addBalance(grad);
        } else {
            if (node.equals(node.parent.right) && node.parent.equals(grad.left)) {
                rotateLeft(node.parent);
                node = node.left;
            } else if (node.equals(node.parent.left) && node.parent.equals(grad.right)) {
                rotateRight(node.parent);
                node = node.right;
            }

            grad = grandfather(node);
            node.parent.red = false;
            grad.red = true;
            if (node.equals(node.parent.left) && node.parent.equals(grad.left)) {
                rotateRight(grad);
            } else {
                rotateLeft(grad);
            }
        }
    }

    private void removeBalance(Element node) {
        if (isRed(node) || node.parent == null) {
            return;
        }
        Element bro = brother(node);
        if (isRed(bro)) {
            node.parent.red = true;
            bro.red = false;
            if (node.equals(node.parent.left)) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
        }
        bro = brother(node);
        if (!isRed(node.parent) && !isRed(bro) && !isRed(bro.right) && !isRed(bro.left)) {
            bro.red = true;
            removeBalance(node.parent);
        } else if (isRed(node.parent) && !isRed(bro) && !isRed(bro.right) && !isRed(bro.left)) {
            node.parent.red = false;
            bro.red = true;
        } else if (!isRed(bro)) {
            if (node.equals(node.parent.left) && isRed(bro.left) && !isRed(bro.right)) {
                bro.red = true;
                bro.left.red = false;
                rotateRight(bro);
            } else if (node.equals(node.parent.right) && isRed(bro.right) && !isRed(bro.left)) {
                bro.red = true;
                bro.right.red = false;
                rotateLeft(bro);
            }

            bro = brother(node);
            if (node.equals(node.parent.left)) {
                bro.right.red = false;
                rotateLeft(node.parent);
            } else {
                bro.left.red = false;
                rotateRight(node.parent);
            }

        }
    }

    private boolean isRed(Element node) {
        return node != null && node.red;
    }

    private class Element implements Comparable {
        public E info;
        public T key;
        public boolean red;
        public Element parent;
        public Element right;
        public Element left;

        /**
         * Add element to Red-Black Tree.
         *
         * @param info Information.
         * @param key Key.
         * @param parent Parent element.
         * */
        public Element(E info, T key, Element parent) {
            this.info = info;
            this.key = key;
            this.parent = parent;
            this.right = null;
            this.left = null;
            this.red = true;
        }

        @Override
        public int compareTo(Object o) {
            return this.key.compareTo(((Element) o).key);
        }
    }
}
