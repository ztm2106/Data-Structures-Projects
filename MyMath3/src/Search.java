/**
 * @author Brian S. Borowski
 * @version 1.0
 * Sequential and binary search implementations
 * COMS W3134
 */
import java.util.Arrays;

/**
 * Person class consisting of a first name and last name.
 * Implements Comparable<Person> which means it implements the
 * method:
 * public int compareTo(Person other)
 */
class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public int compareTo(Person other) {
        int lastNameComp = lastName.compareTo(other.lastName);
        if (lastNameComp == 0) {
            return firstName.compareTo(other.firstName);
        }
        return lastNameComp;
    }
}

public class Search {

    public static int sequentialSearch(int key, int[] array) {
        System.out.println("Iterative sequential search [ints]");
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public static <E extends Comparable<E>> int sequentialSearch(E key, E[] array) {
        System.out.println("Iterative sequential search [generics]");
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(key) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static <E extends Comparable<E>> int sequentialSearchRecHelper(E key, E[] array, int index) {
        if (index == array.length) {
            return -1;
        }
        if (array[index].compareTo(key) == 0) {
            return index;
        }
        return sequentialSearchRecHelper(key, array, index + 1);
    }

    public static <E extends Comparable<E>> int sequentialSearchRec(E key, E[] array) {
        System.out.println("Recursive sequential search [generics]");
        return sequentialSearchRecHelper(key, array, 0);
    }

    public static <E extends Comparable<E>> int binarySearch(E key, E[] array) {
        System.out.println("Iterative binary search [generics]");
        int low = 0, high = array.length - 1;
        while (low <= high) {
            // This formula is the same as (mid + low) / 2, but it avoids potential overflow errors.
            int mid = low + (high - low) / 2,
                result = key.compareTo(array[mid]);
            if (result == 0) {
                return mid;
            }
            // mid is less than key than mid - 1 = high
            if (result < 0) {
                high = mid - 1;
            }
            // mid is less than key than mid + 1 = low
            else {
                low = mid + 1;
            }
        }
        // Returns a negative value, indicating the element is not present.
        // Assume the return value is stored in a variable called index. If you take
        // -index - 1, that value becomes the index in the array the value should be inserted,
        // if you were to do so.
        return -low - 1;
    }

    private static <E extends Comparable<E>> int binarySearchRec(E key, E[] array, int low, int high) {
        if (low > high) {
            return -low - 1;
        }
        int mid = low + (high - low) / 2,
            result = key.compareTo(array[mid]);
        if (result == 0) {
            return mid;
        }
        if (result < 0) {
            return binarySearchRec(key, array, low, mid - 1);
        }
        return binarySearchRec(key, array, mid + 1, high);
    }

    public static <E extends Comparable<E>> int binarySearchRec(E key, E[] array) {
        System.out.println("Recursive binary search [generics]");
        return binarySearchRec(key, array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        int[] intArray = new int[] {3, 5, 1, 9, 8};
        System.out.println(sequentialSearch(9, intArray));

        String[] stringArray = new String[] {"John", "Joe", "Beth", "Amanda", "Susan"};
        System.out.println(sequentialSearch("Amanda", stringArray));

        System.out.println(sequentialSearchRec("Amanda", stringArray));

        Arrays.sort(stringArray);
        System.out.println(Arrays.toString(stringArray));
        // Should print out 0 through 4, indicating all elements have been found.
        for (String s : stringArray) {
            System.out.println(binarySearch(s, stringArray));
        }
        for (String s : stringArray) {
            System.out.println(binarySearchRec(s, stringArray));
        }
        // Should not be found.
        System.out.println(binarySearch("hi", stringArray));
        System.out.println(binarySearchRec("hi", stringArray));

        Integer[] integerArray = new Integer[] {4, 6, 1, 9, 3};
        System.out.println(sequentialSearch(6, integerArray));

        Person[] personArray = new Person[] {
                new Person("Doe", "John"),
                new Person("Simer", "Jane"),
                new Person("Smith", "Steve")};
        System.out.println(binarySearchRec(new Person("Smith", "Steve"), personArray));
    }
}
