package ee.taltech.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
