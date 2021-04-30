package ee.taltech.converter;

import lombok.Data;

@Data
public class TransverseMercator_From_Geodetic_to_UTM {
    private double x;
    private double y;
    private double N;
    private double lambda;
    private double t;
    private double eeta;
    private double Sf;
    private double A0;
    private double A2;
    private double A4;
    private double A6;
    private double A8;
    private double utmx;
    private double utmy;
    private UTMzone uzone;


    //ok 100% ---> checked!!!
    public void computeN(Place liiklusMark, WGS84 earth) {
        //ok 100%
        double element1 = earth.getSemi_major_axis_radius();
        //ok 100%
        double element2 = earth.getEccentricity_square() * Math.pow(Math.sin(liiklusMark.getGeodetic_latitude()), 2);
        //ok 100%
        N = element1 / (Math.sqrt(1 - (element2)));
    }

    //ok 100% ---> checked!!!
    public void computeLambda(Place liiklusMark, UTMzone zone) {
        //ok 100%
        zone.calcZone((Kaev_Left) liiklusMark);
        //ok 100%
        zone.calcCentralMeridian((Kaev_Left) liiklusMark);
        //ok 100%
        double element1 = liiklusMark.getGeodetic_longitude();
        //ok 100%
        double element2 = zone.getCentral_meridian();
        //ok 100%
        lambda = element1 - element2; //must be in radians
    }

    //ok 100% ---> checked!!!
    public void calculateT(Place liiklusMark) {
        //ok 100%
        t = Math.tan(liiklusMark.getGeodetic_latitude()); //number
    }

    //ok 100% ---> checked!!!
    public void calculateEeta(WGS84 earth, Place liiklusMark) {
        //ok 100%
        eeta = (Math.sqrt(earth.getEccentricity_2_square())) * (Math.cos(liiklusMark.getGeodetic_latitude())); //number
    }

    //ok 100% ---> checked!!!
    public void calcSf(WGS84 earth, Place place) {
        //ok 100%
        double element1 = earth.getSemi_major_axis_radius();
        //ok 100%
        double element2 = (A0) * place.getGeodetic_latitude();
        //ok 100%
        double element3 = -((A2) * Math.sin(2 * (place.getGeodetic_latitude())));
        //ok 100%
        double element4 = (A4) * Math.sin(4 * (place.getGeodetic_latitude()));
        //ok 100%
        double element5 = -((A6) * Math.sin(6 * (place.getGeodetic_latitude())));
        //ok 100%
        double element6 = (A8) * Math.sin(8 * (place.getGeodetic_latitude()));
        //ok 100%
        Sf = element1 * (element2 + element3 + element4 + element5 + element6);

    }

    //ok 100% ---> checked!!!
    public void calcA0(WGS84 earth) {
        //ok 100%
        double element1 = -(earth.getEccentricity_square() / 4);
        //ok 100%
        double element2 = -((Math.pow(earth.getEccentricity(), 4) * 3) / 64);
        //ok 100%
        double element3 = -((Math.pow(earth.getEccentricity(), 6) * 5) / 256);
        //ok 100%
        double element4 = -((Math.pow(earth.getEccentricity(), 8) * 175) / 16384);
        //ok 100%
        A0 = 1 + (element1) + (element2) + (element3) + (element4);
    }


    //ok 100% ---> checked!!!
    public void calcA2(WGS84 earth) {
        //ok 100%
        double element1 = earth.getEccentricity_square();
        //ok 100%
        double element2 = Math.pow(earth.getEccentricity(), 4) / 4;
        //ok 100%
        double element3 = (Math.pow(earth.getEccentricity(), 6) * 15) / 128;
        //ok 100%
        double element4 = -((Math.pow(earth.getEccentricity(), 8) * 455) / 4096);
        //ok 100%
        A2 = (3 * (element1 + element2 + element3 + element4)) / 8;
    }

    //ok 100% ---> checked!!!
    public void calcA4(WGS84 earth) {
        //ok 100%
        double element1 = Math.pow(earth.getEccentricity(), 4);
        //ok 100%
        double element2 = (Math.pow(earth.getEccentricity(), 6) * 3) / 4;
        //ok 100%
        double element3 = -((Math.pow(earth.getEccentricity(), 8) * 77) / 128);
        //ok 100%
        A4 = (15 * (element1 + element2 + element3)) / 256;
    }

    //ok 100% ---> checked!!!
    public void calcA6(WGS84 earth) {
        //ok 100%
        double element1 = Math.pow(earth.getEccentricity(), 6);
        //ok 100%
        double element2 = -((Math.pow(earth.getEccentricity(), 8) * 41) / 32);
        //ok 100%
        A6 = (35 * (element1 + element2)) / 3072;
    }

    //ok 100% ---> checked!!!
    public void calcA8(WGS84 earth) {
        //ok 100%
        double element1 = -((Math.pow(earth.getEccentricity(), 8) * 315) / 131072);
        //ok 100%
        A8 = element1;
    }

    //ok 100% ---> checked!!!
    public void calculateX(Place liiklusMark) {
        // element 1
        //ok 100%

        double element1 = N * lambda * Math.cos(liiklusMark.getGeodetic_latitude());
        // element 2
        //ok 100%

        double element2_1 = N * Math.pow(lambda, 3) * Math.pow(Math.cos(liiklusMark.getGeodetic_latitude()), 3);
        //ok 100%
        double element2_2 = (1 - Math.pow(t, 2) + Math.pow(eeta, 2));

        // element 3
        //ok 100%
        double element3_1 = N * Math.pow(lambda, 5) * Math.pow(Math.cos(liiklusMark.getGeodetic_latitude()), 5);
        //ok 100%
        double element3_2 = 5 - (18 * Math.pow(t, 2)) + Math.pow(t, 4) + (14 * Math.pow(eeta, 2)) - (58 * Math.pow(t, 2) * Math.pow(eeta, 2));

        //ok 100%
        x = element1 + ((element2_1 * element2_2) / 6) + ((element3_1 * element3_2) / 120);
    }

    //ok 100% ---> checked!!!
    public void calculateY(Place liiklusMark) {
        //ok 100%
        double element1 = Sf;
        //ok 100%
        double element2_1 = (N * Math.pow(lambda, 2)) / 2;
        //ok 100%
        double element2_2 = Math.sin(liiklusMark.getGeodetic_latitude()) * Math.cos(liiklusMark.getGeodetic_latitude());
        //ok 100%
        double element3_1 = (N * Math.pow(lambda, 4)) / 24;
        //ok 100%
        double element3_2 = Math.sin(liiklusMark.getGeodetic_latitude()) * Math.pow(Math.cos(liiklusMark.getGeodetic_latitude()), 3);
        //ok 100%
        double element3_3 = 5 - Math.pow(t, 2) + (Math.pow(eeta, 2) * 9) + ((Math.pow(eeta, 4)) * 4);
        // error found, was Math.pow(lambda,4), but have to be Math.pow(lambda,6), now is ok 100%
        double element4_1 = (N * Math.pow(lambda, 6)) / 720;
        //ok 100%
        double element4_2 = Math.sin(liiklusMark.getGeodetic_latitude()) * Math.pow(Math.cos(liiklusMark.getGeodetic_latitude()), 5);
        //ok 100%
        double element4_3 = 61 - (Math.pow(t, 2) * 58) + Math.pow(t, 4) + (Math.pow(eeta, 2) * 270) - (330 * Math.pow(t, 2) * Math.pow(eeta, 2));
        //ok 100%
        y = element1 + ((element2_1) * (element2_2)) + ((element3_1) * (element3_2) * (element3_3)) + ((element4_1) * (element4_2) * (element4_3));
    }

    public void calcUTMx() {
        utmx = (0.9996D * x) + 500_000;
    }

    public void calcUTMy() {
        utmy = 0.9996D * y; //Northen Hemisphere (EST)
    }
    public void init(TransverseMercator_From_Geodetic_to_UTM coords, WGS84 laagna, Place lm, UTMzone tln35) {

        //init
        coords.computeN(lm, laagna); //good

        coords.computeLambda(lm, tln35); //good
        coords.calculateT(lm); //good
        coords.calculateEeta(laagna, lm); //good

        coords.calcA0(laagna); //good
        coords.calcA2(laagna); // good
        coords.calcA4(laagna); // good
        coords.calcA6(laagna); // good
        coords.calcA8(laagna); // good
        coords.calcSf(laagna, lm); //good

        //compute UTM x,y for lm on Laagna, TLN
        coords.calculateX(lm); //
        coords.calculateY(lm); //
        coords.calcUTMx();
        coords.calcUTMy();
        uzone = tln35;

    }

    public static void main(String[] args) {

        TransverseMercator_From_Geodetic_to_UTM coords = new TransverseMercator_From_Geodetic_to_UTM();
        WGS84 laagna = new WGS84();
        Kaev_Left lm = new Kaev_Left();
        UTMzone tln35 = new UTMzone();

        coords.init(coords, laagna, lm, tln35);
        /*System.err.println("TM X-coordinate is: " + coords.x);
        System.err.println("TM Y-coordinate is: " + coords.y);*/

        System.out.println("UTM X-coordinate is: " + coords.utmx);
        System.out.println("UTM Y-coordinate is: " + coords.utmy);

    }
}
