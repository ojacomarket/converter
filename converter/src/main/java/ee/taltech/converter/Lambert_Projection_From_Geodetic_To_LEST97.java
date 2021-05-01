package ee.taltech.converter;

import lombok.Getter;

@Getter
public class Lambert_Projection_From_Geodetic_To_LEST97 {
    private double xTrue;
    private double yTrue;
    private final double initialX = 500_000D;
    private final double initialY = 6_375_000D;
    private double x;
    private double y;
    private double geodetic_latitude;
    private double geodetic_longitude;
    private final double fixed_central_meridian = Math2.toRadians(24D);
    private double K;
    private double Kbackup;
    private double L;
    private double f_parallel;
    private double s_parallel;
    // should be less than f_parallel;
    private final double random_parallel = Math2.toRadians(57.51755393D);
    private double N1;
    private double N2;
    private double r;
    private double r0;
    private double q;
    private double q0;
    private double q1;
    private double q2;
    private double lambda;

    //ok 100% --> checked!!!
    public void calcN1(WGS84 earth) {
        //ok 100%
        double element1 = earth.getSemi_major_axis_radius();
        //ok 100%
        double element2 = earth.getEccentricity_square();
        //ok 100%
        double element3 = (element2) * Math.pow(Math.sin(f_parallel), 2);
        //ok 100%
        double element4 = Math.sqrt(1 - (element3));
        //ok 100%
        N1 = (element1) / (element4);
    }

    // copy of N1, just change from 1-->2
    //ok 100% --> checked!!!
    public void calcN2(WGS84 earth) {
        //ok 100%
        double element1 = earth.getSemi_major_axis_radius();
        //ok 100%
        double element2 = earth.getEccentricity_square();
        //ok 100%
        double element3 = (element2) * Math.pow(Math.sin(s_parallel), 2);
        //ok 100%
        double element4 = Math.sqrt(1 - (element3));
        //ok 100%
        N2 = (element1) / (element4);
    }
    //ok 100% --> checked!!!
    public void calcQ(WGS84 earth) {
        //ok 100%
        double element1 = Math.PI / 4;
        //ok 100%
        double element2 = geodetic_latitude / 2;
        //ok 100%
        double element3 = (element1) + (element2);
        //ok 100%
        double element4 = Math.tan((element3));
        //ok 100%
        double element5 = 1 - (earth.getEccentricity() * Math.sin(geodetic_latitude));
        //ok 100%
        double element6 = 1 + (earth.getEccentricity() * Math.sin(geodetic_latitude));
        //ok 100%
        double element7 = (element5) / (element6);
        //ok 100%
        double element8 = Math.sqrt(Math.pow(element7, earth.getEccentricity()));
        //ok 100%
        double element9 = (element4) * (element8);
        //ok 100%
        q = Math2.ln((element9));
    }
    //ok 100% --> checked!!!
    public void calcQ1(WGS84 earth) {
        //ok 100%
        double element1 = Math.PI / 4;
        //ok 100%
        double element2 = f_parallel / 2;
        //ok 100%
        double element3 = (element1) + (element2);
        //ok 100%
        double element4 = Math.tan((element3));
        //ok 100%
        double element5 = 1 - (earth.getEccentricity() * Math.sin(f_parallel));
        //ok 100%
        double element6 = 1 + (earth.getEccentricity() * Math.sin(f_parallel));
        //ok 100%
        double element7 = (element5) / (element6);
        //ok 100%
        double element8 = Math.sqrt(Math.pow(element7, earth.getEccentricity()));
        //ok 100%
        double element9 = (element4) * (element8);
        q1 = Math2.ln((element9));
    }

    //ok 100% --> checked!!!
    public void calcQ2(WGS84 earth) {
        //ok 100%
        double element1 = Math.PI / 4;
        //ok 100%
        double element2 = s_parallel / 2;
        //ok 100%
        double element3 = (element1) + (element2);
        //ok 100%
        double element4 = Math.tan((element3));
        //ok 100%
        double element5 = 1 - (earth.getEccentricity() * Math.sin(s_parallel));
        //ok 100%
        double element6 = 1 + (earth.getEccentricity() * Math.sin(s_parallel));
        //ok 100%
        double element7 = (element5) / (element6);
        //ok 100%
        double element8 = Math.sqrt(Math.pow(element7, earth.getEccentricity()));
        //ok 100%
        double element9 = (element4) * (element8);
        //ok 100%
        q2 = Math2.ln((element9));
    }
    //ok 100% --> checked!!!
    public void calcQ0(WGS84 earth) {
        //ok 100%
        double element1 = Math.PI / 4;
        //ok 100%
        double element2 = random_parallel / 2;
        //ok 100%
        double element3 = (element1) + (element2);
        //ok 100%
        double element4 = Math.tan((element3));
        //ok 100%
        double element5 = 1 - (earth.getEccentricity() * Math.sin(random_parallel));
        //ok 100%
        double element6 = 1 + (earth.getEccentricity() * Math.sin(random_parallel));
        //ok 100%
        double element7 = (element5) / (element6);
        //ok 100%
        double element8 = Math.sqrt(Math.pow(element7, earth.getEccentricity()));
        //ok 100%
        double element9 = (element4) * (element8);
        //ok 100%
        q0 = Math2.ln((element9));
    }
    //ok 100% --> checked!!!
    public void calcL() {
        //error found, were Math.pow, but have to be Math.cos --> now ok 100%
        double element1 = N1 * Math.cos(f_parallel);
        //ok 100%
        double element2 = Math2.ln((element1));
        //same stuff with .pow and .cos --> now ok 100%
        double element3 = N2 * Math.cos(s_parallel);
        //ok 100%
        double element4 = Math2.ln((element3));
        //ok 100%
        double element5 = (element2) - (element4);
        //ok 100%
        double element6 = q2 - q1;
        //ok 100%
        L = (element5) / (element6);
    }
    //ok 100% --> checked!!!
    public void calcK () {
        //ok 100%
        double element1 = N1 * Math.cos(f_parallel);
        //ok 100%
        double element2 = -(L * q1);
        //ok 100%
        double element3 = Math.pow(Math.exp(1), (element2));
        //ok 100%
        double element4 = L * (element3);
        //ok 100%
        K = (element1) / (element4);
    }
    //ok 100% --> checked!!!
    public void calcKbackup () {//value is equal to K (approx last number, which is rounding case in Java
        //ok 100%
        double element1 = N2 * Math.cos(s_parallel);
        //ok 100%
        double element2 = - (L * q2);
        //ok 100%
        double element3 = Math.pow(Math.exp(1), (element2));
        //ok 100%
        double element4 = L * (element3);
        //ok 100%
        Kbackup = (element1) / (element4);
    }
    //ok 100% --> checked!!!
    public void calcR0 () {
        //ok 100%
        double element1 = - (L * q0);
        //ok 100%
        double element2 = Math.pow(Math.exp(1), (element1));
        //ok 100%
        r0 = K * (element2);

    }
    //ok 100% --> checked!!!
    public void calcR () {
        //ok 100%
        double element1 = - (L * q);
        //ok 100%
        double element2 = Math.pow(Math.exp(1), (element1));
        //ok 100%
        r = K * (element2);
    }
    //ok 100% --> checked!!!
   public void calcLambda (UTMzone utm, Kaev_Left place) {
       //ok 100%
        utm.calcZone(place);
       //ok 100%
        utm.calcCentralMeridian(place);
       //ok 100%
        lambda = geodetic_longitude - fixed_central_meridian;

   //utm.getCentral_meridian();
   }
    //ok 100% --> checked!!!
   public void calcL_X () {
       //ok 100%
        // Answer is 4272..., but must be 54272.... where is error???
        x = (r * Math.sin(L * lambda));
   }
    //ok 100% --> checked!!!
   public void calcL_Y () {
       //ok 100%

        y = r0 - (r * Math.cos(L * lambda));
   }
   public void calcXYtrue () {
        xTrue = x + initialX;
        yTrue = y + initialY;
   }
   public void init (Lambert_Projection_From_Geodetic_To_LEST97 lest97, Kaev_Left lm, Estonia_LEST97 est, WGS84 earth, UTMzone zone) {
       lest97.geodetic_latitude = lm.getGeodetic_latitude();
       lest97.geodetic_longitude = lm.getGeodetic_longitude();
       lest97.f_parallel = est.getFirst_parallel();
       lest97.s_parallel = est.getSecond_parallel();
       System.out.println(
               "Geodetic latitude: --> " + Math2.toDegrees(lest97.geodetic_latitude)
                       + "\nGeodetic longitude: --> " + Math2.toDegrees(lest97.geodetic_longitude)
                       + "\nFirst parallel latitude: --> " + Math2.toDegrees(lest97.f_parallel)
                       + "\nSecond parallel latitude: --> " + Math2.toDegrees(lest97.s_parallel)
                       + "\nCustom parallel latitude: --> " + Math2.toDegrees(lest97.random_parallel)
       );
       //above = good!

       lest97.calcN1(earth);
       lest97.calcN2(earth);
       //above = good!

       lest97.calcQ(earth);
       lest97.calcQ0(earth);
       lest97.calcQ1(earth);
       lest97.calcQ2(earth);
       //above good!
       lest97.calcL();

       lest97.calcK();

       lest97.calcR();
       lest97.calcR0();


       lest97.calcLambda(zone, lm);

       lest97.calcL_X();
       lest97.calcL_Y();

       lest97.calcXYtrue();
   }
    public static void main(String[] args) {
        Kaev_Left lm = new Kaev_Left();
        Estonia_LEST97 est = new Estonia_LEST97();
        Lambert_Projection_From_Geodetic_To_LEST97 lest97 = new Lambert_Projection_From_Geodetic_To_LEST97();
        WGS84 earth = new WGS84();
        UTMzone zone = new UTMzone();
        TransverseMercator_From_UTM_to_Geodetic utm = new TransverseMercator_From_UTM_to_Geodetic();


        lest97.init(lest97,lm,est,earth,zone);

        System.err.println("Lambert EST97 x coordinate: " + lest97.xTrue);
        System.err.println("Lambert EST97 y coordinate: " + lest97.yTrue);

    }
}
