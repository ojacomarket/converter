package ee.taltech.converter.lambert;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.Math2;
import ee.taltech.converter.Projection;
import ee.taltech.converter.coordinates.CoordinateSystem;
import lombok.*;

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
