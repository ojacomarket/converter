package ee.taltech.converter;

import lombok.Data;

@Data
public class TransverseMercator_From_UTM_to_Geodetic{
    private final WGS84 tln35 = new WGS84();
    private double UTMx;
    private double UTMy;
    private double lat;
    private double lat1 = UTMy / tln35.getSemi_major_axis_radius();
    private Test testGEODETIC = new Test();
    private double lon;
    private double footprint;
    private double Sf;
    private double Sf2;
    private double A0;
    private double A2;
    private double A4;
    private double A6;
    private double A8;
    private double T1;
    private double Eeta1;
    private double B3;
    private double B4;
    private double B5;
    private double B6;
    private double N1;
    private double R1;
    private UTMzone uzone;

    public void calcLat1 (TransverseMercator_From_Geodetic_to_UTM coords) {
        calcAllA(coords);
        for (int i = 0; i < 10; i++) {
            testGEODETIC.setGeodetic_latitude(lat1);
            calcSf(tln35, testGEODETIC,coords);
            calcSf2(coords);
            double element1 = lat1;
            double element2 = Sf - UTMy;
            double element3 = Sf2;
            lat1 = element1 - ((element2) / (element3));
        }
    }
    public void calcSf2 (TransverseMercator_From_Geodetic_to_UTM geoUTM) {
        double element1 = tln35.getSemi_major_axis_radius();
        double element2 = A0;
        double element3 = -(2 * A2 * Math.cos(2 * lat1));
        double element4 = 4 * A4 * Math.cos(4 * lat1);
        double element5 = -(6 * A6 * Math.cos(6 * lat1));
        double element6 = 8 * A8 * Math.cos(8 * lat1);
        Sf2 = element1 * (element2 + element3 + element4 + element5 + element6);
    }
    public void calcFootprint () {

    }
    public void calcAllA (TransverseMercator_From_Geodetic_to_UTM coords) {
        coords.calcA0(tln35);
        coords.calcA2(tln35);
        coords.calcA4(tln35);
        coords.calcA6(tln35);
        coords.calcA8(tln35);

        A0 = coords.getA0();
        A2 = coords.getA2();
        A4 = coords.getA4();
        A6 = coords.getA6();
        A8 = coords.getA8();

    }
    public void calcSf (WGS84 tln, Place place, TransverseMercator_From_Geodetic_to_UTM coords) {
        coords.calcSf(tln, place);

        Sf = coords.getSf();
    }
    public void calcT1() {
        T1 = Math.tan(lat1);
    }
    public void calcEeta1() {
        // init
       double element1 = tln35.getEccentricity_2_square();
       double element2 = Math.pow(Math.cos(lat1),2);
       Eeta1 = (element1) * (element2);
    }

    public void calcB3 () {
        double element1 = 1;
        double element2 = (2 * Math.pow(T1,2));
        double element3 = Math.pow(Eeta1,2);
        B3 = (element1) + (element2) + (element3);
    }
    public void calcB4 () {
        double element1 = 5;
        double element2 = 3 * Math.pow(T1,2);
        double element3 = Math.pow(Eeta1,2);
        double element4 = -(4 * Math.pow(Eeta1,4));
        double element5 = -(9 * Math.pow(T1,2) * Math.pow(Eeta1,2));
        B4 = (element1) + (element2)+ (element3)+ (element4)+ (element5);
    }

    public void calcB5 () {
        double element1 = 5;
        double element2 = 28 * Math.pow(T1,2);
        double element3 = 24 * Math.pow(T1,4);
        double element4 = 6 * Math.pow(Eeta1,2);
        double element5 = 8 * Math.pow(T1,2) * Math.pow(Eeta1,2);
        B5 = (element1) + (element2)+ (element3)+ (element4)+ (element5);
    }
    public void calcB6 () {
        double element1 = 61;
        double element2 = 90 * Math.pow(T1,2);
        double element3 = 46 * Math.pow(Eeta1,2);
        double element4 = 45 * Math.pow(T1,4);
        double element5 = -(252*Math.pow(T1,2)*Math.pow(Eeta1,2));
        double element6 = -(3 * Math.pow(Eeta1,4));
        double element7 = - (66*Math.pow(T1,2)*Math.pow(Eeta1,4));
        double element8 = -(90*Math.pow(T1,4)*Math.pow(Eeta1,2));
        double element9 = 225*Math.pow(T1,4)*Math.pow(Eeta1,4);
        B6 = (element1)+ (element2) + (element3) + (element4) + (element5) + (element6) + (element7) + (element8) + (element9);
    }
    public void calcN1 () {
        double element1 = tln35.getSemi_major_axis_radius();
        double element2 = tln35.getEccentricity_square() * Math.pow(Math.sin(lat1),2);
        double element3 = 1 - (element2);
        double element4 = Math.sqrt(element3);
        N1 = (element1) / (element4);
    }
    public void calcR1() {
        double element1 = tln35.getSemi_major_axis_radius();
        double element2 = (1 - (tln35.getEccentricity_square()));
        double element3 = (element1) * (element2);
        double element4 = tln35.getEccentricity_square() * Math.pow(Math.sin(lat1),2);
        double element5 = 1 - (element4);
        double element6 = Math.sqrt(Math.pow((element5), 3));
        R1 = (element3) / (element6);
    }
    public void calcLat () {
        double element1 = lat1;
        double element2 = (T1 * N1) / R1;
        double element3 = (Math.pow((UTMx / N1),2)) / 2;
        double element4 = - ((Math.pow((UTMx/N1),4) * B4) / 24);
        double element5 = ((Math.pow((UTMx/N1),6))*B6) / 720;
        double element6 = -((element2) * ((element3) + (element4) + (element5)));
        lat = (element1) + (element6);
    }
    public void calcLon () {
        //ok 100%
        double element1 = uzone.getCentral_meridian();
        double element2 = UTMx / N1;
        double element3 = -((Math.pow((UTMx/N1),3) * B3) / 6);
        double element4 = (Math.pow((UTMx/N1),5) * B5) / 120;
        double element5 = (element2) + (element3) + (element4);
        double element6 = Math2.sec(lat1);
        double element7 = (element6) * (element5);

        lon = (element1) + (element7);
    }
public void init (TransverseMercator_From_UTM_to_Geodetic utmtogeo, TransverseMercator_From_Geodetic_to_UTM utm,
                  UTMzone u35, Kaev_Left kaev) {
    utmtogeo.UTMx = utm.getX();
    utmtogeo.UTMy = utm.getY();
    utmtogeo.uzone = utm.getUzone();

    utmtogeo.calcLat1(utm);
    utmtogeo.calcT1();
    utmtogeo.calcEeta1();
    utmtogeo.calcN1();
    utmtogeo.calcR1();
    utmtogeo.calcB3();
    utmtogeo.calcB4();
    utmtogeo.calcB5();
    utmtogeo.calcB6();

    utmtogeo.calcLat();
    utmtogeo.calcLon();
}
    public static void main(String[] args) {
        TransverseMercator_From_UTM_to_Geodetic utmtogeo = new TransverseMercator_From_UTM_to_Geodetic();
        TransverseMercator_From_Geodetic_to_UTM utm = new TransverseMercator_From_Geodetic_to_UTM();
        UTMzone u35 = new UTMzone();
        Kaev_Left kaev = new Kaev_Left();

        utm.init(utm, utmtogeo.tln35, kaev, u35);
        utmtogeo.init(utmtogeo,utm,u35,kaev);

        System.out.println("Latitude is " + utmtogeo.lat * 180/Math.PI);
        System.out.println("Longitude is " + utmtogeo.lon * 180/Math.PI);
    }
}
