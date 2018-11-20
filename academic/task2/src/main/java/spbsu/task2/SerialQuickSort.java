package spbsu.task2;

/** Serial quick sort. */
public class SerialQuickSort<T extends Comparable> implements Sort<T> {
    @Override
    public void sort(T[] array) {
        qSort(array, 0, array.length - 1);
    }

    private void qSort(T[] array, int left, int right) {
        if (right <= left) {
            return;
        }

        int i = left;
        int j = right;
        T middle = array[(i + j) / 2];

        while (i <= j) {
            while (i <= j && array[i].compareTo(middle) < 0) {
                i++;
            }

            while (i <= j && array[j].compareTo(middle) > 0) {
                j--;
            }

            if (i <= j && array[j].compareTo(array[i]) <= 0) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        qSort(array, i, right);
        qSort(array, left, j);
    }

    private void swap(T[] array, int i, int j) {
        T swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }
}
