package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // display list of employees
    @GetMapping(value = {"/", "/index"})
    public String contextPage(Model model) {
        return findPaginated(1, "name", "asc" ,model);
    }

    @GetMapping(value = "/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @GetMapping(value = "/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") long id, Model model) {

        // get employee form the service
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to the form
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @PostMapping(value = "/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") long id) {

        // call delete employee mothod
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 2;

        Page<Employee> paginated = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> listEmployees = paginated.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);

        return "index";
    }
}
