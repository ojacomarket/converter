/*
package ee.taltech.converter;

import lombok.Data;

@Data
public class UTMzone {
    private double zone;
    private double central_meridian; //radians!!
    public void calcZone (Kaev_Left place) {
        // from Greenwich to 180 degrees EAST (Estonia is there ca 24 degrees)
        double greater_value = 31 + ((180*place.getGeodetic_longitude())/(Math.PI * 6));
        zone = Math.round(greater_value); //Estonia is located in [<-- 34 <--> 35 -->] /// UTM Zones
    }
    // Calculate central meridian for given UTM zone and longitude (central meridian = meridian line,
    // that passes nearby :D)
    public void calcCentralMeridian (Kaev_Left place) {
        //for Estonia
        central_meridian = ((6 * zone)-183) * (Math.PI/180);
    }


}
*/
