import java.util.Arrays;
import java.util.Random;

/**
 * Class that implements n lg n sorting algorithms:
 *  - heapsort
 *  - mergesort
 *  - quicksort (with Lomuto partitioning)
 * @author Brian S. Borowski
 * @version 1.0 November 15, 2022
 */
public class AdvancedSorting {

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void percolateDown(int[] array, int i, int n) {
        int child = 2 * i + 1, temp;
        for (temp = array[i]; child < n; i = child, child = 2 * i + 1) {
            if (child != n - 1 && array[child] < array[child + 1]) {
                child++;
            }
            if (temp < array[child]) {
                array[i] = array[child];
            } else {
                break;
            }
        }
        array[i] = temp;
    }

    public static void heapsort(int[] array) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            percolateDown(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            percolateDown(array, 0, i);

        }
    }

    private static void mergesortHelper(int[] array, int[] scratch, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergesortHelper(array, scratch, low, mid);
            mergesortHelper(array, scratch, mid + 1, high);

            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                }
            }
            for (int k = low; k <= high; k++) {
                array[k] = scratch[k];
            }
        }
    }

    public static void mergesort(int[] array) {
        int[] scratch = new int[array.length];
        mergesortHelper(array, scratch, 0, array.length - 1);
    }

    private static int lomutoPartition(int[] array, int left, int right) {
        int x = array[left], s = left;
        for (int j = left + 1; j <= right; j++) {
            if (array[j] < x) {
                s++;
                if (s != j) {
                    swap(array, s, j);
                }
            }
        }
        if (s != left) {
            swap(array, s, left);
        }
        return s;
    }

    private static void quicksortHelper(int[] array, int p, int r) {
        if (p < r) {
            int q = lomutoPartition(array, p, r);
            quicksortHelper(array, p, q - 1);
            quicksortHelper(array, q + 1, r);
        }
    }

    public static void quicksort(int[] array) {
        quicksortHelper(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = rand.nextInt(100); // ints from 0 - 99, inclusive
        }
        System.out.println("Original array          : " + Arrays.toString(array));

        int[] clone1 = array.clone();
        heapsort(clone1);
        System.out.println("Sorted array (heapsort) : " + Arrays.toString(clone1));

        int[] clone2 = array.clone();
        mergesort(clone2);
        System.out.println("Sorted array (mergesort): " + Arrays.toString(clone2));

        int[] clone3 = array.clone();
        quicksort(clone3);
        System.out.println("Sorted array (quicksort): " + Arrays.toString(clone3));
    }
}
