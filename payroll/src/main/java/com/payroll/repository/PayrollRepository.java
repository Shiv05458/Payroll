package com.payroll.repository;

import com.payroll.model.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PayrollRepository extends MongoRepository<Payroll, Integer> {

}
