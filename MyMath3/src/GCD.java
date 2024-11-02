/**
 * Class containing method that perform the GCD of two real integers.
 * @author Zakiy Manigo
 * @version 1.0
 */
public class GCD {
    /**
     * Returns the GCD of two integer using iterative algorithm.
     * @param m first integer & n the second integer
     * @return the greatest common divisor between m and n
     */
    public static int iterativeGcd(int m, int n) {
        while (n != 0) {
            int z = n;
            n = m % n;
            m = z;
        }
        return m;
    }

    /**
     * Returns the GCD of two integer using recursive algorithm.
     * @param m first integer & n the second integer
     * @return the greatest common divisor between m and n
     */
    public static int recursiveGcd(int m, int n){
        if (n == 0){
            return m;
        }
        return recursiveGcd(n, m % n);
    }

    public static void main(String[] args) {
        //If there are not exactly 2 arguments, the program will print the usage message to standard error
        if (args.length != 2){
            System.out.println("Usage: java GCD <integer m> <integer n>");
            System.exit(1);
        }
        // If the user supplies a String for m that is not a valid integer, the program will print the message
        String input1 = args[0];
        try {
            int m = Integer.parseInt(input1);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: The first argument is not a valid integer.");
            System.exit(1);
        }

        //If the user supplies a String for n that is not a valid integer, the program will print the message
        String input2 = args[1];
        try {
            int n = Integer.parseInt(input2);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: The second argument is not a valid integer.");
            System.exit(1);
        }

        //If the user enters 0 for both m and n, the GCD is undefined. Otherwise, the program should compute the GCD of integers m and n both iterative and recursively.
        // also checks to see if there are only two integers inputted otherwise error
        try {
            int m = Math.abs(Integer.parseInt(args[0]));
            int n = Math.abs(Integer.parseInt(args[1]));
            if (m == 0 && n == 0){
                System.out.println("gcd(0, 0) = undefined");
                System.exit(0);
            }
            else{
                int iterativeGcd = iterativeGcd(m,n);
                int recursiveGcd = recursiveGcd(m,n);
                System.out.println("Iterative: gcd(" + args[0] + ", " + args[1] + ") = " + iterativeGcd + "\n" +
                        "Recursive: gcd(" + args[0] + ", " + args[1] + ") = " + recursiveGcd);
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Please provide two valid integer.");
            System.exit(1);
        }
    }
}

