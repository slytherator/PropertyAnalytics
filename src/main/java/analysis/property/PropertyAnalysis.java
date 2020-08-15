package analysis.property;

import analysis.PostcodeLocation;
import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

public class PropertyAnalysis {
    private static final double MONTH_MIILIS = 30 * 86400000.0;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");

    private static HashMap<String, Map<String, Map<String, Property>>> properties = new HashMap<>();
    private static HashMap<String, double[]> rollingGrowthRates = new HashMap<>();

    private static BufferedWriter writer;
    private static String[] data = null;
    private static Date reached = null;
    private static int months = 0;

    public static void main(String[] args) throws IOException, ParseException {
        CSVReader reader = new CSVReader(new FileReader("[DATA_PATH]/pp-complete-sorted.csv"));
        writer = new BufferedWriter(new FileWriter("price_evolutions.csv"));
        writer.write("date,x,y,g\n");

        while ((data = reader.readNext()) != null){
            String postcode = data[3];
            //longitude, latitude
            double[] location = PostcodeLocation.locations.get(postcode.split(" ")[0]);
            if(location != null) {
                String loc = Math.round(location[0] * 5) + "," + Math.round(location[1] * 5);
                String id = postcode + " " + data[7];

                Property property = properties.computeIfAbsent(loc, k -> new HashMap<>()).computeIfAbsent(postcode, k -> new HashMap<>()).computeIfAbsent(id, k -> new Property(id, data[4], data[6], data[3], location));
                property.newBuild = data[5].equals("Y");

                Date dt = format.parse(data[2]);
                if(reached == null){
                    reached = dt;
                }

                long ts = dt.getTime();
                double prx = Double.parseDouble(data[1]);

                if(ts > (property.lastSold + 6 * MONTH_MIILIS) && property.lastPriceEstimate > 0){
                    double months = (ts - property.lastEstimate) / MONTH_MIILIS;
                    double growthRate = Math.exp(Math.log(prx/property.lastPriceEstimate)/months);

                    //Filter out wide growth rates
                    if(Math.abs(growthRate - 1) < 0.1){
                        double[] g = rollingGrowthRates.computeIfAbsent(loc, k -> new double[]{1.0});

                        //Keep a rolling avg of growth rates for the location
                        g[0] = 0.99 * g[0] + 0.01 * growthRate;
                    }

                    if(reached.getMonth() != dt.getMonth()){
                        rebase();
                    }
                    reached = dt;
                }

                property.lastSold = ts;
                property.lastSoldPrice = prx;

                property.lastEstimate = property.lastSold;
                property.lastPriceEstimate = property.lastSoldPrice;
            }
        }
    }

    private static void rebase() {
        if(months++ > 12 * 4) {
            properties.forEach(new BiConsumer<String, Map<String, Map<String, Property>>>() {
                @Override
                public void accept(String loc, Map<String, Map<String, Property>> m0) {
                    double g = rollingGrowthRates.computeIfAbsent(loc, k -> new double[]{1.0})[0];
                    m0.forEach(new BiConsumer<String, Map<String, Property>>() {
                        @Override
                        public void accept(String postcode, Map<String, Property> m1) {
                            m1.forEach(new BiConsumer<String, Property>() {
                                @Override
                                public void accept(String id, Property property) {
                                    double prx = property.lastPriceEstimate * Math.pow(g, (reached.getTime() - property.lastEstimate) / MONTH_MIILIS);
                                    property.lastEstimate = reached.getTime();
                                    property.lastPriceEstimate = prx;
                                }
                            });
                        }
                    });

                    try {
                        writer.write(f2.format(reached) + "," + loc + "," + g + "\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
