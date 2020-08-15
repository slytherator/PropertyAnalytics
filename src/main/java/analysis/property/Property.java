package analysis.property;

import java.util.Arrays;
import java.util.Date;

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

    public double[] getState(long ts, Property benchmark){
        return new double[]{
            ts - lastSold,
            ts - lastEstimate,
            lastEstimate - lastSold,
            lastSoldPrice,
            (lastEstimate - lastSoldPrice)/lastSoldPrice,
            newBuild ? 1 : 0,
            holding.charAt(0),
            type.charAt(0),
            location[0],
            location[1],
            (location[0] - benchmark.location[0]) * (location[0] - benchmark.location[0]) + (location[1] - benchmark.location[1]) * (location[1] - benchmark.location[1]),
            lastPriceEstimate - benchmark.lastPriceEstimate,
            holding.equals(benchmark.holding) ? 1 : 0,
            type.equals(benchmark.type) ? 1 : 0,
        };
    }

    public double[] getState(Date dt){
        long ts = dt.getTime();
        return new double[]{
                ts - lastSold,
                ts - lastEstimate,
                lastEstimate - lastSold,
                lastSoldPrice,
                (lastEstimate - lastSoldPrice)/lastSoldPrice,
                newBuild ? 1 : 0,
                holding.charAt(0),
                type.charAt(0),
                location[0],
                location[1],
                dt.getMonth()
        };
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
