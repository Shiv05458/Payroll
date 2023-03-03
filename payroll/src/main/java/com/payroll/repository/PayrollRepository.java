package com.payroll.repository;

import com.payroll.model.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PayrollRepository extends MongoRepository<Payroll, Integer> {

    Payroll findByEmployeeId(int employeeId);
}
