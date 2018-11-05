package spbsu.test2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class QuickSortTest {
    /** Creation test of serial and parallel realization class. */
    @Test
    public void creationTest() {
        QuickSort<Integer> serial = new SerialQuickSort<Integer>();
        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();
    }

    /** Creation test of serial and parallel realization class. */
    @Test
    public void parallelSortTest() {
        Integer[] array = randGenerator(1000);
        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();
    }

    private static Integer[] randGenerator(Integer arrayLenght) {
        Random rand = new Random();
        Integer[] array = new Integer[arrayLenght];

        for (int i = 0; i < arrayLenght; i++) {
            array[i] = rand.nextInt();
        }

        return array;
    }
}
