package ee.taltech.converter;

import lombok.Data;

/**
 * Math.
 *      cos
 *      sin
 *      tan
 * They all operate on RADIANS, to pass RADIANS into equation do --> Math.cos(90 * Math.PI / 180) = 0.0
 */
@Data
public class WGS84 {

    //ok
    private final double semi_major_axis_radius = 6378137D;
    //ok
    private final double semi_minor_axis_radius = 6356752D;
    //ok
    private double flattening = (semi_major_axis_radius - semi_minor_axis_radius) / semi_major_axis_radius;


    //ok
    private final double eccentricity_square = (Math.pow(semi_major_axis_radius,2) - Math.pow(semi_minor_axis_radius,2))
            / Math.pow(semi_major_axis_radius,2);

    private final double eccentricity_2_square = (Math.pow(semi_major_axis_radius,2) - Math.pow(semi_minor_axis_radius,2))
            / Math.pow(semi_minor_axis_radius,2);

    //ok
    private final double eccentricity = Math.sqrt(eccentricity_square);


    public double get_radius_of_curvature_on_prime_vertical (Kaev_Left kaevLeft, WGS84 earth) {
        return earth.semi_major_axis_radius / (Math.sqrt
                (1 - (earth.eccentricity_square * Math.pow(Math.sin(kaevLeft.getGeodetic_latitude()), 2))));
    }
}
