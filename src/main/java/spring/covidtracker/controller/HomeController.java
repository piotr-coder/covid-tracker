package spring.covidtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import spring.covidtracker.model.LocationStats;
import spring.covidtracker.service.CoronavirusDataService;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CoronavirusDataService coronavirusDataService;

    private final ModelMap modelMap = new ModelMap();

    @PostConstruct
    private void fetchData (){
        List<LocationStats> allStats = coronavirusDataService.getStats();
        int totalReportedCases = allStats.get(allStats.size() - 4).getTotalCases();
        int totalReportedDeaths = allStats.get(allStats.size() - 4).getTotalDeaths();
        int totalNewCases = allStats.get(allStats.size() - 4).getNewCases();
        modelMap.addAttribute("readingResponse", coronavirusDataService.getReadingResponse());
        modelMap.addAttribute("stats", allStats);
        modelMap.addAttribute("totalReportedCases", totalReportedCases);
        modelMap.addAttribute("totalNewCases", totalNewCases);
        modelMap.addAttribute("totalReportedDeaths", totalReportedDeaths);
    }

    @GetMapping("home")
    public String home(Model model){
        model.addAllAttributes(modelMap);
        return "home";
    }

    @GetMapping("vaccine")
    public String vaccine(Model model){
        model.addAllAttributes(modelMap);
        return "vaccine";
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
