package ee.taltech.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConverterApplication {

	public static void main(String[] args) {
		 double LATITUDE_TLN = 59.436961D;
		 double LONGITUDE_TLN = 24.753575D;

		 double major_semi = 6378137D;
		 double minor_semi = 6356752.31424518D;
		 double flattering = 1 - (minor_semi / major_semi);
		 double e = Math.sqrt(2*flattering - Math.pow(flattering,2));

		 double A0 = 1 - (Math.pow(e,2)/4) - (3*Math.pow(e,4)/64) - (5*Math.pow(e,6)/256);
		 double A2 = (3/8) * (Math.pow(e,2) + (Math.pow(e,4)/4) + (15*Math.pow(e,6)/128));
		 double A4 = (15/256) * (Math.pow(e,4) + Math.pow(e,6)*3/4);
		 double A6 = Math.pow(e,6)*35/3072;

		 double m = major_semi*(A0*LATITUDE_TLN + A2*Math.sin(2*LATITUDE_TLN) + A4*Math.sin(4*LATITUDE_TLN)+
				 A6*Math.sin(6*LATITUDE_TLN));

		 double p = (major_semi*(1 - Math.pow(e,2))) / (Math.pow(1 - (Math.pow(e,2)*Math.pow(Math.sin(LATITUDE_TLN),2)), 1.5));
		 double v = major_semi / (Math.sqrt(1 - (Math.pow(e,2)*Math.pow(Math.sin(LATITUDE_TLN),2))));
		 double VILKA = v/p;
		 double t = Math.tan(LATITUDE_TLN);
		 double OMEGA = LONGITUDE_TLN - 500_000L;



		 double UTM_NORTH;


	}

}
