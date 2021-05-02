package ee.taltech.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WGS84_Test {
    WGS84 estonia = new WGS84();
    double flattening = 0.003353D;
    double eccentricity = 0.08182D;
    double eccentricity_ = Math.sqrt((Math.pow(estonia.majorRadius,2) - Math.pow(estonia.minorRadius,2)) / Math.pow(estonia.minorRadius,2));

    @Test
    void return_WGS84_flattening() {
        double flatteningResult = Math2.round((estonia.flattening(estonia.majorRadius, estonia.minorRadius)),6);
        assertEquals(flattening, flatteningResult);
    }
    @Test
    void return_WGS84_eccentricity() {
        double eccentricityResult = Math2.round((estonia.eccentricity(estonia.majorRadius, estonia.minorRadius)),6);
        assertEquals(eccentricity, eccentricityResult);
    }
    @Test
    void return_WGS84_eccentricity_() {
        double eccentricity_Result = estonia.eccentricity_(estonia.majorRadius, estonia.minorRadius);
        assertEquals(eccentricity_, eccentricity_Result);
    }
}
