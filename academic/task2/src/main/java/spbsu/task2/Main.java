package spbsu.task2;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int ARRAY_LENGHT = 1000000;
        final int SIZE = 16;

        float parallelTime = 0;
        float serialTime = 0;

        for (int i = 0; i < SIZE; i++) {
            Integer[] array = randomGenerator(ARRAY_LENGHT);
            serialTime += serialTime(array.clone()) / SIZE;
            parallelTime += parallelTime(array) / SIZE;
        }

        System.out.println("serialTime = " + serialTime);
        System.out.println("parallelTime = " + parallelTime);
    }

    private static float serialTime(Integer[] array) {
        Sort<Integer> sorter = new SerialQuickSort<Integer>();

        long time = System.currentTimeMillis();
        sorter.sort(array);

        return System.currentTimeMillis() - time;
    }

    private static float parallelTime(Integer[] array) {
        Sort<Integer> sorter = new ParallelQuickSort<Integer>();

        long time = System.currentTimeMillis();
        sorter.sort(array);

        return System.currentTimeMillis() - time;
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
