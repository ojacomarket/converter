package ee.taltech.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Abstract class to serve general parameters, which should each ellipsoid model have.
 */
@RequiredArgsConstructor
abstract public class Ellipsoid {
    @NonNull
    protected double majorRadius;
    @NonNull
    protected double minorRadius;
    protected double eccentricity;
    protected double eccentricity_;
    protected double flattening;
}
