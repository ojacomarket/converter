package ee.taltech.converter;

import ee.taltech.converter.coordinates.Cartesian;
import ee.taltech.converter.coordinates.Geodetic;

public interface CartesianCoordinates<E extends Ellipsoid> {
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

    default double initBeta(E earth, Cartesian<E> point) {
        return ((earth.majorRadius * point.getZ()) /
                (earth.minorRadius * (Math.sqrt(
                        Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2)))));
    }

    default double tanLatitude(E earth, Cartesian<E> point) {
        //tangent to return
        return (point.getZ() + (Math.pow(earth.eccentricity_, 2)
                * earth.minorRadius * Math.pow(Math.sin(point.getBetaAngle()), 3))) /
                ((Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2))) -
                        (earth.majorRadius * Math.pow(earth.eccentricity, 2) *
                                Math.pow(Math.cos(point.getBetaAngle()), 3)));
    }

    default double updateBeta(E earth, double longitude) {
        return (1 - earth.flattening) * Math.tan(longitude);
    }

    default double calculateLatitude(Cartesian<E> point, E earth) {
        // Find initial beta first
        point.setBetaAngle(Math.atan(initBeta(earth, point)));
        double latitude = 0;

        // Reduce errors
        for (int i = 0; i < 10; i++) {
            latitude = Math.atan(tanLatitude(earth, point));
            point.setBetaAngle(Math.atan(updateBeta(earth, latitude)));
        }
        return latitude;
    }

    default double calculateHeight(Cartesian<E> point, Geodetic<E> point2, E earth) {
        point2.setCurvatureRadius(point2.curvatureRadius(point2, earth));
        return ((Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2))) /
                (Math.cos(point2.getLatitude()))) - point2.getCurvatureRadius();
    }
}
