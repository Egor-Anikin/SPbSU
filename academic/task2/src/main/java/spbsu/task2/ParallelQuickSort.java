package spbsu.task2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/** Parallel quick sort. */
public class ParallelQuickSort<T extends Comparable> implements Sort<T> {

    @Override
    public void sort(T[] array) {
        Parallel first = new Parallel<T>(array, 0, array.length - 1);
        new ForkJoinPool().invoke(first);
    }

    private class Parallel<T extends Comparable> extends RecursiveAction {
       private int right;
       private int left;
       private T[] array;

        protected Parallel (T[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        private  void swap(T[] array, int i, int j) {
            T swap = array[i];
            array[i] = array[j];
            array[j] = swap;
        }

        @Override
        protected void compute() {
            if (right <= left) {
                return;
            }

            int i = left;
            int j = right;
            T middle = array[(i + j) / 2];

            while (i <= j) {
                while (i <= j && array[i].compareTo(middle) < 0 ) {
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

            Parallel<T> cRight = new Parallel<T>(array, i, right);
            Parallel<T> cLeft = new Parallel<T>(array, left, j);

            cRight.fork();
            cLeft.compute();
            cRight.join();
        }
    }
}
