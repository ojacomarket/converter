package ee.taltech.converter.coordinates;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.UTMCoordinates;
import ee.taltech.converter.CoordinateSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents structure of UTM amd TM coordinate system.
 *
 * @param <T> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
@Getter
@Setter
@AllArgsConstructor
public class UTM<T extends Ellipsoid> extends CoordinateSystem implements UTMCoordinates<T> {
    private double x;
    private double y;
}
