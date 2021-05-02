package ee.taltech.converter.coordinates;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.GeodeticCoordinates;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
//@RequiredArgsConstructor
public class Geodetic<T extends Ellipsoid> extends CoordinateSystem implements GeodeticCoordinates<T> {
   // @NonNull
    private final double latitude;
   // @NonNull
    private final double longitude;
    @Setter
    private double height;
    @Setter
    private double curvatureRadius;

    public Geodetic (double latitude, double longitude, double height) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
    }
    public Geodetic (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
