package spbsu.test2;


import java.util.Random;

public class main {
    public static void main(String[] args) {
        final int ARRAYLENGHT = 1000000;
        final int SIZE = 16;

        float parallelTime = 0;
        float serialTime = 0;

        for (int i = 0; i < SIZE; i++) {
            Integer[] array = randGenerator(ARRAYLENGHT);
            serialTime += serialTime(array.clone());
            parallelTime += parallelTime(array);
        }

        System.out.println("serialTime = " + serialTime);
        System.out.println("parallelTime = " + parallelTime);
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
