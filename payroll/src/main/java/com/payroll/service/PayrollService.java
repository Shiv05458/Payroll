package com.payroll.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payroll.model.DbSequence;
import com.payroll.model.Employee;
import com.payroll.model.Payroll;
import com.payroll.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository repository;

    double monthlySalary;


    @KafkaListener(topics="Annaul_CTC", groupId = "groupId")
    public void getMonthlySalary(String annualSalary){
        monthlySalary = Double.parseDouble(annualSalary) / 12 ;
    }

    @KafkaListener(topics="Annual_CTC_Update", groupId = "groupId")
    public void autoSaveEmployeePayrollDetails(String annualSalary) throws JsonProcessingException {
        List<Payroll> list = repository.findAll();
        Payroll pay = new Payroll();
        List<Payroll> result = list.stream().sorted(Comparator.comparingInt(Payroll::getPayrollId)).collect(Collectors.toList());
        if(result.size()==0){
            pay.setPayrollId(1);
        }else {
            int id = result.get(result.size() - 1).getPayrollId() != 0 ? result.get(result.size() - 1).getPayrollId() + 1 : 1;
            pay.setPayrollId(id);
        }
        ObjectMapper om = new ObjectMapper();
        Employee emp = om.readValue(annualSalary, Employee.class);
        if(emp != null){
        double updateMonthlySalary = emp.getCtc()/12;
        int empId = emp.getId();

        pay.setEmployeeId(empId);
        pay.setMonthlySalary(updateMonthlySalary);
        repository.save(pay);
        }
    }

    public String createPayroll(Payroll payroll){
      //  payroll.setPayrollId(sequenceGenerator.getSequence(DbSequence.SEQUENCE_NAME));

        //payroll.setMonthlySalary(monthlySalary);
        Payroll empPay = repository.findByEmployeeId(payroll.getEmployeeId());
        if(empPay != null) {
            empPay.setRegisteredBank(payroll.getRegisteredBank());
            empPay.setBankAccountNumber(payroll.getBankAccountNumber());
            Payroll pay = repository.save(empPay);
            return "Payroll created successfully with id : "+pay.getPayrollId();
        }
        return  "Failed in creating Payroll";
    }


    public Payroll updatePayroll(int id, Payroll payroll){
        Payroll pay = repository.findById(id).get();
        pay.setEmployeeId(payroll.getEmployeeId());
        pay.setBankAccountNumber(payroll.getBankAccountNumber());
        pay.setMonthlySalary(payroll.getMonthlySalary());
        pay.setRegisteredBank(payroll.getRegisteredBank());
        return repository.save(payroll);
    }

    @KafkaListener(topics="Update_Annaul_CTC", groupId = "groupId")
    public void updatePayrollByKafka(String empCtc) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Employee emp = om.readValue(empCtc, Employee.class);
        double updateMonthlySalary = emp.getCtc()/12;
        int empId = emp.getId();
        Payroll pay = repository.findByEmployeeId(empId);
        if(pay != null){
            pay.setMonthlySalary(updateMonthlySalary);
            repository.save(pay);
        }
    }



    public Payroll getPayroll(int empId){
        return repository.findByEmployeeId(empId);
    }
}
