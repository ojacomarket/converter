package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.utm.UTM;

import java.util.List;

public interface GeodeticCoordinates<E extends Ellipsoid> extends Projection<E>{
    default double curvatureRadius(Geodetic<E> point, E ellipsoid) {
        System.out.println(ellipsoid.majorRadius);
        return ellipsoid.majorRadius / (Math.sqrt
                (1 - (Math.pow(ellipsoid.eccentricity, 2) * Math.pow(Math.sin(point.getLatitude()), 2))));
    }

    default double calculateXcartesian(E estonia, Geodetic<E> geodetic) {
        geodetic.setCurvatureRadius(curvatureRadius(geodetic, estonia));
        return (geodetic.getCurvatureRadius() + geodetic.getHeight())
                * Math.cos(geodetic.getLatitude()) * Math.cos(geodetic.getLongitude());
    }

    default double calculateYcartesian(Geodetic<E> geodetic) {
        return (geodetic.getCurvatureRadius() + geodetic.getHeight())
                * Math.cos(geodetic.getLatitude()) * Math.sin(geodetic.getLongitude());
    }

    default double calculateZcartesian(E estonia, Geodetic<E> geodetic) {
        return ((((Math.pow(estonia.minorRadius, 2)) / (Math.pow(estonia.majorRadius, 2)))
                * geodetic.getCurvatureRadius()) + geodetic.getHeight()) *
                Math.sin(geodetic.getLatitude());
    }
}
