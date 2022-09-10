public class Trig {
    public static void main(String[] args) {
        double degrees = Double.parseDouble(args[0]);
        double radians = Math.toRadians(degrees);

        double s = Math.sin(radians);
        System.out.println("sin(" + degrees + ") = " + s);
    }
}