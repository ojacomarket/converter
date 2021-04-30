package ee.taltech.converter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {
    private double geodetic_latitude = 22222222D;
    private double geodetic_longitude =33333333333D;
    private double geodetic_height;
}
