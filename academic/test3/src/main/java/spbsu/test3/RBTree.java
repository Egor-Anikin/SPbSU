package spbsu.test3;

public class RBTree<T extends Comparable, E> {
    private Element hade;


    public RBTree() {
        hade = null;
    }

    public void add(T key, E info) {
        Element node = find(key);

        if (node == null) {
            hade = new Element(info, key, null);
            hade.red = false;
            return;
        } else if (node.key.equals(key)) {
            return;
        } else if (node.key.compareTo(key)<0) {
            node.right = new Element(info, key, node);
            node = node.right;
        } else {
            node.left = new Element(info, key, node);
            node = node.left;
        }

        addBalans(node);
    }

    public void remove(T key) {
        Element node = find(key);
        Element next;

        if (node == null || node.key != key) {
            return;
        }
        if (node.parent == null) {
            hade = null;
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
            removeBalans(node);
        }
        if (next != null) {
            node.key = next.key;
            node.info = next.info;
            node.right = next.right;
            node.left = next.left;
        } else if (node.parent.left.equals(node)) {
            node.parent.left = null;
        } else if (node.parent.right.equals(node)) {
            node.parent.right = null;
        }
    }

    public void see(T key) {
        Element node = find(key);
        if (node == null || !node.key.equals(key)) {
            System.out.println("Элемент не найден");
        } else {
            System.out.println("info = " + node.info);
        }
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
        return node.parent.left.equals(node) ? node.parent.right : node.parent.left;
    }

    private void rotateLeft(Element n) { //    p                      p
        Element node = n.right;     //        n                   t
        node.parent = n.parent;     //     C    t               n     B
        if (n.parent != null)       //        A     B       C      A
        {
            if (n.parent.left.equals(n)) {
                n.parent.left = node;
            } else {
                n.parent.right = node;
            }
        }

        n.right = node.left;
        if (node.left != null) {
            node.left.parent = n;
        }

        n.parent = node;
        node.left = n;
    }

    private void rotateRight(Element n) { //    p               p
        Element node = n.left;     //        n               t
        node.parent = n.parent;     //     t    C         A    n
        if (n.parent != null)       //  A     B              B    C
        {
            if (n.parent.left.equals(n)) {
                n.parent.left = node;
            } else {
                n.parent.right = node;
            }
        }

        n.left = node.right;
        if (node.right != null) {
            node.right.parent = n;
        }

        n.parent = node;
        node.right = n;
    }

    private Element find(T key) {
        if (hade == null) {
            return null;
        } else {
            Element node = hade;

            while (node.left != null || node.right != null) {
                if (node.key.compareTo(key) < 0 && node.right != null) {
                    node = node.right;
                } else if (node.key.compareTo(key) > 0 && node.left != null) {
                    node = node.right;
                } else {
                    break;
                }
            }

            return node;
        }
    }

    private void addBalans(Element node) {
        Element uncle = uncle(node);
        Element grad = grandfather(node);

        if (node.parent == null) {
            node.red = false;
        } else if (!isRed(node.parent)) {
            return;
        } else if (uncle != null && isRed(uncle)) {
            node.parent.red = false;
            uncle.red = false;
            grad.red = true;
            addBalans(grad);
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

    private void removeBalans(Element node) {
        if (isRed(node) || node.parent == null) {
            return;
        }
        Element bro = brother(node);
        if (isRed(bro)) {
            node.parent.red = true;
            bro.red = false;
            if (node.parent.left.equals(node)) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
        }
        bro = brother(node);
        if (!isRed(node.parent) && !isRed(bro) && !isRed(bro.right) && !isRed(bro.left)) {
            bro.red = true;
            removeBalans(node.parent);
        } else if (isRed(node.parent) && !isRed(bro) && !isRed(bro.right) && !isRed(bro.left)) {
            node.parent.red = false;
            bro.red = true;
        } else if (!isRed(bro)) {
            if (node.parent.left.equals(node) && isRed(bro.left) && !isRed(bro.right)) {
                bro.red = true;
                bro.left.red = false;
                rotateRight(bro);
            } else if (node.parent.right.equals(node) && isRed(bro.right) && !isRed(bro.left)) {
                bro.red = true;
                bro.right.red = false;
                rotateLeft(bro);
            }

            bro = brother(node);
            if (node.parent.left.equals(node)) {
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

    private class Element<T extends Comparable, E> implements Comparable {
        public E info;
        public T key;
        public boolean red;
        public Element parent;
        public Element right;
        public Element left;


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

