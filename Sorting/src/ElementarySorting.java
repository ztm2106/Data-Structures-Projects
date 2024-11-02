import java.util.Arrays;
import java.util.Random;

/**
 * Class that implements n^2 sorting algorithms:
 *  - bubble sort
 *  - selection sort
 *  - insertion sort
 * @author Brian S. Borowski
 * @version 1.0 November 15, 2022
 */
public class ElementarySorting {

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void bubbleSort(int[] array) {
        int len = array.length;
        while (len > 0) {
            int n = 0;
            for (int i = 1; i < len; ++i) {
                if (array[i - 1] > array[i]) {
                    swap(array, i - 1, i);
                    n = i;
                }
            }
            len = n;
        }
    }
    //n = the highest index which the swap occurs
        //in the root notation of the loop, we need only to go up to the last value of n
    // theta(n) is the best case
    // n(n-1)/2 so the worst case is theta(n^2)
    // average case is theta(n^2)
    //overall theta(n^2)

    public static void selectionSort(int[] array) {
        for (int i = 0, i_bound = array.length - 1; i < i_bound; ++i) {
            int min_index = i, min = array[i];
            for (int j = i + 1; j < array.length; ++j) {
                if (array[j] < min) {
                    min_index = j;
                    min = array[j];
                }
            }
            if (min_index != i) {
                swap(array, i, min_index);
            }
        }
    }
    // theta(n) is the best case
    // n(n-1)/2 so the worst case is theta(n^2)
    // average case is theta(n^2)
    //overall theta(n^2)

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; ++i) {
            int k, current = array[i];
            for (k = i - 1; k >= 0 && array[k] > current; --k) {
                array[k + 1] = array[k];
            }
            array[k + 1] = current;
        }
    }
    //like playing poker getting a straight
    // theta(n) is the best case
    // n(n-1)/2 so the worst case is theta(n^2)
    // average case is theta(n^2)
    //overall O(n^2)

    public static void main(String[] args) {
        Random rand = new Random();
        int[] array = new int[10];
        int upperBound = 100;
        for (int i = 0; i < 10; i++) {
            array[i] = rand.nextInt(100); // ints from 0 - 99, inclusive
        }
        System.out.println("Original array               : " + Arrays.toString(array));

        int[] clone1 = array.clone();
        bubbleSort(clone1);
        System.out.println("Sorted array (bubble sort)   : " + Arrays.toString(clone1));

        int[] clone2 = array.clone();
        selectionSort(clone2);
        System.out.println("Sorted array (selection sort): " + Arrays.toString(clone2));

        int[] clone3 = array.clone();
        insertionSort(clone3);
        System.out.println("Sorted array (insertion sort): " + Arrays.toString(clone3));
    }
}
