package ee.taltech.converter.utm;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.UTMCoordinates;
import ee.taltech.converter.coordinates.CoordinateSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UTM<E extends Ellipsoid> extends CoordinateSystem implements UTMCoordinates<E> {
    private double x;
    private double y;
}
