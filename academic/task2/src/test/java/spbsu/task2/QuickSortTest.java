package spbsu.task2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

/** Sorting test class. */
public class QuickSortTest {
    private final int ARRAY_LENGHT = 1000000;
    private final int SIZE = 16;

    /** Creation test of serial and parallel realization class. */
    @Test
    public void creationTest() {
        Sort<Integer> serial = new SerialQuickSort<Integer>();
        Sort<Integer> parallel = new ParallelQuickSort<Integer>();
    }

    /** Parallel sorting test. */
    @Test
    public void parallelSortTest() {
        Integer[] array = randomGenerator(ARRAY_LENGHT);

        Sort<Integer> parallel = new ParallelQuickSort<Integer>();
        parallel.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Parallel sort doesn't work", array[i].compareTo(array[i + 1]) <= 0);
        }
    }

    /** Serial sorting test. */
    @Test
    public void serialSortTest() {
        Integer[] array = randomGenerator(ARRAY_LENGHT);

        Sort<Integer> serial = new SerialQuickSort<Integer>();
        serial.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Serial sort doesn't work", array[i].compareTo(array[i + 1]) <= 0);
        }
    }

    /** Sorting empty array. */
    @Test
    public void  nullTest() {
        Sort<Integer> serial = new SerialQuickSort<Integer>();
        Sort<Integer> parallel = new ParallelQuickSort<Integer>();

        Integer[] array = {};

        serial.sort(array);
        parallel.sort(array);
    }

    /** String sorting test. */
    @Test
    public void stringSortTest() {
        String[] array1 = {"abc", "a", "bcd", "efg"};
        String[] array2 = array1.clone();

        Sort<String> serial = new SerialQuickSort<String>();
        Sort<String> parallel = new ParallelQuickSort<String>();

        serial.sort(array1);
        parallel.sort(array2);

        for (int i = 0; i < array1.length - 1; i++) {
            assertTrue("Serial sort doesn't work with strings", array1[i].compareTo(array1[i + 1]) <= 0);
            assertTrue("Parallel sort doesn't work with strings", array2[i].compareTo(array2[i + 1]) <= 0);
        }
    }

    private static Integer[] randomGenerator(Integer arrayLenght) {
        Random random = new Random();
        Integer[] array = new Integer[arrayLenght];

        for (int i = 0; i < arrayLenght; i++) {
            array[i] = random.nextInt();
        }

        return array;
    }
}
