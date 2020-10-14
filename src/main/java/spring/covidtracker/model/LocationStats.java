package spring.covidtracker.model;

import lombok.Data;

@Data
public class LocationStats {
    private String country;
    private String newCases;
    private int totalCases;
    private String deaths;
    private int totalDeaths;
    private String population;
    private String rate;
}
