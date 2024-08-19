package businesslogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherAPI {
    public String API(String AA) {

//        System.out.println(AA);

        
        String cityId = airportCityMap.get(AA);; // Replace with the desired city ID

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=" + cityId + "&appid=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response JSON and extract sunset information
                String sunsetTime = parseSunsetTime(response.toString());
//                System.out.println("Sunset time: " + sunsetTime);
                return sunsetTime;
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    private static String parseSunsetTime(String jsonResponse) {


        // Define the regular expression pattern to match the "sunset" key and its value
        // + is at least one
        Pattern pattern = Pattern.compile("\"sunset\":(\\d+)");

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(jsonResponse);

        String sunsetValue = "";
        if (matcher.find()) {
            // Retrieve the value after the "sunset" key
            sunsetValue = matcher.group(1);
        } else {
            System.out.println("No match found.");
        }


        long sunsetTimestamp = Long.parseLong(sunsetValue);
        // Replace with the actual timestamp value
        Date sunsetDate = new Date(sunsetTimestamp * 1000);  // Multiply by 1000 to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String exsunsetTime = sdf.format(sunsetDate);
        System.out.println(exsunsetTime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        String sunsetTime = sdf2.format(sunsetDate);

        return sunsetTime;
    }
    public static final Map<String, String> airportCityMap = new HashMap<>();

    static {
        airportCityMap.put("VNO/Vilnius", "593116");
        airportCityMap.put("LHR/London", "2643743");
        airportCityMap.put("CDG/Paris", "2988507");
        airportCityMap.put("FRA/Frankfurt", "2925533");
        airportCityMap.put("AMS/Amsterdam", "2759794");
        airportCityMap.put("FCO/Rome", "3169070");
        airportCityMap.put("MAD/Madrid", "3117735");
        airportCityMap.put("MUC/Munich", "2867714");
        airportCityMap.put("BCN/Barcelona", "3128760");
        // Add more mappings here for other airport codes and corresponding city IDs
    }


}

