package com.example.employeemanagement;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class employeeServiceImplContextConfiguration {

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee eyraf = new Employee((long) 1,"Eyraf","Abdullayev","eyrafabdullayev@gmail.com");
        Employee elchin = new Employee((long) 2,"Elchin", "Abdullayev", "elchin74@gmail.com");

        ArrayList<Employee> employees = new ArrayList<Employee>(Arrays.asList(eyraf,elchin));

        Employee elvin = new Employee("Elvin", "Abdurrahmanov", "elvinabdurrahmanov@mail.ru");

        Mockito.when(employeeRepository.findAll())
                .thenReturn(employees);

        Mockito.when(employeeRepository.findById(eyraf.getId()))
                .thenReturn(Optional.of(eyraf));

        Mockito.when(employeeRepository.save(elvin))
                .thenReturn(elvin);
    }

    @Test
    public void whenAllEmployees_thenEmployeesShouldBeFound() {
        int size = 2;

        List<Employee> found = employeeService.getAllEmployees();

        assertThat(found.size())
                .isEqualTo(size);
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() {
        String name = "Eyraf";

        Employee found = employeeService.getEmployeeById(1);

        assertThat(found.getName())
                .isEqualTo(name);
    }

    @Test
    public void whenValidEmployee_thenEmployeeBeFound() {
        Employee elvin = new Employee("Elvin", "Abdurrahmanov", "elvinabdurrahmanov@mail.ru");

        employeeService.saveEmployee(elvin);
    }
}
