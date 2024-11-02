/**
 * @author Zakiy Manigo
 * Programming Assignment 2 - Recursion exercises
 * COMS W3134
 *
 * Note: All methods must be written recursively. No credit will be given for
 * methods written without recursion, even if they produce the correct output.
 */
public class Recursion {

    /**
     * Returns the value of x * y, computed via recursive addition.
     * x is added y times. Both x and y are non-negative.
     * @param x  non-negative integer multiplicand 1
     * @param y  non-negative integer multiplicand 2
     * @return   x * y
     */
    public static int recursiveMultiplication(int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        } else {
            if (y >= 0) {
                return x + recursiveMultiplication(x, y - 1);
            }
            else {
                return  recursiveMultiplication(x, y - 1);
            }
        }
    }

/******************************************************************************/
    /**
     * Reverses a string via recursion.
     * @param s  the non-null string to reverse
     * @return   a new string with the characters in reverse order
     */
    public static String reverse(String s) {
        if (s.isEmpty()){
            return "";
        }
        else {
            return (s.charAt(s.length() - 1) + reverse(s.substring(0, s.length() - 1)));
        }
    }

/******************************************************************************/
    private static int maxHelper(int[] array, int index, int max) {
        if (array.length == index) {
            return max;
        }
        else {
            if (array[index] > max) {
                return maxHelper(array, index + 1, array[index]);
            }
            else {
                return maxHelper(array, index + 1, max);
            }
        }
    }

    /**
     * Returns the maximum value in the array.
     * Uses a helper method to do the recursion.
     * @param array  the array of integers to traverse
     * @return       the maximum value in the array
     */
    public static int max(int[] array) {
        return maxHelper(array, 0, Integer.MIN_VALUE);
    }

/******************************************************************************/

    /**
     * Returns whether or not a string is a palindrome, a string that is
     * the same both forward and backward.
     * @param s  the string to process
     * @return   a boolean indicating if the string is a palindrome
     */
    public static boolean isPalindrome(String s) {
        if (s.length() <= 1) {
            return true;
        }
        else {
            if (s.charAt(s.length()-1) == s.charAt(0)) {
                if (s.length() == 2){
                    return true;
                }
                else {
                    return isPalindrome(s.substring(s.indexOf(s.charAt(0)) + 1, s.length() - 1));
                }
            }
            else {
                return false;
            }
        }
    }

/******************************************************************************/
    private static boolean memberHelper(int key, int[] array, int index) {
        if (array.length == index || array.length == 0) {
            return false;
        }
        else {
            if (key == array[index]){
                return true;
            }
            else {
                return memberHelper(key, array, index + 1);
            }
        }

    }

    /**
     * Returns whether or not the integer key is in the array of integers.
     * Uses a helper method to do the recursion.
     * @param key    the value to seek
     * @param array  the array to traverse
     * @return       a boolean indicating if the key is found in the array
     */
    public static boolean isMember(int key, int[] array) {
        return memberHelper(key, array, 0);
    }
//
/******************************************************************************/
    /**
     * Returns a new string where identical chars that are adjacent
     * in the original string are separated from each other by a tilde '~'.
     * @param s  the string to process
     * @return   a new string where identical adjacent characters are separated
     *           by a tilde
     */
    public static String separateIdentical(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        else {
            if (s.charAt(0) == s.charAt(1)) {
                return s.charAt(0) + "~" + separateIdentical(s.substring(1));
            }
            return  s.charAt(0) + separateIdentical(s.substring(1));
        }
    }

    public static void main(String[] args) {
        System.out.println("recursive tests");
        System.out.println(recursiveMultiplication( 1, 4));
        System.out.println(recursiveMultiplication( 3, 5));
        System.out.println(recursiveMultiplication( 1000, 5));
        System.out.println(recursiveMultiplication( 5, 1000));
        System.out.println("reverse tests");
        System.out.println(reverse("cat"));
        System.out.println(reverse("dog"));
        System.out.println(reverse("mat"));
        System.out.println(reverse("abcdefg"));
        System.out.println("maxHelper");
        int[] array1 = {3, 7, 2, 8, 5};
        int[] array2 = {-3, -7, -2, -8, -5};
        int[] array3 = {42};
        int[] array4 = {10, 10, 10, 10};
        int[] array5 = {};
        System.out.println(max(array1));
        System.out.println(max(array2));
        System.out.println(max(array3));
        System.out.println(max(array4));
        System.out.println(max(array5));
        System.out.println("Palindrome tests");
        System.out.println(isPalindrome("hah"));
        System.out.println(isPalindrome("aha"));
        System.out.println(isPalindrome("taooat"));
        System.out.println(isPalindrome("uioloiu"));
        System.out.println(isPalindrome("aha"));
        System.out.println(isPalindrome("ahasd"));
        System.out.println(isPalindrome("dsegvahasd"));
        System.out.println("isMember tests");
        int[] array6 = {1, 2, 3, 4, 5};
        int key1 = 3;
        int[] array7 = {10, 20, 30, 40, 50};
        int key2 = 15;
        int[] array8 = {};
        int key3 = 5;
        int[] array9 = {42, 15, 30};
        int key4 = 42;
        System.out.println(isMember(key1, array6));
        System.out.println(isMember(key2, array7));
        System.out.println(isMember(key3, array8));
        System.out.println(isMember(key4, array9));
        System.out.println("separateIdentical tests");
        System.out.println(separateIdentical("abcc"));
        System.out.println(separateIdentical("abcd"));
        System.out.println(separateIdentical("aabbccdd"));
        System.out.println(separateIdentical("aabccd"));
        System.out.println(separateIdentical(""));
        System.out.println(separateIdentical("a"));
        System.out.println(separateIdentical("a~a"));
        System.out.println(separateIdentical("a~aa"));

    }
}
