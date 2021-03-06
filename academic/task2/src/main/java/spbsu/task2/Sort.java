package spbsu.task2;

/** Sorting Algorithms interface. */
public interface Sort<T extends Comparable> {
    /** Sorting function. */
    void sort(T[] array);
}
