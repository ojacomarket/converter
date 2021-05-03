package ee.taltech.converter.coordinates;

import ee.taltech.converter.Ellipsoid;
import ee.taltech.converter.GeodeticCoordinates;
import ee.taltech.converter.UTMCoordinates;
import lombok.*;

@Getter
@Setter
@ToString
public class Geodetic<T extends Ellipsoid> extends CoordinateSystem implements GeodeticCoordinates<T>, UTMCoordinates<T> {
    private double latitude;
    private double longitude;
    private double height;
    private double curvatureRadius;

    public Geodetic(double latitude, double longitude, double height) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
    }

    public Geodetic(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
