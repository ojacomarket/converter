package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;

/**
 * Interface, that provides functionality to convert Geodetic coordinates into Cartesian coordinates.
 *
 * @param <E> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
public interface GeodeticCoordinates<E extends Ellipsoid> extends Projection<E> {
    /**
     * Method to convert Geodetic coordinate's latitude value into Cartesian coordinate's X value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Geodetic coordinates.
     * @return X value of the surveyed point.
     */
    default double calculateXcartesian(E earth, Geodetic<E> point) {
        point.setCurvatureRadius(curvatureRadius(point, earth));
        return (point.getCurvatureRadius() + point.getHeight())
                * Math.cos(point.getLatitude()) * Math.cos(point.getLongitude());
    }

    /**
     * Method to convert Geodetic coordinate's longitude value into Cartesian coordinate's Y value.
     *
     * @param point Surveyed point in Geodetic coordinates.
     * @return Y value of the surveyed point.
     */
    default double calculateYcartesian(Geodetic<E> point) {
        return (point.getCurvatureRadius() + point.getHeight())
                * Math.cos(point.getLatitude()) * Math.sin(point.getLongitude());
    }

    /**
     * Method to convert Geodetic coordinate's height value into Cartesian coordinate's Z value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Geodetic coordinates.
     * @return Z value of the surveyed point.
     */
    default double calculateZcartesian(E earth, Geodetic<E> point) {
        return ((((Math.pow(earth.minorRadius, 2)) / (Math.pow(earth.majorRadius, 2)))
                * point.getCurvatureRadius()) + point.getHeight()) *
                Math.sin(point.getLatitude());
    }

    /**
     * Method to calculate radius of curvature on prime vertical.
     *
     * @param point Surveyed point in Geodetic coordinates.
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @return Radius of curvature on prime vertical.
     */
    default double curvatureRadius(Geodetic<E> point, E earth) {
        System.out.println(earth.majorRadius);
        return earth.majorRadius / (Math.sqrt
                (1 - (Math.pow(earth.eccentricity, 2) * Math.pow(Math.sin(point.getLatitude()), 2))));
    }
}
