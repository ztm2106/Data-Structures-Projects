public class Hanoi {
 
    public static void hanoi(int numDisks, int fromPeg, int toPeg) {
        if (numDisks == 1) {
            System.out.println(fromPeg + " -> " + toPeg);
            return;
        }
        int helpPeg = 6 - fromPeg - toPeg;
        hanoi(numDisks - 1, fromPeg, helpPeg);
        System.out.println(fromPeg + " -> " + toPeg);
        hanoi(numDisks - 1, helpPeg, toPeg);
        // Recurrence: T(n) = 2T(n - 1) + Theta(1)
    }

    public static void main(String[] args) {
        hanoi(3, 3, 1);
    }
}
