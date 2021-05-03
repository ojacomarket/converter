package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.lambert.LambertEST97;

public interface Projection<E extends Ellipsoid> extends UTMCoordinates<E> {
    default double calculateLambda(double longitude, double fixedCentralMeridian) {
        return longitude - fixedCentralMeridian;
    }

    default double calculateQ(double latitude, E earth) {
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

    default double calculateL(E earth) {
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

    default double calculateK(E earth) {
        double L = calculateL(earth);
        double N1 = calculateN(earth, LambertEST97.firstParallel);
        double q1 = calculateQ(LambertEST97.firstParallel, earth);
        double element1 = N1 * Math.cos(LambertEST97.firstParallel);
        double element2 = -(L * q1);
        double element3 = Math.pow(Math.exp(1), (element2));
        double element4 = L * (element3);
        return (element1) / (element4);
    }

    default double calculateR(E earth, double latitude) {
        double L = calculateL(earth);
        double K = calculateK(earth);
        double Q = calculateQ(latitude, earth);
        double element1 = -(L * Q);
        double element2 = Math.pow(Math.exp(1), (element1));
        return K * (element2);
    }

    default double calculateX(E earth, Geodetic<E> point) {
        double L = calculateL(earth);
        double R = calculateR(earth, point.getLatitude());
        double lambda = calculateLambda(point.getLongitude(), LambertEST97.fixedCentralMeridian);
        return (R * Math.sin(L * lambda));
    }

    default double calculateY(E earth, Geodetic<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);
        double R = calculateR(earth, point.getLatitude());
        double L = calculateL(earth);
        double lambda = calculateLambda(point.getLongitude(), LambertEST97.fixedCentralMeridian);
        return R0 - (R * Math.cos(L * lambda));
    }

    default double calculateTeta(E earth, LambertEST97<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);
        double element1 = R0 - point.getY();
        double element2 = point.getX() / (element1);
        return Math.atan((element2));
    }

    default double calculateR2(E earth, LambertEST97<E> point) {
        double R0 = calculateR(earth, LambertEST97.belowFirstParallel);

        double Teta = calculateTeta(earth, point);
        double element1 = R0 - point.getY();
        double element2 = Math.cos(Teta);
        return (element1) / (element2);
    }

    default double calculateQ2(E earth, LambertEST97<E> point) {
        double K = calculateK(earth);

        double R = calculateR2(earth, point);
        System.out.println("R " + R);
        double L = calculateL(earth);
        double element1 = K / R;
        double element2 = Math2.ln((element1));
        return (element2) / L;
    }

    default double calculateLongitudeLambert(E earth, LambertEST97<E> point) {
        double L = calculateL(earth);
        double Teta = calculateTeta(earth, point);
        double element1 = Teta / L;
        return (element1) + LambertEST97.fixedCentralMeridian;
    }

    default double calculateFunction(LambertEST97<E> point, E earth, double latitude) {
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

    default double calculateFunction2(double latitude, E earth) {
        double element1 = 1 - (Math.pow(earth.eccentricity, 2));
        double element2 = 1 - (Math.pow(earth.eccentricity_, 2) * Math.pow(Math.sin(latitude), 2));
        double element3 = Math.cos(latitude) * (element2);
        return (element1) / (element3);
    }

    default double firstIteration(E earth, LambertEST97<E> point) {
        double Q = calculateQ2(earth, point);
        double element1 = 2 * Math.atan(Math.pow(Math.exp(1), Q));
        return (element1) - (Math.PI / 2);
    }

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
}
