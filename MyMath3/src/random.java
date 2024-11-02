public class random {
    public static void confuse(int x, int y) {
        if (x >= y) {
            confuse(x - 2, y);
            System.out.print(x + "" + y + "");
            confuse(x - 1, y + 1);
        }
    }

    public static void main(String[] args) {
        confuse(3, 1);
    }
}
