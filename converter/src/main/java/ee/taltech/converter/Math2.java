package ee.taltech.converter;

public class Math2 {
    public static double sec(double value) {
        return (1 / Math.cos((value)));
    }

    public static double toRadians(double degrees) {
        return (degrees * Math.PI / 180);
    }

    public static double toDegrees(double radians) {
        return (radians * 180 / Math.PI);
    }

    public static double ln(double value) {
        return (Math.log10(value) / Math.log10(Math.exp(1)));
    }

    public static double log(double base, double value) {
        return (Math.log10(value) / Math.log10(base));
    }

    public static double round(double value, double pointsAfterDot) {
        double multiplier = 1D;
        for (int i = 0; i < pointsAfterDot; i++) {
            multiplier *= 10D;
        }
        return Math.round(value * multiplier) / multiplier;
    }
}
