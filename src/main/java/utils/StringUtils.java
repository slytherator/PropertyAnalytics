package utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static List<Map<String, String>> readCSV(String file) throws IOException {
        CSVReader r1 = new CSVReader(new FileReader(file));
        String[] headers = r1.readNext();
        List l = new ArrayList();
        String[] parts = null;
        while ((parts = r1.readNext()) != null){
            if(parts.length == headers.length) {
                Map<String, String> m = new HashMap<>();
                for (int i = 0; i < parts.length; i++) {
                    m.put(headers[i], parts[i]);
                }
                l.add(m);
            }
        }
        return l;
    }
}
