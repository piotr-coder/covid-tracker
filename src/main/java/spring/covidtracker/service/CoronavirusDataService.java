package spring.covidtracker.service;


import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import spring.covidtracker.model.LocationStats;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class CoronavirusDataService {
//    public static final String VIRUS_DATA_URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";
    public static final String VIRUS_DATA_URL = "https://covid.ourworldindata.org/data/owid-covid-data.csv";
    private List<LocationStats> stats = new ArrayList<>();
    private String readingResponse;


    @PostConstruct
    public void fetchVirusData() throws IOException {   // fits for this purpose but better to move business logic outside service
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        URL url = new URL(VIRUS_DATA_URL);
        List<LocationStats> newStats = new ArrayList<>();
        LocationStats location = new LocationStats();
        location.setCountry("Fooo");
        newStats.add(location);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        long read_start = System.nanoTime();
        while((inputLine = bufferedReader.readLine()) != null){
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        long read_end = System.nanoTime();
        readingResponse = "Finished reading response in " + decimalFormat.format((read_end - read_start) / Math.pow(10, 6)) + " milliseconds";
        System.out.println(readingResponse);
        bufferedReader.close();

        StringReader stringReader = new StringReader(stringBuilder.toString());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            if (!record.get("location").equals(newStats.get(newStats.size()-1).getCountry())) {
                LocationStats locationStats = new LocationStats();
                locationStats.setCountry(record.get("location"));
                locationStats.setNewCases(record.get("new_cases"));
                locationStats.setTotalCases(convertToInt(locationStats.getNewCases()));
                locationStats.setDeaths(record.get("new_deaths"));
                locationStats.setTotalDeaths(convertToInt(locationStats.getDeaths()));
                locationStats.setPopulation(record.get("population"));
                locationStats.setRate(record.get(record.size()-1));
                newStats.add(locationStats);
            }else {
                newStats.get(newStats.size()-1).setTotalCases(newStats.get(newStats.size()-1).getTotalCases()
                        + convertToInt(record.get("new_cases")));
                newStats.get(newStats.size()-1).setTotalDeaths(newStats.get(newStats.size()-1).getTotalDeaths()
                        + convertToInt(record.get("new_deaths")));
            }
        }
        newStats.remove(location);
        System.out.println(newStats);
        this.stats = newStats;
    }
    private int convertToInt(String value){
        try{
            return (int) Math.round(Double.parseDouble(value));
        }
        catch (Exception e){
            System.out.println("Unable to convert value " + value + " to String. Converting to 0. Exception is: " + e.toString());
            return 0;
        }
    }
}
//    @PostConstruct    // JAVA 12 SYNTAX TO FETCH DATA
//    public void fetchVirusData() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(VIRUS_DATA_URL))
//                .build();
//
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());
//    }
//}