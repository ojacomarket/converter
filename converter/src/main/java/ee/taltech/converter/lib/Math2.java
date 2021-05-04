package ee.taltech.converter.lib;

/**
 * Abstract class, that is used as an addition to java.lang.Math class, which mainly adds more functionality
 * for complex mathematical calculations.
 */
abstract public class Math2 {
    /**
     * Method that is used to calculate secans of the angle.
     *
     * @param value Angle value in radians.
     * @return Secans of the angle.
     */
    public static double sec(double value) {
        return (1 / Math.cos((value)));
    }

    /**
     * Method that is used to transform value of an angle in degrees into value of radians.
     *
     * @param degrees Value of the angle in degrees.
     * @return Value of the angle in radians.
     */
    public static double toRadians(double degrees) {
        return (degrees * Math.PI / 180);
    }

    /**
     * Method that is used to transform value of an angle in radians into value of degrees.
     *
     * @param radians Value of the angle in radians.
     * @return Value of the angle in degrees.
     */
    public static double toDegrees(double radians) {
        return (radians * 180 / Math.PI);
    }

    /**
     * Method that is used to calculate natural logarithm of a value (base is 'e')
     *
     * @param value Value to apply natural logarithm on.
     * @return Natural logarithm of the value.
     */
    public static double ln(double value) {
        return (Math.log10(value) / Math.log10(Math.exp(1)));
    }

    /**
     * Method that is used to calculate any algorithm of any value.
     *
     * @param base  Base of the logarithm.
     * @param value Value to apply logarithm on.
     * @return Logarithm of the value, with specified base.
     */
    public static double log(double base, double value) {
        return (Math.log10(value) / Math.log10(base));
    }

    /**
     * Method that is used to round decimal numbers with a precision of 2^64 numbers after decimal point.
     *
     * @param value          Value to round.
     * @param pointsAfterDot How many number you want to see after decimal point (human-friendly, starts with 1).
     * @return
     */
    public static double round(double value, double pointsAfterDot) {
        double multiplier = 1D;
        for (int i = 0; i < pointsAfterDot; i++) {
            multiplier *= 10D;
        }
        return Math.round(value * multiplier) / multiplier;
    }
}
