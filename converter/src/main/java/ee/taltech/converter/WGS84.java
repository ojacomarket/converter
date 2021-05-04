package ee.taltech.converter;

import lombok.Getter;

/**
 * Class that holds all needed parameters, which describe perfectly WGS84 model of the Earth.
 */
@Getter
public class WGS84 extends Ellipsoid implements DescribeEarth, GeodeticCoordinates<WGS84> {

    public WGS84() {
        super(6378137D, 6356752D);
        flattening = flattening(majorRadius, minorRadius);
        eccentricity = eccentricity(majorRadius, minorRadius);
        eccentricity_ = eccentricity_(majorRadius, minorRadius);
    }
}
