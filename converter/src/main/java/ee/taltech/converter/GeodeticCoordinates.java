package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;

public interface GeodeticCoordinates<E extends Ellipsoid> {
    default double curvatureRadius(Geodetic<E> point, E ellipsoid) {
        System.out.println(ellipsoid.majorRadius);
        return ellipsoid.majorRadius / (Math.sqrt
                (1 - (Math.pow(ellipsoid.eccentricity, 2) * Math.pow(Math.sin(point.getLatitude()), 2))));
    }

    default double calculateX(E estonia, Geodetic<E> geodetic) {
        geodetic.setCurvatureRadius(curvatureRadius(geodetic, estonia));
        return (geodetic.getCurvatureRadius() + geodetic.getHeight())
                * Math.cos(geodetic.getLatitude()) * Math.cos(geodetic.getLongitude());
    }

    default double calculateY(Geodetic<E> geodetic) {
        return (geodetic.getCurvatureRadius() + geodetic.getHeight())
                * Math.cos(geodetic.getLatitude()) * Math.sin(geodetic.getLongitude());
    }

    default double calculateZ(E estonia, Geodetic<E> geodetic) {
        return ((((Math.pow(estonia.minorRadius, 2)) / (Math.pow(estonia.majorRadius, 2)))
                * geodetic.getCurvatureRadius()) + geodetic.getHeight()) *
                Math.sin(geodetic.getLatitude());
    }
}
