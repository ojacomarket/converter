package ee.taltech.converter;

import ee.taltech.converter.coordinates.Geodetic;
import ee.taltech.converter.utm.UTM;

import java.util.ArrayList;
import java.util.List;

public interface UTMCoordinates<E extends Ellipsoid> {
    private double calculateN(E earth, double latitude) {
        double element1 = earth.majorRadius;
        double element2 = (Math.pow(earth.eccentricity,2)) * Math.pow(Math.sin(latitude), 2);
        System.err.println(" element2 " + element2);

        return element1 / (Math.sqrt(1 - (element2)));
    }

    default double calculateZone(double longitude) {
        // from Greenwich to 180 degrees EAST (Estonia is there ca 24 degrees)
        double greaterValue = 31 + ((180*longitude)/(Math.PI * 6));
        return Math.round(greaterValue); //Estonia is located in [<-- 34 <--> 35 -->] /// UTM Zones
    }
    default double calculateCentralMeridianByLongitude(double longitude) {
        double zone = calculateZone(longitude);
        return ((6 * zone)-183) * (Math.PI/180);
    }
    default double calculateCentralMeridianByZone(double zone) {
        return ((6 * zone)-183) * (Math.PI/180);
    }
    private double calculateLambda (Geodetic<E> point) {
        double centralMeridian = calculateCentralMeridianByLongitude(point.getLongitude());
        double element1 = point.getLongitude();

        return element1 - centralMeridian; //must be in radians
    }
    //ok!!!
    private double calculateT (double latitude) {

        return Math.tan(latitude); //number
    }
    private double calculateEeta(double latitude, E earth) {
        System.out.println("EEEE " + earth.eccentricity_ * (Math.cos(latitude)));
        return earth.eccentricity_ * (Math.cos(latitude));
    }
    default List<Double> calculateA (E earth) {
        // Init List
        List<Double> values = new ArrayList<>();
        // A0 computation

            double element1 = -((Math.pow(earth.eccentricity,2)) / 4);

            double element2 = -((Math.pow(earth.eccentricity,4) * 3) / 64);

            double element3 = -((Math.pow(earth.eccentricity, 6) * 5) / 256);

            double element4 = -((Math.pow(earth.eccentricity, 8) * 175) / 16384);

           double A0 = 1 + (element1) + (element2) + (element3) + (element4);
           values.add(A0);

           // A2 computation

            double element11 = Math.pow(earth.eccentricity,2);

            double element21 = Math.pow(earth.eccentricity, 4) / 4;

            double element31 = (Math.pow(earth.eccentricity, 6) * 15) / 128;

            double element41 = -((Math.pow(earth.eccentricity, 8) * 455) / 4096);

            double A2 = (3 * (element11 + element21 + element31 + element41)) / 8;
        values.add(A2);
            // A4 computation

            double element111 = Math.pow(earth.eccentricity, 4);

            double element211 = (Math.pow(earth.eccentricity, 6) * 3) / 4;

            double element311 = -((Math.pow(earth.eccentricity, 8) * 77) / 128);

           double A4 = (15 * (element111 + element211 + element311)) / 256;
        values.add(A4);

        // A6 computation

            double element1111 = Math.pow(earth.eccentricity, 6);

            double element2111 = -((Math.pow(earth.eccentricity, 8) * 41) / 32);

           double A6 = (35 * (element1111 + element2111)) / 3072;
        values.add(A6);

        // A8 computation

        double A8 = -((Math.pow(earth.eccentricity, 8) * 315) / 131072);
        values.add(A8);

        //filled list to return
        return values;
    }
    default double calculateSf (E earth, double latitude) {
        List<Double> valuesOfA = calculateA(earth);


        double element1 = earth.majorRadius;

        double element2 = (valuesOfA.get(0)) * latitude;

        double element3 = -((valuesOfA.get(1)) * Math.sin(2 * (latitude)));

        double element4 = (valuesOfA.get(2)) * Math.sin(4 * (latitude));

        double element5 = -((valuesOfA.get(3)) * Math.sin(6 * (latitude)));

        double element6 = (valuesOfA.get(4)) * Math.sin(8 * (latitude));

        return element1 * (element2 + element3 + element4 + element5 + element6);
    }
    default double calculateTMx (Geodetic<E> point, E earth) {

          double n = calculateN(earth,point.getLatitude());
          double lambda = calculateLambda(point);
          double t = calculateT(point.getLatitude());
          double eeta = calculateEeta(point.getLatitude(),earth);

            double element1 = n * lambda * Math.cos(point.getLatitude());

            double element21 = n * Math.pow(lambda, 3) * Math.pow(Math.cos(point.getLatitude()), 3);

            double element22 = (1 - Math.pow(t, 2) + Math.pow(eeta, 2));

            double element31 = n * Math.pow(lambda, 5) * Math.pow(Math.cos(point.getLatitude()), 5);

            double element32 = 5 - (18 * Math.pow(t, 2)) + Math.pow(t, 4) + (14 * Math.pow(eeta, 2)) - (58 * Math.pow(t, 2) * Math.pow(eeta, 2));

            return  (element1 + ((element21 * element22) / 6) + ((element31 * element32) / 120));
    }
    default double calculateTMy (Geodetic<E> point, E earth) {
        double n = calculateN(earth,point.getLatitude());
        double lambda = calculateLambda(point);
        double t = calculateT(point.getLatitude());
        double eeta = calculateEeta(point.getLatitude(),earth);
        double sf = calculateSf(earth,point.getLatitude());

        double element2_1 = (n * Math.pow(lambda, 2)) / 2;

        double element2_2 = Math.sin(point.getLatitude()) * Math.cos(point.getLatitude());

        double element3_1 = (n * Math.pow(lambda, 4)) / 24;

        double element3_2 = Math.sin(point.getLatitude()) * Math.pow(Math.cos(point.getLatitude()), 3);

        double element3_3 = 5 - Math.pow(t, 2) + (Math.pow(eeta, 2) * 9) + ((Math.pow(eeta, 4)) * 4);

        double element4_1 = (n * Math.pow(lambda, 6)) / 720;

        double element4_2 = Math.sin(point.getLatitude()) * Math.pow(Math.cos(point.getLatitude()), 5);

        double element4_3 = 61 - (Math.pow(t, 2) * 58) + Math.pow(t, 4) + (Math.pow(eeta, 2) * 270) - (330 * Math.pow(t, 2) * Math.pow(eeta, 2));

        return (sf + ((element2_1) * (element2_2)) + ((element3_1) * (element3_2) * (element3_3)) + ((element4_1) * (element4_2) * (element4_3)));
    }
    default double calculateUTMx (Geodetic<E> point, E earth ) {
        double TMx = calculateTMx(point, earth);
        return (0.9996D * TMx) + 500_000;
    }
    default double calculateUTMy (Geodetic<E> point, E earth) {
        double TMy = calculateTMy(point,earth);
        return 0.9996D * TMy;
    }
    //ok!!!
    private double calculateLatitude2 (UTM<E> point, E earth) {
       // List<Double> valuesOfA = calculateA(earth);
        double latitude = 0D;

        double sf;
        double sf2;
        for (int i = 0; i < 10; i++) {
            if(i == 0) {
                latitude = point.getY() / earth.majorRadius;
            }
            sf = calculateSf(earth, latitude);

            sf2 = calculateSf2(earth, latitude);

            double element1 = sf - point.getY();

            latitude = latitude - ((element1) / (sf2));

        }
        return latitude;
    }
    //ok!!
    private double calculateSf2 (E earth, double latitude) {
        List<Double> valuesOfA = calculateA(earth);
        double element1 = earth.majorRadius;
        double element2 = valuesOfA.get(0);
        double element3 = -(2 * valuesOfA.get(1) * Math.cos(2 * latitude));
        double element4 = 4 * valuesOfA.get(2) * Math.cos(4 * latitude);
        double element5 = -(6 * valuesOfA.get(3) * Math.cos(6 * latitude));
        double element6 = 8 * valuesOfA.get(4) * Math.cos(8 * latitude);

        return element1 * (element2 + element3 + element4 + element5 + element6);
    }
    //okk!!!
    private double calculateR (E earth, double latitude) {
        double element1 = earth.majorRadius;
        double element2 = (1 - (Math.pow(earth.eccentricity,2)));
        double element3 = (element1) * (element2);
        double element4 = Math.pow(earth.eccentricity,2) * Math.pow(Math.sin(latitude), 2);
        double element5 = 1 - (element4);
        double element6 = Math.sqrt(Math.pow((element5), 3));

        return (element3) / (element6);
    }
    private List<Double> calculateB (double latitude, E earth) {
        double t = calculateT(latitude);
        double eeta = calculateEeta(latitude, earth);
        List<Double> values = new ArrayList<>();
            double element1 = 1;
            double element2 = (2 * Math.pow(t,2));
            double element3 = Math.pow(eeta,4);
            double B3 = (element1) + (element2) + (element3);
            values.add(B3);

            double element11 = 5;
            double element21 = 3 * Math.pow(t,2);
            double element31 = Math.pow(eeta,2);
            double element41= -(4 * Math.pow(eeta,4));
            double element51 = -(9 * Math.pow(t,2) * Math.pow(eeta,2));
            double B4 = (element11) + (element21)+ (element31)+ (element41)+ (element51);
            values.add(B4);

            double element111 = 5;
            double element211 = 28 * Math.pow(t,2);
            double element311 = 24 * Math.pow(t,4);
            double element411 = 6 * Math.pow(eeta,2);
            double element511 = 8 * Math.pow(t,2) * Math.pow(eeta,2);
            double B5 = (element111) + (element211)+ (element311)+ (element411)+ (element511);
            values.add(B5);

            double element1111 = 61;
            double element2111 = 90 * Math.pow(t,2);
            double element3111 = 46 * Math.pow(eeta,2);
            double element4111 = 45 * Math.pow(t,4);
            double element5111 = -(252*Math.pow(t,2)*Math.pow(eeta,2));
            double element6111 = -(3 * Math.pow(eeta,4));
            double element7111 = - (66*Math.pow(t,2)*Math.pow(eeta,4));
            double element8111 = -(90*Math.pow(t,4)*Math.pow(eeta,2));
            double element9111 = 225*Math.pow(t,4)*Math.pow(eeta,4);
            double B6 = (element1111)+ (element2111) + (element3111) + (element4111) + (element5111) + (element6111) + (element7111) + (element8111) + (element9111);
            values.add(B6);
        for (double b:values
             ) {
            System.out.println("This is b " + b);
        }
        return values;
    }
    default double calculateLatitude (UTM<E> point, E earth) {
        double latitude = calculateLatitude2(point, earth);
        System.err.println(latitude);
        double t = calculateT(latitude);
        double n = calculateN(earth, latitude);
       //good above
        double r = calculateR(earth, latitude);
        //good above
        List<Double> valuesOfB = calculateB(latitude, earth);

        double element2 = (t * n) / r;
        double element3 = (Math.pow((point.getX() / n),2)) / 2;
        double element4 = - ((Math.pow((point.getX()/n),4) * valuesOfB.get(0)) / 24);
        double element5 = ((Math.pow((point.getX()/n),6))*valuesOfB.get(3)) / 720;
        double element6 = -((element2) * ((element3) + (element4) + (element5)));
        return  (latitude) + (element6);
    }
    default double calculateLongitude (UTM<E> point, E earth, double zone) {
        double latitude = calculateLatitude2(point,earth);
        double n = calculateN(earth, latitude);
        List<Double> valuesOfB = calculateB(latitude,earth);

        double element1 = calculateCentralMeridianByZone(zone);
        double element2 = point.getX() / n;
        double element3 = -((Math.pow((point.getX()/n),3) * valuesOfB.get(0)) / 6);
        double element4 = (Math.pow((point.getX()/n),5) * valuesOfB.get(2)) / 120;
        double element5 = (element2) + (element3) + (element4);
        double element6 = Math2.sec(latitude);
        double element7 = (element6) * (element5);

        return (element1) + (element7);
    }

}

