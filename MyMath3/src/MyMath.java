/**
 * Class containing methods that perform mathematical operations.
 * @author Brian Borowski
 * @version 1.0
 */
public class MyMath {
    /**
     * Returns the square of a number.
     * @param x  the number to square
     * @return   the square of the number
     */
    public static double sqr(double x) {
        return x * x;
    }

    public static void main(String[] args) {

        System.out.println("You entgered the following args");
        for (String s: args) {
            double sq = sqr(Double.parseDouble(s));
            System.out.println(" - " + s + " - " + sq);
        }
        System.out.println(sqr(5));
    }
}

// jave Mymath 3
