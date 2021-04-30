package ee.taltech.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Kaev_Left extends Place{

    //right now it is liiklusmark
    /*private final double geodetic_latitude = 59.44061D;
    private final double geodetic_longitude = 24.847142D;
    private final double geodetic_height = 36.189D;*/
    //right now is tallinn center (to check Mercator UTM)
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

    public void calculate_cartesian_coordinates(WGS84 earth, Kaev_Left kaevLeft) {
        // Calculate X
        cartesian_x = (earth.get_radius_of_curvature_on_prime_vertical(kaevLeft, earth) + geodetic_height)
                * Math.cos(geodetic_latitude) * Math.cos(geodetic_longitude);

        // Calculate Y
        cartesian_y = (earth.get_radius_of_curvature_on_prime_vertical(kaevLeft, earth) + geodetic_height)
                * Math.cos(geodetic_latitude) * Math.sin(geodetic_longitude);

        // Calculate Z
        cartesian_z = ((((Math.pow(earth.getSemi_minor_axis_radius(), 2)) / (Math.pow(earth.getSemi_major_axis_radius(), 2)))
                * earth.get_radius_of_curvature_on_prime_vertical(kaevLeft, earth)) + kaevLeft.geodetic_height) *
                Math.sin(kaevLeft.geodetic_latitude);
    }

    public void calculate_geodetic_coordinates(WGS84 earth, Kaev_Left kaevLeft) {

        // Calculate longitude
        if (kaevLeft.cartesian_x != 0) {
            find_longitude = Math.atan(cartesian_y / cartesian_x);
        } else if (kaevLeft.cartesian_y > 0) {
            find_longitude = 90D;
        } else if (kaevLeft.cartesian_y < 0) {
            find_longitude = -90D;
        } else {
            find_longitude = 0;
        }
        // Find initial beta first
        beta = Math.atan(get_initial_beta(earth, kaevLeft));

        // Reduce errors
        for (int i = 0; i < 10; i++) {
            find_latitude = Math.atan(kaevLeft.tangentOfBet(earth, kaevLeft));

            kaevLeft.beta = Math.atan(kaevLeft.get_beta(earth, kaevLeft));
        }

        // Calculate height
        find_height = ((Math.sqrt(Math.pow(cartesian_x, 2) + Math.pow(cartesian_y, 2))) /
                (Math.cos(find_latitude))) - earth.get_radius_of_curvature_on_prime_vertical(kaevLeft, earth);
    }

    //ok
    public double get_initial_beta(WGS84 earth, Kaev_Left kaevLeft) {
        //tangent to return
        return ((earth.getSemi_major_axis_radius() * kaevLeft.cartesian_z) /
                (earth.getSemi_minor_axis_radius() * (Math.sqrt(
                        Math.pow(kaevLeft.cartesian_x, 2) + Math.pow(kaevLeft.cartesian_y, 2)))));
    }

    public double get_beta(WGS84 earth, Kaev_Left kaevLeft) {
        return (1 - earth.getFlattening()) * Math.tan(kaevLeft.find_latitude);
    }

    public double tangentOfBet(WGS84 earth, Kaev_Left kaevLeft) {
        //tangent to return
        return (kaevLeft.cartesian_z + (earth.getEccentricity_2_square()
                * earth.getSemi_minor_axis_radius() * Math.pow(Math.sin(beta), 3))) /
                ((Math.sqrt(Math.pow(kaevLeft.cartesian_x, 2) + Math.pow(kaevLeft.cartesian_y, 2))) -
                        (earth.getSemi_major_axis_radius() * earth.getEccentricity_square() *
                                Math.pow(Math.cos(beta), 3)));
    }
}
