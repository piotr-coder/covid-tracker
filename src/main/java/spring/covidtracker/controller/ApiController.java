package spring.covidtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.covidtracker.model.LocationStats;
import spring.covidtracker.service.CoronavirusDataService;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private CoronavirusDataService coronavirusDataService;

    @GetMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationStats> exposeApi() {
        return coronavirusDataService.getStats();
    }
}