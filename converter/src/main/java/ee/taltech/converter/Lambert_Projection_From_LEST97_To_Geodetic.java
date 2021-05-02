/*
package ee.taltech.converter;

import lombok.Getter;

@Getter
public class Lambert_Projection_From_LEST97_To_Geodetic {
    private double lon;
    private double lat;
    private double x;
    private double y;
    private double fixed_parallel;
    private double first_parallel;
    private double second_parallel;
    private double fixed_central_meridian;
    private double L;
    private double r0;
    private double K;
    private double N1;
    private double N2;
    private double q;
    private double q0;
    private double q1;
    private double q2;
    private double teta;
    private double r;
    private double func;
    private double func1;

    public void calcTeta () {
        double element1 = r0 - y;
        double element2 = x / (element1);
        teta = Math.atan((element2));
    }

    public void calcR () {
        double element1 = r0 - y;
        double element2 = Math.cos(teta);
        r = (element1) / (element2);
    }

    public void calcQ () {
        double element1 = K / r;
        double element2 = Math2.ln((element1));
        q = (element2) / L;
    }
    public void calcLon() {
        double element1 = teta / L;
        lon = (element1) + fixed_central_meridian;
    }

    public void calcFunc (double angle, WGS84 earth) {
        double element1 = -(q);
        double element2 = 1 + Math.sin(angle);
        double element3 = 1 - Math.sin(angle);
        double element4 = (element2) / (element3);
        double element5 = 1 - (earth.getEccentricity() * Math.sin(angle));
        double element6 = 1 + (earth.getEccentricity() * Math.sin(angle));
        double element7 = (element5) / (element6);
        double element8 = Math.pow((element7),earth.getEccentricity());
        double element9 = (element4) * (element8);
        double element10 = Math2.ln((element9));
        double element11 = (element10) / 2;
        func = (element1) + (element11);
    }

    public void calcFunc1 (double angle, WGS84 earth) {
        double element1 = 1 - (earth.getEccentricity_square());
        double element2 = 1 - (earth.getEccentricity_square() * Math.pow(Math.sin(angle),2));
        double element3 = Math.cos(angle) * (element2);
        func1 = (element1) / (element3);
    }
    public void initLat () {
        double element1 = 2 * Math.atan(Math.pow(Math.exp(1),q));
        lat = (element1) - (Math.PI/2);
    }
    public void calcLat (WGS84 earth) {
        for (int i = 0; i < 20; i++) {
            calcFunc(lat, earth);
            calcFunc1(lat, earth);
            lat = lat - (func/func1);
            System.out.println(lat);
        }
    }

    public static void main(String[] args) {
        Lambert_Projection_From_LEST97_To_Geodetic geoLest = new Lambert_Projection_From_LEST97_To_Geodetic();
        Lambert_Projection_From_Geodetic_To_LEST97 lest = new Lambert_Projection_From_Geodetic_To_LEST97();
        Kaev_Left lm = new Kaev_Left();
        Estonia_LEST97 est = new Estonia_LEST97();
        WGS84 earth = new WGS84();
        UTMzone zone = new UTMzone();
//ok
        lest.init(lest,lm,est,earth,zone);
        geoLest.x = lest.getX();
        geoLest.y = lest.getY();
//ok
        geoLest.fixed_parallel = lest.getRandom_parallel();
        geoLest.first_parallel = lest.getF_parallel();
        geoLest.second_parallel = lest.getS_parallel();
        geoLest.fixed_central_meridian = lest.getFixed_central_meridian();

//ok
        geoLest.L = lest.getL();
//ok
        geoLest.r0 = lest.getR0();
//ok
        geoLest.K = lest.getK();
//ok
        geoLest.N1 = lest.getN1();
//ok
        geoLest.N2 = lest.getN2();

        geoLest.q0 = lest.getQ0();
//ok
        geoLest.q1 = lest.getQ1();
//ok
        geoLest.q2 = lest.getQ2();

//ok
        geoLest.calcTeta();
//ok
        geoLest.calcR();
//ok
        geoLest.calcQ();
//ok
        geoLest.calcLon();
        geoLest.initLat();
        System.out.println(geoLest.lat);
        geoLest.calcLat(earth);
        System.out.println("Latitude " + Math2.toDegrees(geoLest.lat));
        System.out.println("Longitude " + Math2.toDegrees(geoLest.lon));


    }
}
*/
