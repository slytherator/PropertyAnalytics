package analysis;

import utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PostcodeLocation {
    public static HashMap<String, double[]> locations = new HashMap<>();
    static {
        try {
            StringUtils.readCSV("[DATA_PATH]/postcode-outcodes.csv").forEach(new Consumer<Map<String, String>>() {
                @Override
                public void accept(Map<String, String> data) {
                    locations.put(data.get("postcode"), new double[]{
                        Double.parseDouble(data.get("latitude")),
                        Double.parseDouble(data.get("longitude"))
                    });
                }
            });
            System.out.println(locations.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
