package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.coordinates.LambertEST97;
import ee.taltech.converter.lib.Math2;

/**
 * Interface, that provides functionality to convert Geodetic coordinates into Lambert EST97 projection
 * coordinates and vice versa.
 *
 * @param <E> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
public interface Projection<E extends Ellipsoid> extends UTMCoordinates<E> {
    /**
     * Method used to calculate Lambert EST97 projection's coordinate X value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Geodetic coordinates.
     * @return Lambert EST97 projection's coordinate X value.
     */
    default double calculateXLambert(E earth, Geodetic<E> point) {
        double L = calculateL(earth);
        double R = calculateR(earth, point.getLatitude());
        double lambda = calculateLambda2(point.getLongitude());
        return (R * Math.sin(L * lambda));
    }

    /**
     * Method used to calculate Lambert EST97 projection's coordinate Y value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Geodetic coordinates.
     * @return Lambert EST97 projection's coordinate Y value.
     */
    default double calculateYLambert(E earth, Geodetic<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);
        double R = calculateR(earth, point.getLatitude());
        double L = calculateL(earth);
        double lambda = calculateLambda2(point.getLongitude());
        return R0 - (R * Math.cos(L * lambda));
    }

    /**
     * Method used to calculate Geodetic coordinate's latitude value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Lambert EST97 projection coordinates.
     * @return Geodetic coordinates latitude value.
     */
    default double calculateLongitudeLambert(E earth, LambertEST97<E> point) {
        double L = calculateL(earth);
        double Teta = calculateTeta(earth, point);
        double element1 = Teta / L;
        return (element1) + LambertEST97.fixedCentralMeridian;
    }

    /**
     * Method used to calculate Geodetic coordinate's longitude value.
     *
     * @param earth Ellipsoid model, that is currently in use (typically WGS84).
     * @param point Surveyed point in Lambert EST97 projection coordinates.
     * @return Geodetic coordinates longitude value.
     */
    default double calculateLatitudeLambert(E earth, LambertEST97<E> point) {
        double function;
        double function2;
        double latitude = firstIteration(earth, point);
        for (int i = 0; i < 10; i++) {
            function = calculateFunction(point, earth, latitude);
            function2 = calculateFunction2(latitude, earth);
            latitude = latitude - (function / function2);
        }
        return latitude;
    }

    //region utility methods
    private double calculateFunction(LambertEST97<E> point, E earth, double latitude) {
        double Q = calculateQ2(earth, point);
        double element1 = -(Q);
        double element2 = 1 + Math.sin(latitude);
        double element3 = 1 - Math.sin(latitude);
        double element4 = (element2) / (element3);
        double element5 = 1 - (earth.eccentricity * Math.sin(latitude));
        double element6 = 1 + (earth.eccentricity * Math.sin(latitude));
        double element7 = (element5) / (element6);
        double element8 = Math.pow((element7), earth.eccentricity);
        double element9 = (element4) * (element8);
        double element10 = Math2.ln((element9));
        double element11 = (element10) / 2;
        return (element1) + (element11);
    }

    private double calculateFunction2(double latitude, E earth) {
        double element1 = 1 - (Math.pow(earth.eccentricity, 2));
        double element2 = 1 - (Math.pow(earth.eccentricity_, 2) * Math.pow(Math.sin(latitude), 2));
        double element3 = Math.cos(latitude) * (element2);
        return (element1) / (element3);
    }

    private double firstIteration(E earth, LambertEST97<E> point) {
        double Q = calculateQ2(earth, point);
        double element1 = 2 * Math.atan(Math.pow(Math.exp(1), Q));
        return (element1) - (Math.PI / 2);
    }

    private double calculateTeta(E earth, LambertEST97<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);
        double element1 = R0 - point.getY();
        double element2 = point.getX() / (element1);
        return Math.atan((element2));
    }

    private double calculateR2(E earth, LambertEST97<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);

        double Teta = calculateTeta(earth, point);
        double element1 = R0 - point.getY();
        double element2 = Math.cos(Teta);
        return (element1) / (element2);
    }

    private double calculateQ2(E earth, LambertEST97<E> point) {
        double K = calculateK(earth);

        double R = calculateR2(earth, point);
        System.out.println("R " + R);
        double L = calculateL(earth);
        double element1 = K / R;
        double element2 = Math2.ln((element1));
        return (element2) / L;
    }

    private double calculateLambda2(double longitude) {
        return longitude - LambertEST97.fixedCentralMeridian;
    }

    private double calculateQ(double latitude, E earth) {
        double element1 = Math.PI / 4;
        double element2 = latitude / 2;
        double element3 = (element1) + (element2);
        double element4 = Math.tan((element3));
        double element5 = 1 - (earth.eccentricity * Math.sin(latitude));
        double element6 = 1 + (earth.eccentricity * Math.sin(latitude));
        double element7 = (element5) / (element6);
        double element8 = Math.sqrt(Math.pow(element7, earth.eccentricity));
        double element9 = (element4) * (element8);
        return Math2.ln((element9));
    }

    private double calculateL(E earth) {
        double N1 = calculateN(earth, LambertEST97.firstParallel);
        double N2 = calculateN(earth, LambertEST97.secondParallel);
        double element1 = N1 * Math.cos(LambertEST97.firstParallel);
        double q1 = calculateQ(LambertEST97.firstParallel, earth);
        double q2 = calculateQ(LambertEST97.secondParallel, earth);
        double element2 = Math2.ln((element1));
        double element3 = N2 * Math.cos(LambertEST97.secondParallel);
        double element4 = Math2.ln((element3));
        double element5 = (element2) - (element4);
        double element6 = q2 - q1;
        return (element5) / (element6);
    }

    private double calculateK(E earth) {
        double L = calculateL(earth);
        double N1 = calculateN(earth, LambertEST97.firstParallel);
        double q1 = calculateQ(LambertEST97.firstParallel, earth);
        double element1 = N1 * Math.cos(LambertEST97.firstParallel);
        double element2 = -(L * q1);
        double element3 = Math.pow(Math.exp(1), (element2));
        double element4 = L * (element3);
        return (element1) / (element4);
    }

    private double calculateR(E earth, double latitude) {
        double L = calculateL(earth);
        double K = calculateK(earth);
        double Q = calculateQ(latitude, earth);
        double element1 = -(L * Q);
        double element2 = Math.pow(Math.exp(1), (element1));
        return K * (element2);
    }
    //endregion
}
