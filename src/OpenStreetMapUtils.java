import org.apache.commons.imaging.ImageReadException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

/*
Reverse Geocoding library for Java
von Dimitrios Ioannidis

A small Java library, which is basically a wrap-around for Nominatim, a reverse geocoding
[which means the input is a position given by latitude and longitude and the output is a street address]
service based on OpenStreetMap.
 */

public class OpenStreetMapUtils {

    private static final String GEOCODING_RESOURCE = "https://nominatim.openstreetmap.org/reverse";
    private static final String FORMAT = "jsonv2";
    private String osm_response = null;
    private String display_name = null; // 'Ολα μαζί

    private String name = null;             // Όνομα
    private String road = null;             // Οδός
    private String neighbourhood = null;    // Περιοχή
    private String post_code = null;        // ΤΚ
    private String city = null;             // Πόλη
    private String municipality = null;     // Δήμος
    private String county = null;           // Περιφερειακή Ενότητα
    private String state = null;            // Αποκεντρωμένη Διοίκηση
    private String state_district = null;   // Περιφέρεια
    private String country = null;          // Χώρα
    private String country_code = null;     // Κωδικός Χώρας, πχ GR

    private void OpenStreetMapJSONParser() {
        // parsing response
        Object obj = null;
        try {
            obj = new JSONParser().parse(osm_response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println(osm_response);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting display name
        display_name = (String) jo.get("display_name");

        // getting name
        name = (String) jo.get("name");

        // getting address
        Map address = ((Map)jo.get("address"));

        // iterating address Map
        Iterator<Map.Entry> itr1 = address.entrySet().iterator();

        while (itr1.hasNext()) {

            Map.Entry pair = itr1.next();

            String key = pair.getKey().toString().toLowerCase().trim();
            String value = pair.getValue().toString().trim();

            if ( key.equalsIgnoreCase("name") ) {
                name = value;
            }

            if ( key.equalsIgnoreCase("country") ) {
                country = value;
            }

            if ( key.equalsIgnoreCase("road") ) {
                road = value;
            }

            if ( key.equalsIgnoreCase("city") ) {
                city = value;
            }

            if ( key.equalsIgnoreCase("neighbourhood") ) {
                neighbourhood = value;
            }

            if ( key.equalsIgnoreCase("country_code") ) {
                country_code = value;
            }

            if ( key.equalsIgnoreCase("postcode") ) {
                post_code = value;
            }

            if ( key.equalsIgnoreCase("state") ) {
                state = value;
            }

            if ( key.equalsIgnoreCase("municipality") ) {
                municipality = value;
            }

            if ( key.equalsIgnoreCase("state_district") ) {
                state_district = value;
            }

            if ( key.equalsIgnoreCase("county") ) {
                county = value;
            }
        }
    }

    public void GeocodeSync(String latitude, String longitude) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        String requestUri = GEOCODING_RESOURCE +
                "?format=" + FORMAT +
                "&lat=" + latitude +
                "&lon=" + longitude;

        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                .GET().uri(URI.create(requestUri))
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/74.0.3729.169 Safari/537.36")
                .timeout(Duration.ofMillis(4000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        osm_response = geocodingResponse.body().toString();
        // Parse Json
        OpenStreetMapJSONParser();
    }

    public String getDisplayName() {
        final String value = (display_name == null || display_name.isEmpty() || display_name.trim().isEmpty())
                ? "Not Found"
                : display_name.trim();
        return value;
    }

    public String getName() {
        final String value = (name == null || name.isEmpty() || name.trim().isEmpty())
                ? "Not Found"
                : name.trim();
        return value;
    }

    public String getMunicipality() {
        final String value = (municipality == null || municipality.isEmpty() || municipality.trim().isEmpty())
                ? "Not Found"
                : municipality.trim();
        return value;
    }

    public String getCountryCode() {
        final String value = (country_code == null || country_code.isEmpty() || country_code.trim().isEmpty())
                ? "Not Found"
                : country_code.trim().toUpperCase();
        return value;
    }

    public String getCountry() {
        final String value = (country == null || country.isEmpty() || country.trim().isEmpty())
                ? "Not Found"
                : country.trim();
        return value;
    }

    public String getPostcode() {
        final String value = (post_code == null || post_code.isEmpty() || post_code.trim().isEmpty())
                ? "Not Found"
                : post_code.trim();
        return value;
    }

    public String getState() {
        final String value = (state == null || state.isEmpty() || state.trim().isEmpty())
                ? "Not Found"
                : state.trim();
        return value;
    }

    public String getStateDistrict() {
        final String value = (state_district == null || state_district.isEmpty() || state_district.trim().isEmpty())
                ? "Not Found"
                : state_district.trim();
        return value;
    }

    public String getCity() {
        final String value = (city == null || city.isEmpty() || city.trim().isEmpty())
                ? "Not Found"
                : city.trim();
        return value;
    }

    public String getNeighbourhood() {
        final String value = (neighbourhood == null || neighbourhood.isEmpty() || neighbourhood.trim().isEmpty())
                ? "Not Found"
                : neighbourhood.trim();
        return value;
    }

    public String getCounty() {
        final String value = (county == null || county.isEmpty() || county.trim().isEmpty())
                ? "Not Found"
                : county.trim();
        return value;
    }

    public String getRoad() {
        final String value = (road == null || road.isEmpty() || road.trim().isEmpty())
                ? "Not Found"
                : road.trim();
        return value;
    }



}
