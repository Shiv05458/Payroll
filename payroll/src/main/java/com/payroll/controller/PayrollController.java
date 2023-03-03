package com.payroll.controller;

import com.payroll.model.Payroll;
import com.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService service;


    @PostMapping("/v1/create")
    public ResponseEntity<String> createPayroll(@RequestBody Payroll payroll){
        String str = service.createPayroll(payroll);
        return new ResponseEntity<>(str, HttpStatus.CREATED);
    }

    @PutMapping("/v1/update/{pId}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable int pId, @RequestBody Payroll payroll){
        Payroll pay = service.updatePayroll(pId, payroll);
        return new ResponseEntity<>(pay, HttpStatus.OK);
    }

    @GetMapping("/v1/details/{empId}")
    public ResponseEntity<Payroll> getPayrollDetails(@PathVariable int empId){
        Payroll pay = service.getPayroll(empId);
        return new ResponseEntity<>(pay, HttpStatus.OK);
    }

}
