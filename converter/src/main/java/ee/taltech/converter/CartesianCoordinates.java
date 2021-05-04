package ee.taltech.converter;

import ee.taltech.converter.coordinates.Cartesian;
import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.lib.Math2;

/**
 * Interface, that provides functionality to convert Cartesian coordinates into Geodetic coordinates.
 *
 * @param <E> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
public interface CartesianCoordinates<E extends Ellipsoid> {
    /**
     * Method to convert Cartesian coordinate's X value into Geodetic coordinate's latitude.
     *
     * @param point Surveyed point in Cartesian coordinates.
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @return Latitude value of the surveyed point.
     */
    default double calculateLatitude(Cartesian<E> point, E earth) {
        point.setBetaAngle(Math.atan(initBeta(earth, point)));
        double latitude = 0;
        for (int i = 0; i < 10; i++) {
            latitude = Math.atan(tanLatitude(earth, point));
            point.setBetaAngle(Math.atan(updateBeta(earth, latitude)));
        }
        return latitude;
    }

    /**
     * Method to convert Cartesian coordinate's Y value into Geodetic coordinate's longitude.
     *
     * @param point Surveyed point in Cartesian coordinates.
     * @return Longitude value of the surveyed point.
     */
    default double calculateLongitude(Cartesian<E> point) {
        if (point.getX() != 0) {
            return Math.atan(point.getY() / point.getX());
        } else if (point.getY() > 0) {
            return Math2.toRadians(90D);
        } else if (point.getY() < 0) {
            return Math2.toRadians(-90D);
        } else {
            return 0D;
        }
    }

    /**
     * Method to convert Cartesian coordinate's Z value into Geodetic coordinate's height.
     *
     * @param point  Surveyed point in Cartesian coordinates.
     * @param point2 Point in geodetic coordinates, which has latitude and longitude known.
     * @param earth  Ellipsoid model, that is currently in use (typically WGS84).
     * @return Height value of the surveyed point.
     */
    default double calculateHeight(Cartesian<E> point, Geodetic<E> point2, E earth) {
        point2.setCurvatureRadius(point2.curvatureRadius(point2, earth));
        return ((Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2))) /
                (Math.cos(point2.getLatitude()))) - point2.getCurvatureRadius();
    }

    //region utility methods
    private double initBeta(E earth, Cartesian<E> point) {
        return ((earth.majorRadius * point.getZ()) /
                (earth.minorRadius * (Math.sqrt(
                        Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2)))));
    }

    private double updateBeta(E earth, double longitude) {
        return (1 - earth.flattening) * Math.tan(longitude);
    }

    private double tanLatitude(E earth, Cartesian<E> point) {
        return (point.getZ() + (Math.pow(earth.eccentricity_, 2)
                * earth.minorRadius * Math.pow(Math.sin(point.getBetaAngle()), 3))) /
                ((Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2))) -
                        (earth.majorRadius * Math.pow(earth.eccentricity, 2) *
                                Math.pow(Math.cos(point.getBetaAngle()), 3)));
    }
    //endregion
}
