package com.epam.brest.web_app;
import com.epam.brest.service.UniversityDtoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UniversityController {

    private final UniversityDtoService universityDtoService;

    public UniversityController(UniversityDtoService universityDtoService) {
        this.universityDtoService = universityDtoService;
    }


    @GetMapping(value = "/universities")
    public final String universities( Model model) {
        model.addAttribute("universities", universityDtoService.findAllWithAvgCourse());
        return "universities";
    }

    @GetMapping(value = "/university/{id}")
    public final String gotoEditUniversityPage(@PathVariable Integer id, Model model) {
        return "university";
    }

    @GetMapping(value = "/university/add")
    public final String gotoAddUniversityPage(Model model) {
        return "university";
    }
}