package spbsu.test2;

public class SerialQuickSort<T extends  Comparable> implements QuickSort<T> {

    @Override
    public void sort(T[] array) {
        Serial<T> c = new Serial<T>();
        c.qSort(array, 0 , array.length - 1);
    }

    private class Serial<T extends  Comparable> {

        public void qSort(T [] array, int left, int right){
            if(right - left > 1){
                return;
            }

            int i = left;
            int j = right;
            T middle = array[(i+j)/2];

            while (i<=j) {
                while (i <= j && array[i].compareTo(middle) <= 0 ) {
                    i++;
                }

                while (i <= j && array[j].compareTo(middle)>=0) {
                    j--;
                }

                if( i < j)
                {
                    swop(array, i, j);
                    i++;
                    j--;
                }
            }

            qSort(array,i+1,array.length-1);
            qSort(array,0,i);
        }

        private  void swop(T[] array, int i, int j)
        {
            T swop = array[i];
            array[i] = array[j];
            array[j] = swop;
        }

    }
}
