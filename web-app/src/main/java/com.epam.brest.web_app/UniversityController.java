package com.epam.brest.web_app;
import com.epam.brest.model.University;
import com.epam.brest.service.UniversityDtoService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.web_app.validators.UniversityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UniversityController {

    private final Logger logger = LogManager.getLogger(UniversityController.class);

    private final UniversityDtoService universityDtoService;

    private final UniversityService universityService;

    private final UniversityValidator universityValidator;

    public UniversityController(UniversityDtoService universityDtoService, UniversityService universityService, UniversityValidator universityValidator) {
        this.universityDtoService = universityDtoService;
        this.universityService = universityService;
        this.universityValidator = universityValidator;
    }

    @GetMapping(value = "/universities")
    public final String universities(Model model) {
        model.addAttribute("universities", universityDtoService.findAllWithAvgCourse());
        return "universities";
    }

    @GetMapping(value = "/university")
    public final String gotoAddUniversityPage(Model model) {
        logger.debug("gotoAddUniversityPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("university", new University());
        return "university";
    }

    @GetMapping(value = "/university/{id}")
    public final String gotoEditUniversityPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditUniversityPage(id:{}, model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("university", universityService.getUniversityById(id));
        return "university";
    }

    @GetMapping(value = "/university/{id}/delete")
    public final String deleteUniversityById(@PathVariable Integer id, Model model) {
        logger.debug("deleteUniversityById({})", id);
        universityService.delete(id);
        return "redirect:/universities";
    }

    @PostMapping(value = "/university")
    public final String addUniversity(University university, BindingResult result) {
        logger.debug("addUniversity({})", university);
        universityValidator.validate(university, result);
        if (result.hasErrors()) {
            return "university";
        }
        this.universityService.create(university);
        return "redirect:/universities";
    }

    @PostMapping(value = "/university/{id}")
    public final String updateUniversity(@PathVariable Integer id, University university, BindingResult result) {
        logger.debug("addUniversity({})", university);
        universityValidator.validate(university, result);
        if (result.hasErrors()) {
            return "university";
        }
        this.universityService.update(university);
        return "redirect:/universities";
    }
}