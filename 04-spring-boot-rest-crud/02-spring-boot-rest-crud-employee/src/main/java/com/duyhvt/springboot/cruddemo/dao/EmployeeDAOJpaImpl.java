package com.duyhvt.springboot.cruddemo.dao;

import com.duyhvt.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
    // define field for EntityManager
    private EntityManager entityManager;
    // set up a constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Employee> findAll() {
        // create the query
        TypedQuery<Employee> query = entityManager.createQuery("FROM Employee", Employee.class);
        // execute the query and get the result list
        List<Employee> employees = query.getResultList();
        // return the result
        return employees;
    }

    @Override
    public Employee findById(int id) {
        // get employee
        Employee employee = entityManager.find(Employee.class, id);
        // return employee
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        // save employee
        // if id == 0 then insert save else updated
        Employee savedEmployee = entityManager.merge(employee);

        return savedEmployee;
    }

    @Override
    public void delete(int id) {
        Employee employee = entityManager.find(Employee.class, id);

        entityManager.remove(employee);
    }
}
