package ee.taltech.converter.coordinates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UTM extends CoordinateSystem{
    private double x;
    private double y;
}
