package ee.taltech.converter;

import ee.taltech.converter.coordinates.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Class Point is used to serve as a blueprint for Geodetic, Cartesian, UTM and Lambert Conical Projection
 * data storage.
 */
@Deprecated
@AllArgsConstructor
abstract public class Point<T extends CoordinateSystem> {

    /*protected Cartesian cartesian;
    protected UTM utm;
    protected Geodetic geodetic;
    protected LambertEST97 lambert;*/

    protected T coordinates;

    /*protected double geodeticLatitude;
    protected double geodeticLongitude;
    protected double geodeticHeight;

    protected double cartesianX;
    protected double cartesianY;

    protected double utmX;
    protected double utmY;

    protected double lambertEst97X;
    protected double lambertEst97Y;*/
}
