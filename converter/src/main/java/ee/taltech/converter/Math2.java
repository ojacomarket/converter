package ee.taltech.converter;

public class Math2 {
    public static double sec (double value) {
        return (1 / Math.cos((value)));
    }
    public static double toRadians (double degrees) {
        return (degrees * Math.PI / 180);
    }
    public static double toDegrees (double radians) {
        return (radians * 180 / Math.PI);
    }
    public static double ln (double value) {
        return (Math.log10(value) / Math.log10(Math.exp(1)));
    }

    public static double log (double base, double value) {
        return (Math.log10(value) / Math.log10(base));
    }

    public static void main(String[] args) {
        System.out.println(Math.exp(1));
    }
}
