package ee.taltech.converter;

import ee.taltech.converter.coordinates.Cartesian;
import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.coordinates.LambertEST97;
import ee.taltech.converter.lib.Math2;
import ee.taltech.converter.coordinates.UTM;

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
        Geodetic<WGS84> geodetic2 = new Geodetic<>(
                cartesian.calculateLatitude(cartesian, estonia),
                cartesian.calculateLongitude(cartesian)
        );
        geodetic2.setHeight(cartesian.calculateHeight(cartesian, geodetic2, estonia));

        UTM<WGS84> utm = new UTM<>(
                geodetic2.calculateTMx(geodetic2, estonia),
                geodetic2.calculateTMy(geodetic2, estonia)
        );
        UTM<WGS84> utm2 = new UTM<>(
                geodetic2.calculateUTMx(geodetic2, estonia),
                geodetic2.calculateUTMy(geodetic2, estonia)
        );
        Geodetic<WGS84> geodetic3 = new Geodetic<>(
                utm.calculateLatitude(utm, estonia),
                utm.calculateLongitude(utm, estonia, 35)
        );
        LambertEST97<WGS84> lambert = new LambertEST97<>(
                geodetic3.calculateXLambert(estonia, geodetic3),
                geodetic3.calculateYLambert(estonia, geodetic3)
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
        System.out.println("UTM X is " + utm2.getX());
        System.out.println("UTM Y is " + utm2.getY());
        System.out.println("Lambert without origin X is " + (lambert.getX()));
        System.out.println("Lambert without origin Y is " + (lambert.getY()));
        System.out.println("Lambert X is " + (lambert.getX() + LambertEST97.originX));
        System.out.println("Lambert Y is " + (lambert.getY() + LambertEST97.originY));
        System.out.println("Geodetic Latitude is " + (Math2.toDegrees(geodetic4.getLatitude())));
        System.out.println("Geodetic Longitude is " + (Math2.toDegrees(geodetic4.getLongitude())));
    }
}
