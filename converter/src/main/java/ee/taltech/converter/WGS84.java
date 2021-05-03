package ee.taltech.converter;

import lombok.*;

@Getter
public class WGS84 extends Ellipsoid implements DescribeEarth, GeodeticCoordinates<WGS84> {

    public WGS84() {
        super(6378137D, 6356752D);
        flattening = flattening(majorRadius, minorRadius);
        eccentricity = eccentricity(majorRadius, minorRadius);
        eccentricity_ = eccentricity_(majorRadius, minorRadius);
    }

    //private final double majorRadius = 6378137D;
    /*protected double majorRadius = 6378137D;
    protected double minorRadius = 6356752D;*/

   /* protected double eccentricity47 = eccentricity(majorRadius, minorRadius);
    protected double eccentricity_ = eccentricity_(majorRadius,minorRadius);*/


    //private final double minorRadius = 6356752D;

    //private double flattening = (majorRadius - minorRadius) / majorRadius;

    /*private final double eccentricity_square = (Math.pow(majorRadius,2) - Math.pow(minorRadius,2))
            / Math.pow(majorRadius,2);

    private final double eccentricity_2_square = (Math.pow(majorRadius,2) - Math.pow(minorRadius,2))
            / Math.pow(minorRadius,2);*/

    //private final double eccentricity = Math.sqrt(eccentricity_square);

   /* public double get_radius_of_curvature_on_prime_vertical (PointOfInterest pointOfInterest, WGS84 earth) {
        return earth.majorRadius / (Math.sqrt
                (1 - (earth.eccentricity_square * Math.pow(Math.sin(pointOfInterest.getGeodetic_latitude()), 2))));
    }*/

}
