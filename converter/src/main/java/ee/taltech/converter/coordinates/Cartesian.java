package ee.taltech.converter.coordinates;

import ee.taltech.converter.CartesianCoordinates;
import ee.taltech.converter.Ellipsoid;

import ee.taltech.converter.CoordinateSystem;
import lombok.*;

/**
 * Class that represents structure of Cartesian coordinate system.
 *
 * @param <T> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
@Getter
@RequiredArgsConstructor
@ToString
public class Cartesian<T extends Ellipsoid> extends CoordinateSystem implements CartesianCoordinates<T> {
    @NonNull
    private final double x;
    @NonNull
    private final double y;
    @NonNull
    private final double z;
    @Setter
    private double betaAngle;
}
