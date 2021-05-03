package ee.taltech.converter;

import ee.taltech.converter.coordinates.Cartesian;
import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.lambert.LambertEST97;
import ee.taltech.converter.utm.UTM;

public class Main {
    public static void main(String[] args) {
        WGS84 estonia = new WGS84();
        Geodetic<WGS84> geodetic = new Geodetic<>(
                Math2.toRadians(59.436961D),
                Math2.toRadians(24.753575D),
                12.348D);
        Cartesian<WGS84> cartesian = new Cartesian<>(
                geodetic.calculateXcartesian(estonia, geodetic),
                geodetic.calculateYcartesian(geodetic),
                geodetic.calculateZcartesian(estonia, geodetic));
        System.out.println("Cartesian " + cartesian);
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
                Math2.toRadians(59.43696099998488D),
                Math2.toRadians(24.753574999167256D),
                12.348000000230968D
        );
        LambertEST97<WGS84> lambert = new LambertEST97<>(
                geodetic3.calculateX(estonia, geodetic3),
                geodetic3.calculateY(estonia, geodetic3)
        );
        Geodetic<WGS84> geodetic4 = new Geodetic<>(
                lambert.calculateLatitudeLambert(estonia, lambert),
                lambert.calculateLongitudeLambert(estonia, lambert)
        );
        System.out.println("Major radius of model is " + estonia.majorRadius);
        System.out.println("Minor radius of model is " + estonia.minorRadius);
        System.out.println("Cartesian X is " + cartesian.getX());
        System.out.println("Cartesian Y is " + cartesian.getY());
        System.out.println("Cartesian Z is " + cartesian.getZ());
        System.out.println("Geodetic Latitude is " + Math.toDegrees(geodetic.getLatitude()));
        System.out.println("Geodetic Longitude is " + Math.toDegrees(geodetic.getLongitude()));
        System.out.println("Geodetic Height is " + geodetic.getHeight());
        System.out.println("Geodetic2 Latitude is " + Math.toDegrees(geodetic2.getLatitude()));
        System.out.println("Geodetic2 Longitude is " + Math.toDegrees(geodetic2.getLongitude()));
        System.out.println("Geodetic2 Height is " + geodetic2.getHeight());
        System.out.println("TM X is " + utm.getX());
        System.out.println("TM Y is " + utm.getY());
        System.err.println("Lambert without origin X is " + (lambert.getX()));
        System.err.println("Lambert without origin Y is " + (lambert.getY()));
        System.err.println("Lambert X is " + (lambert.getX() + LambertEST97.originX));
        System.err.println("Lambert Y is " + (lambert.getY() + LambertEST97.originY));
        System.err.println("Goedetic Latitude is " + (Math2.toDegrees(geodetic4.getLatitude())));
        System.err.println("Geodetic Longitude is " + (Math2.toDegrees(geodetic4.getLongitude())));
    }
}
