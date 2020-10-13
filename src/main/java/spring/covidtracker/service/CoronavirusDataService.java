package spring.covidtracker.service;


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

@Service
public class CoronavirusDataService {
//    public static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/owid-covid-codebook.csv";
//    public static final String VIRUS_DATA_URL = "http://opendata.ecdc.europa.eu/covid19/casedistribution/csv/";
    public static final String VIRUS_DATA_URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";
    private List<LocationStats> stats = new ArrayList<>();


    @PostConstruct
    public void fetchVirusData() throws IOException {
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
//            System.out.println(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        long read_end = System.nanoTime();
        System.out.println("Finished reading response in " + decimalFormat.format((read_end - read_start) / Math.pow(10, 6)) + " milliseconds");
        bufferedReader.close();

        StringReader stringReader = new StringReader(stringBuilder.toString());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            if (!record.get("countriesAndTerritories").equals(newStats.get(newStats.size()-1).getCountry())) {
                LocationStats locationStats = new LocationStats();
                locationStats.setCountry(record.get("countriesAndTerritories"));
                locationStats.setNewCases(record.get("cases"));
                locationStats.setDeaths(record.get("deaths"));
                locationStats.setPopulation(record.get("popData2019"));
                locationStats.setRate(record.get(record.size()-1));
                newStats.add(locationStats);
                System.out.println(locationStats);
            }
        }
        newStats.remove(location);
        this.stats = newStats;
    }
}
//    @PostConstruct    // JAVA 12 SYNTAX
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