package Task3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;


public class Task3 {
    public static void main(String[] args) throws IOException {

        JSONParser parser = new JSONParser();
        Files.createFile(Paths.get("src/Task3/report.json"));

        Map<Long, String> map;
        map = readValues();

        try (Reader reader = new FileReader("src/Task3/tests.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray msg = (JSONArray) jsonObject.get("tests");

            recursIterator(msg, map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Map<Long, String> readValues() {

        JSONParser parser = new JSONParser();
        Map<Long, String> map = new LinkedHashMap<>();

        try (Reader reader = new FileReader("src/Task3/values.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("values");

            for (Object o : jsonArray) {
                JSONObject inner = (JSONObject) o;
                map.put((long) inner.get("id"), (String) inner.get("value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void recursIterator(JSONArray msg, Map<Long, String> map) throws IOException {

        for (Object o : msg) {
            JSONObject inner = (JSONObject) o;
            if (inner.get("values") != null) {
                JSONArray deepInner = (JSONArray) inner.get("values");
                recursIterator(deepInner, map);
            }

            if (inner.get("value") == "") {
                String newValue = insertValues(map, inner);
                inner.put("value", newValue);
            }
            inner.remove("values");
            System.out.println(inner);
            Files.writeString(Paths.get("src/Task3/report.json"), inner.toString() + " \n", StandardOpenOption.APPEND);
        }
    }

    public static String insertValues(Map<Long, String> map, JSONObject inner) {
        for (Map.Entry<Long, String> mapObj : map.entrySet()) {
            if (inner.get("id").equals(mapObj.getKey())) {
                return mapObj.getValue();
            }
        }
        return "";
    }
}
