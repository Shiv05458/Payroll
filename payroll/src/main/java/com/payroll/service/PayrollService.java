package com.payroll.service;

import com.payroll.model.DbSequence;
import com.payroll.model.Payroll;
import com.payroll.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository repository;

    @Autowired
    private SequenceGenerator sequenceGenerator;


    public String createPayroll(Payroll payroll){
      //  payroll.setPayrollId(sequenceGenerator.getSequence(DbSequence.SEQUENCE_NAME));

        List<Payroll> list = repository.findAll();

        List<Payroll> result = list.stream().sorted(Comparator.comparingInt(Payroll::getPayrollId)).collect(Collectors.toList());

        payroll.setPayrollId(result.get(result.size()-1).getPayrollId()+1);

        Payroll pay = repository.save(payroll);
        return pay != null ? "Payroll created successfully with id : "+pay.getPayrollId() : "Failed inb creating Payroll";
    }

    public Payroll updatePayroll(int id, Payroll payroll){
        Payroll pay = repository.findById(id).get();
        pay.setEmployeeId(payroll.getEmployeeId());
        pay.setBankAccountNumber(payroll.getBankAccountNumber());
        pay.setMonthlySalary(payroll.getMonthlySalary());
        pay.setRegisteredBank(payroll.getRegisteredBank());
        return repository.save(payroll);
    }

    public Payroll getPayroll(int id){
        return repository.findById(id).get();
    }
}
