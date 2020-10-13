package spring.covidtracker.model;

import lombok.Data;

@Data
public class LocationStats {
    private String country;
    private String newCases;
    private String deaths;
    private String population;
    private String rate;
}
