package spring.covidtracker.model;

import lombok.Data;

@Data
public class LocationStats {
    private String country;
    private int newCases;
    private int totalCases;
    private int deaths;
    private int totalDeaths;
    private String population;

    private String stringencyIndex;
    private String cardiovascDeathRate;
    private String totalVaccinationsPerHundred;
    private String peopleFullyVaccinatedPerHundred;
    private String lifeExpectancy;

    public void setPopulation(String population){
        this.population = population.replace(".0","");
    }
}