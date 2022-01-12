package com.epam.brest.web_app;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentDtoService;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.web_app.validators.StudentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final Logger logger = LogManager.getLogger(UniversityController.class);

    private final StudentDtoService studentDtoService;

    private final StudentService studentService;

    private final UniversityService universityService;

    private final StudentValidator studentValidator;

    public StudentController(StudentDtoService studentDtoService, StudentService studentService,
                             UniversityService universityService, StudentValidator studentValidator) {
        this.studentDtoService = studentDtoService;
        this.studentService = studentService;
        this.universityService = universityService;
        this.studentValidator = studentValidator;
    }

    @GetMapping(value = "/students")
    public final String students(Model model) {
        logger.debug("students()");
        model.addAttribute("filtered", false);
        model.addAttribute("students", studentDtoService.findAllDto());
        return "students";
    }

    @GetMapping(value = "/students/filter")
    public final String filteredStudentsByDate(Model model, @RequestParam(value = "startDate") String startDate,
                                               @RequestParam(value = "endDate") String endDate) {
        logger.debug("filteredStudentsByDate({}, {})", startDate, endDate);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("filtered", true);
        model.addAttribute("students", studentDtoService.findAllWithDate(startDate, endDate));
        return "students";
    }

    @GetMapping(value = "/student")
    public final String gotoAddStudentPage(Model model) {
        logger.debug("gotoAddStudentPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("student", new Student());
        model.addAttribute("universities", universityService.findAll());
        return "student";
    }

    @GetMapping(value = "/student/{id}")
    public final String gotoEditStudentPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditStudentPage(id:{}, model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("universities", universityService.findAll());
        return "student";
    }

    @GetMapping(value = "/student/{id}/delete")
    public final String deleteStudentById(@PathVariable Integer id, Model model) {
        logger.debug("deleteStudentById({})", id);
        studentService.delete(id);
        return "redirect:/students";
    }

    @PostMapping(value = "/student")
    public final String addStudent(Model model, Student student, BindingResult result) {
        logger.debug("addStudent({})", student);
        studentValidator.validate(student, result);
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            model.addAttribute("universities", universityService.findAll());
            return "student";
        }
        this.studentService.create(student);
        return "redirect:/students";
    }

    @PostMapping(value = "/student/{id}")
    public final String updateStudent(@PathVariable Integer id, Model model, Student student, BindingResult result) {
        logger.debug("updateStudent({})", student);
        studentValidator.validate(student, result);
        if (result.hasErrors()) {
            model.addAttribute("isNew", false);
            model.addAttribute("universities", universityService.findAll());
            return "student";
        }
        this.studentService.update(student);
        return "redirect:/students";
    }
}