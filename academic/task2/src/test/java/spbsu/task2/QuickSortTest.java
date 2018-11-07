package spbsu.task2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

/** Sorting test class. */
public class QuickSortTest {
    private final int ARRAY_LENGHT = 1000000;
    private final int SIZE = 20;

    /** Creation test of serial and parallel realization class. */
    @Test
    public void creationTest() {
        QuickSort<Integer> serial = new SerialQuickSort<Integer>();
        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();
    }

    /** Parallel sorting test. */
    @Test
    public void parallelSortTest() {
        Integer[] array = randGenerator(ARRAY_LENGHT);

        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();
        parallel.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Parallel sort doesn't work", array[i].compareTo(array[i + 1]) <= 0);
        }
    }

    /** Serial sorting test. */
    @Test
    public void serialSortTest() {
        Integer[] array = randGenerator(ARRAY_LENGHT);

        QuickSort<Integer> serial = new SerialQuickSort<Integer>();
        serial.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Serial sort doesn't work", array[i].compareTo(array[i + 1]) <= 0);
        }
    }

    /** Sorting empty array. */
    @Test
    public void  nullTest() {
        QuickSort<Integer> serial = new SerialQuickSort<Integer>();
        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();

        Integer[]array = {};

        serial.sort(array);
        parallel.sort(array);
    }

    /** String sorting test. */
    @Test
    public void stringSortTest() {
        String[] array1 = {"abc", "a", "bcd", "efg"};
        String[] array2 = array1.clone();

        QuickSort<String> serial = new SerialQuickSort<String>();
        QuickSort<String> parallel = new ParallelQuickSort<String>();

        serial.sort(array1);
        parallel.sort(array2);

        for (int i = 0; i < array1.length - 1; i++) {
            assertTrue("Serial sort doesn't work", array1[i].compareTo(array1[i + 1]) <= 0);
            assertTrue("Parallel sort doesn't work", array2[i].compareTo(array2[i + 1]) <= 0);
        }
    }

    private static float serialTime(Integer[] array) {
        QuickSort<Integer> sorter = new SerialQuickSort<Integer>();

        long time = System.currentTimeMillis();
        sorter.sort(array);

        return System.currentTimeMillis() - time;
    }

    private static float parallelTime(Integer[] array) {
        QuickSort<Integer> sorter = new ParallelQuickSort<Integer>();

        long time = System.currentTimeMillis();
        sorter.sort(array);

        return System.currentTimeMillis() - time;
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
