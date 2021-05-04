package ee.taltech.converter;

/**
 * Interface, that provides functionality for describing an ellipsoid model.
 */
public interface DescribeEarth {
    /**
     * Method that calculates given ellipsoid flattening (how much ellipsoid is squeezed from North to South).
     *
     * @param major_radius Half of the ellipsoid radius from East to West direction.
     * @param minor_radius Half of the ellipsoid radius from North to South direction.
     * @return Flattening value of given ellipsoid.
     */
    default double flattening(double major_radius, double minor_radius) {
        return (major_radius - minor_radius) / major_radius;
    }

    /**
     * Method that calculates a proportion, which show how much ellipsoid is away from circle with regards to
     * its major radius value.
     *
     * @param major_radius Half of the ellipsoid radius from East to West direction.
     * @param minor_radius Half of the ellipsoid radius from North to South direction.
     * @return Eccentricity value.
     */
    default double eccentricity(double major_radius, double minor_radius) {
        double squared = (Math.pow(major_radius, 2) - Math.pow(minor_radius, 2)) / Math.pow(major_radius, 2);
        return Math.sqrt(squared);
    }

    /**
     * Method that calculates a proportion, which show how much ellipsoid is away from circle with regards to
     * its minor radius value.
     *
     * @param major_radius Half of the ellipsoid radius from East to West direction.
     * @param minor_radius Half of the ellipsoid radius from North to South direction.
     * @return Eccentricity value.
     */
    default double eccentricity_(double major_radius, double minor_radius) {
        double squared = (Math.pow(major_radius, 2) - Math.pow(minor_radius, 2)) / Math.pow(minor_radius, 2);
        return Math.sqrt(squared);
    }
}
