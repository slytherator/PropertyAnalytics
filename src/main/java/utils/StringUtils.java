package utils;

import com.opencsv.CSVReader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static List<String> getContent(String filename){
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            Pattern pattern = Pattern.compile("(\\w+)|(\\.{3})|[^\\s]");

            while ((line = reader.readLine()) != null){
                line = line.toLowerCase();
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    list.add(matcher.group());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getContentAll(String filename){
        String all = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null){
                all += line + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

    public static String getContentFromURL(String url){
        String all = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line = null;
            while ((line = reader.readLine()) != null){
                all += line + "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }
}
