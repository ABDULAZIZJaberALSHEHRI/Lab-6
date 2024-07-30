package com.example.lab6.Controller;

import com.example.lab6.ApiResponse.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<Employee>();

    @GetMapping("/display-employees")
    public ResponseEntity displayAllEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add-employee")
    public ResponseEntity addNewEmployee(@Valid @RequestBody Employee emp, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(emp);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added successfully"));
    }

    @PutMapping("/update-employee/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, @Valid @RequestBody Employee emp, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index, emp);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    @DeleteMapping("/delete-employee/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }

    @GetMapping("/search-emp/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position) {
        ArrayList<Employee> employeesFounded = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getPosition().equalsIgnoreCase(position)) {
                employeesFounded.add(emp);
            }
        }
        return ResponseEntity.status(200).body(employeesFounded);
    }

    @GetMapping("/get-emp-age/{from}/{to}")
    public ResponseEntity getEmployeeByAge(@PathVariable int from, @PathVariable int to) {
        ArrayList<Employee> getEmpAge = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAge() >= from && emp.getAge() <= to) {
                getEmpAge.add(emp);
            }
        }
        return ResponseEntity.status(200).body(getEmpAge);
    }


    @PutMapping("/apply-for-annual-leave/{id}")
    public ResponseEntity applyForAnnualLeave(@PathVariable String id) {

        for (Employee emp : employees) {
        if (emp.getId().equalsIgnoreCase(id)) {
            //Check Employee is on leave
            if (!emp.isOnLeave()) {
                //Check annual leave and compare it with vacation days '1'
                if (emp.getAnnualLeave() >= 1) {
                    emp.setOnLeave(true);
                    emp.setAnnualLeave(emp.getAnnualLeave() - 1);
                    return ResponseEntity.status(200).body(new ApiResponse("Apply of annual leave for vacation successfully"));
                }
            } else {
                return ResponseEntity.status(400).body(new ApiResponse("user already on leave !"));
            }
        }
        }
        return ResponseEntity.status(400).body(new ApiResponse("user not found !"));
    }

    @GetMapping("/no-annual-leave")
    public ResponseEntity noAnnualLeave() {
        ArrayList<Employee> noAnnualLeave = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAnnualLeave() == 0) {
                noAnnualLeave.add(emp);
            }
        }
        return ResponseEntity.status(200).body(noAnnualLeave);
    }

    @PutMapping("/promote/{id}")
    public ResponseEntity promoteEmployee(@PathVariable String id) {
        for (Employee emp : employees) {
            if (emp.getId().equalsIgnoreCase(id)) {
                if (emp.getPosition().equalsIgnoreCase("coordinator")) {
                    if (emp.getAge()>=30) {
                        if (!emp.isOnLeave()) {
                            emp.setPosition("supervisor");
                            return ResponseEntity.status(200).body(new ApiResponse("Employee promoted successfully"));
                        }else {
                            return ResponseEntity.status(400).body(new ApiResponse("user is on leave !"));
                        }
                    }else if (emp.getAge()<30){
                        return ResponseEntity.status(400).body(new ApiResponse("Employee age is under '30' !"));
                    }
                }else if(emp.getPosition().equalsIgnoreCase("supervisor")) {
                    return ResponseEntity.status(400).body(new ApiResponse("Employee is supervisor !"));
                }
            }

        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not exists"));
    }



}
