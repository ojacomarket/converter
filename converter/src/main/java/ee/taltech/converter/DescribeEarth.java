package ee.taltech.converter;

public interface DescribeEarth {

    default double flattening(double major_radius, double minor_radius) {
        return (major_radius - minor_radius) / major_radius;
    }

    default double eccentricity(double major_radius, double minor_radius) {
        double squared = (Math.pow(major_radius, 2) - Math.pow(minor_radius, 2)) / Math.pow(major_radius, 2);
        return Math.sqrt(squared);
    }

    default double eccentricity_(double major_radius, double minor_radius) {
        double squared = (Math.pow(major_radius, 2) - Math.pow(minor_radius, 2)) / Math.pow(minor_radius, 2);
        return Math.sqrt(squared);
    }
}
