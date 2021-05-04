package ee.taltech.converter.coordinates;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.lib.Math2;
import ee.taltech.converter.Projection;
import ee.taltech.converter.CoordinateSystem;
import lombok.*;

/**
 * Class that represents structure of Lambert EST97 coordinate system.
 *
 * @param <T> Is an Ellipsoid model, that currently in use within coordinate system, for most of the time
 *            it is WGS84.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LambertEST97<T extends Ellipsoid> extends CoordinateSystem implements Projection<T> {
    @NonNull
    private double x;
    @NonNull
    private double y;
    public static final double belowFirstParallel = Math2.toRadians(57.51755393D);
    public static final double firstParallel = Math2.toRadians(58.0000000000000D);
    public static final double secondParallel = Math2.toRadians(59.3333333333333D);
    public static final double originX = 500_000D;
    public static final double originY = 6_375_000D;
    public static final double fixedCentralMeridian = Math2.toRadians(24.000000000000D);
}
