package analysis.property;

import java.util.Arrays;

public class Property {
    public final String id;
    public final String type;
    public final String holding;
    public final double[] location;
    public final String postcode;

    public long lastSold;
    public double lastSoldPrice;
    public long lastEstimate;
    public double lastPriceEstimate;

    public boolean newBuild = false;

    public Property(String id, String type, String holding, String postcode, double[] location) {
        this.id = id;
        this.type = type;
        this.holding = holding;
        this.postcode = postcode;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", holding='" + holding + '\'' +
                ", location=" + Arrays.toString(location) +
                ", postcode='" + postcode + '\'' +
                ", lastSold=" + lastSold +
                ", lastSoldPrice=" + lastSoldPrice +
                ", lastEstimate=" + lastEstimate +
                ", lastPriceEstimate=" + lastPriceEstimate +
                ", newBuild=" + newBuild +
                '}';
    }
}
