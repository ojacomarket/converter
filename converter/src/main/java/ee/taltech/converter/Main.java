package ee.taltech.converter;

import ee.taltech.converter.coordinates.Cartesian;
import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.utm.UTM;

public class Main {
    public static void main(String[] args) {
        // 1. Create a system
        WGS84 estonia = new WGS84();
        // If you want to transform from geodetic to Cartesian, first create geodetic coordinates
        Geodetic<WGS84> geodetic = new Geodetic<>(
                Math2.toRadians(59.436961D),
                Math2.toRadians(24.753575D),
                12.348D);
        // 3. Transform to Cartesian
        Cartesian<WGS84> cartesian = new Cartesian<>(
                geodetic.calculateXcartesian(estonia, geodetic),
                geodetic.calculateYcartesian(geodetic),
                geodetic.calculateZcartesian(estonia, geodetic));
        // Since I've already created Cartesian coordinates, I now can transform it back to geodetic
        // by doing this I ensure, that initial geodetic coordinates and transformed concise, which means
        // that transformation succeeded
        Geodetic<WGS84> geodetic2 = new Geodetic<>(
                cartesian.calculateLatitude(cartesian, estonia),
                cartesian.calculateLongitude(cartesian)
        );
        geodetic2.setHeight(cartesian.calculateHeight(cartesian, geodetic2, estonia));
        UTM<WGS84> utm = new UTM<>(
                geodetic2.calculateTMx(geodetic2, estonia),
                geodetic2.calculateTMy(geodetic2, estonia)
        );
        Geodetic<WGS84> geodetic3 = new Geodetic<>(
                utm.calculateLatitude(utm, estonia),
                utm.calculateLongitude(utm, estonia, 35)
        );
        // Output everything into console
        System.out.println("Major radius of model is " + estonia.majorRadius);
        System.out.println("Minor radius of model is " + estonia.minorRadius);
        System.out.println("Cartesian X is " + cartesian.getX());
        System.out.println("Cartesian Y is " + cartesian.getY());
        System.out.println("Cartesian Z is " + cartesian.getZ());
        System.out.println("Geodetic Latitude is " + Math.toDegrees(geodetic.getLatitude()));
        System.out.println("Geodetic Longitude is " + Math.toDegrees(geodetic.getLongitude()));
        System.out.println("Geodetic Height is " + geodetic.getHeight());
        System.err.println("Geodetic2 Latitude is " + Math.toDegrees(geodetic2.getLatitude()));
        System.err.println("Geodetic2 Longitude is " + Math.toDegrees(geodetic2.getLongitude()));
        System.err.println("Geodetic2 Height is " + geodetic2.getHeight());
        System.err.println("UTM X is " + utm.getX());
        System.err.println("UTM Y is " + utm.getY());
        System.err.println("Geodetic3 X is " + Math2.toDegrees(geodetic3.getLatitude()));
        System.err.println("Geodetic3 Y is " + Math2.toDegrees(geodetic3.getLongitude()));
    }
}
