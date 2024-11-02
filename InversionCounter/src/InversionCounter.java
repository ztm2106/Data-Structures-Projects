import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InversionCounter {

    public static long countInversionsSlow(int[] array) {
        long inversionCount = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    inversionCount++;
                }
            }
        }

        return inversionCount;
    }

    public static long countInversionsFast(int[] array) {
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        return countInversionsSort(arrayCopy);
    }

    public static long countInversionsSort(int[] array) {
        int[] scratch = new int[array.length];
        return countInversionsSortHelper(array, scratch, 0, array.length - 1);
    }

    public static long countInversionsSortHelper(int[] array, int[] scratch, int low, int high) {
        long inversionCount = 0;
        int mid = low + (high - low) / 2;
        if (low < high) {

            inversionCount += countInversionsSortHelper(array, scratch, low, mid);
            inversionCount += countInversionsSortHelper(array, scratch, mid + 1, high);
        }
            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                    inversionCount += (mid - i + 1);
                }
            }
        System.arraycopy(scratch, low, array, low, high - low + 1);

        return inversionCount;
    }


    private static int[] readArrayFromStdin() throws IOException, NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;

        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                                "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;

        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }

        return array;
    }

    public static void main(String[] args) {
        try {
            int[] array = readArrayFromStdin();
            long count;
            if (array.length == 0) {
                if (args.length == 1 && !args[0].equalsIgnoreCase("slow") && !args[0].equalsIgnoreCase("fast")){
                    System.out.println("Error: Unrecognized option '" + args[0] + "'.");
                    System.exit(1);
                }
                if (args.length > 1 && !args[0].equalsIgnoreCase("slow") && !args[0].equalsIgnoreCase("fast")){
                    System.out.println("Usage: java InversionCounter [slow]");
                    System.exit(1);
                }
                System.out.println("Enter sequence of integers, each followed by a space: Error: Sequence of integers not received.");
                System.exit(1);
            }


            if (args.length > 0 && args[0].equalsIgnoreCase("slow")) {
                // Use the slow algorithm
                count = countInversionsSlow(array);
                System.out.println("Enter sequence of integers, each followed by a space: Number of inversions: " + count);
            }
            else {
                // Use the fast algorithm (default)
                count = countInversionsFast(array);
                System.out.println("Enter sequence of integers, each followed by a space: Number of inversions: " + count);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Enter sequence of integers, each followed by a space: Error: " + e.getMessage());
            System.exit(1);
        }

    }
}


