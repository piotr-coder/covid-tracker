package spring.covidtracker.controller;

import com.google.gson.*;
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
        int totalReportedCases = allStats.get(allStats.size()-4).getTotalCases();
        int totalReportedDeaths = allStats.get(allStats.size()-4).getTotalDeaths();
        int totalNewCases = allStats.get(allStats.size()-4).getNewCases();
//        int totalNewCases = allStats.stream().mapToInt(stat -> (int) (Math.round(Double.parseDouble(stat.getNewCases())))).sum();
        model.addAttribute("readingResponse", coronavirusDataService.getReadingResponse());
        model.addAttribute("stats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        return "home";
    }

    @GetMapping("vaccine")
    public String vaccine(Model model){
        List<LocationStats> allStats = coronavirusDataService.getStats();
        int totalReportedCases = allStats.get(allStats.size()-4).getTotalCases();
        int totalReportedDeaths = allStats.get(allStats.size()-4).getTotalDeaths();
        int totalNewCases = allStats.get(allStats.size()-4).getNewCases();
//        int totalNewCases = allStats.stream().mapToInt(stat -> (int) (Math.round(Double.parseDouble(stat.getNewCases())))).sum();
        model.addAttribute("readingResponse", coronavirusDataService.getReadingResponse());
        model.addAttribute("stats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        return "vaccine";
    }
    @GetMapping("api")
    public String api(Model model) {
        List<LocationStats> allStats = coronavirusDataService.getStats();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(allStats); // converts to json
        model.addAttribute("json", json);
        return "api";
    }
}








//        List<LocationStats> allStats = coronavirusDataService.getStats();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        return "api";
//        try {
//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allStats);
//            System.out.println(json);
//            return json;
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
