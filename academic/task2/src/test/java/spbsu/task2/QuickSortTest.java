package spbsu.task2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

/** Sorting test class. */
public class QuickSortTest {
    private final int ARRAYLENGHT = 1000000;
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
        Integer[] array = randGenerator(ARRAYLENGHT);
        QuickSort<Integer> parallel = new ParallelQuickSort<Integer>();
        parallel.sort(array);
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Parallel sort doesn't work",array[i].compareTo(array[i+1]) <= 0);
        }
    }

    /** Serial sorting test. */
    @Test
    public void serialSortTest() {
        Integer[] array = randGenerator(ARRAYLENGHT);
        QuickSort<Integer> serial = new SerialQuickSort<Integer>();
        serial.sort(array);
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Serial sort doesn't work",array[i].compareTo(array[i+1]) <= 0);
        }
    }

    /** Test by some example. */
    @Test
    public void usualTest() {
        float parallelTime = 0;
        float serialTime = 0;

        for (int i = 0; i < SIZE; i++) {
            Integer[] array = randGenerator(ARRAYLENGHT);
            serialTime += serialTime(array.clone())/SIZE;
            parallelTime += parallelTime(array)/SIZE;
        }
        assertTrue("Parallel slower sequential", parallelTime < serialTime );
    }

    /** Stability test. */
    @Test
    public void stabilityTest() {
        for (int j = 0; j < SIZE; j++) {
            float parallelTime = 0;
            float serialTime = 0;

            for (int i = 0; i < SIZE; i++) {
                Integer[] array = randGenerator(ARRAYLENGHT);
                serialTime += serialTime(array.clone()) / SIZE;
                parallelTime += parallelTime(array) / SIZE;
            }
            assertTrue("Parallel sometimes slower sequential", parallelTime < serialTime);
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
