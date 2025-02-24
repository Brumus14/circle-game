import java.util.*;

public class GunShootPoint {
    private Rectangle barrel;
    private double offset;

    public GunShootPoint(Rectangle b, double o) {
        barrel = b;
        offset = o;
    }

    public Rectangle getBarrel() {
        return barrel;
    }

    public double getOffset() {
        return offset;
    }
}
