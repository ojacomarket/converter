package ee.taltech.converter.coordinates;

import ee.taltech.converter.CartesianCoordinates;
import ee.taltech.converter.Ellipsoid;

import lombok.*;

@Getter
@RequiredArgsConstructor
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
