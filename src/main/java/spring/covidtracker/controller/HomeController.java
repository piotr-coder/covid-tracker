package spring.covidtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.covidtracker.model.LocationStats;
import spring.covidtracker.service.CoronavirusDataService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CoronavirusDataService coronavirusDataService;

    @GetMapping
    public String home(Model model){
        List<LocationStats> allStats = coronavirusDataService.getStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getTotalCases()).sum();
        int totalReportedDeaths = allStats.stream().mapToInt(stat -> stat.getTotalDeaths()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> (int) (Math.round(Double.parseDouble(stat.getNewCases())))).sum();
        model.addAttribute("readingResponse", coronavirusDataService.getReadingResponse());
        model.addAttribute("stats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        return "home";
    }
}
