package com.duyhvt.springboot.cruddemo.rest;

import com.duyhvt.springboot.cruddemo.entity.Employee;
import com.duyhvt.springboot.cruddemo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    // constructor injection
    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // Add mapping for GET /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee id is not found - " + employeeId);
        }

        return employee;
    }

    // Add mapping for POST /employees/ - add new Employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // Also just in case, they pass an id in JSON -> we need to set id to 0
        // this is to force a save of new item, NOT update the current item

        employee.setId(0);

        Employee employee1 = employeeService.save(employee);

        return employee1;
    }

    // Add mapping for PUT /employees - update existing Employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }

    // Add mapping for PATCH /employees/{employeeId} - partial update
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String, Object> patchPayload) {
        Employee tempEmployee = employeeService.findById(employeeId);
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id is not found - " + employeeId);
        }

        // throw exception if request body contains "id" key
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
        }

        Employee patchedEmployee = apply(patchPayload, tempEmployee);

        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
        // Convert employee object to a JSON Object node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        // Convert patchPayload object to a JSON Object node
        ObjectNode patchPayloadNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        // Merge the patch update into the employee node
        employeeNode.setAll(patchPayloadNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }

    // Add mapping for DELETE /employee/{employeeId} - delete employee
    @DeleteMapping("/employees/{employeeId}")
    private String deleteEmployee(@PathVariable int employeeId) {
        // find Employee ID
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
        }

        employeeService.delete(employeeId);

        return "Deleted employee with id: " + employeeId;
    }
}
