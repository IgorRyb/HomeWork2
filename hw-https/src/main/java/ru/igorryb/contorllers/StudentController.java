package ru.igorryb.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.igorryb.services.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/students")
    public String findAll(Model model) {
        model.addAttribute("student", studentService.findStudentName());
        return "students";
    }

    @PostMapping(value = "/students")
    public RedirectView addStudent(@RequestParam("age") int age,@RequestParam("name") String name) {
        studentService.addStudent(age, name);
        return new RedirectView("/students");
    }

}
