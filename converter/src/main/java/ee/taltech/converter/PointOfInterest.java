
package ee.taltech.converter;

import ee.taltech.converter.coordinates.CoordinateSystem;
import lombok.Getter;

@Getter
@Deprecated
public class PointOfInterest<T extends CoordinateSystem> extends Point<T> {

    public PointOfInterest(T coordinateSystem) {
        super(coordinateSystem);
    }


private final double geodetic_latitude = 59.436961D * Math.PI / 180;
    private final double geodetic_longitude = 24.753575D * Math.PI / 180;
    private final double geodetic_height = 12.348D;

    public double cartesian_x;
    public double cartesian_y;
    public double cartesian_z;

    public double find_latitude;
    public double find_longitude;
    public double find_height;



    public double beta;
/*
    public void calculate_cartesian_coordinates(WGS84 earth, PointOfInterest pointOfInterest) {

 // Calculate X
        cartesian_x = (earth.get_radius_of_curvature_on_prime_vertical(pointOfInterest, earth) + geodetic_height)
                * Math.cos(geodetic_latitude) * Math.cos(geodetic_longitude);


        // Calculate Y
        cartesian_y = (earth.get_radius_of_curvature_on_prime_vertical(pointOfInterest, earth) + geodetic_height)
                * Math.cos(geodetic_latitude) * Math.sin(geodetic_longitude);

        // Calculate Z
        cartesian_z = ((((Math.pow(earth.getMinorRadius(), 2)) / (Math.pow(earth.getMajorRadius(), 2)))
                * earth.get_radius_of_curvature_on_prime_vertical(pointOfInterest, earth)) + pointOfInterest.geodetic_height) *
                Math.sin(pointOfInterest.geodetic_latitude);
    }

    public void calculate_geodetic_coordinates(WGS84 earth, PointOfInterest pointOfInterest) {

        // Calculate longitude
        if (pointOfInterest.cartesian_x != 0) {
            find_longitude = Math.atan(cartesian_y / cartesian_x);
        } else if (pointOfInterest.cartesian_y > 0) {
            find_longitude = 90D;
        } else if (pointOfInterest.cartesian_y < 0) {
            find_longitude = -90D;
        } else {
            find_longitude = 0;
        }
        // Find initial beta first
        beta = Math.atan(get_initial_beta(earth, pointOfInterest));

        // Reduce errors
        for (int i = 0; i < 10; i++) {
            find_latitude = Math.atan(pointOfInterest.tangentOfBet(earth, pointOfInterest));

            pointOfInterest.beta = Math.atan(pointOfInterest.get_beta(earth, pointOfInterest));
        }

        // Calculate height
        find_height = ((Math.sqrt(Math.pow(cartesian_x, 2) + Math.pow(cartesian_y, 2))) /
                (Math.cos(find_latitude))) - earth.get_radius_of_curvature_on_prime_vertical(pointOfInterest, earth);
    }

    //ok
    public double get_initial_beta(WGS84 earth, PointOfInterest pointOfInterest) {
        //tangent to return
        return ((earth.getMajorRadius() * pointOfInterest.cartesian_z) /
                (earth.getMinorRadius() * (Math.sqrt(
                        Math.pow(pointOfInterest.cartesian_x, 2) + Math.pow(pointOfInterest.cartesian_y, 2)))));
    }

    public double get_beta(WGS84 earth, PointOfInterest pointOfInterest) {
        return (1 - earth.getFlattening()) * Math.tan(pointOfInterest.find_latitude);
    }

    public double tangentOfBet(WGS84 earth, PointOfInterest pointOfInterest) {
        //tangent to return
        return (pointOfInterest.cartesian_z + (earth.getEccentricity_2_square()
                * earth.getMinorRadius() * Math.pow(Math.sin(beta), 3))) /
                ((Math.sqrt(Math.pow(pointOfInterest.cartesian_x, 2) + Math.pow(pointOfInterest.cartesian_y, 2))) -
                        (earth.getMajorRadius() * earth.getEccentricity_square() *
                                Math.pow(Math.cos(beta), 3)));
    }*/
}
