package com.payroll.controller;

import com.payroll.model.Payroll;
import com.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService service;


    @PostMapping("/create")
    public ResponseEntity<String> createPayroll(@RequestBody Payroll payroll){
        String str = service.createPayroll(payroll);
        return new ResponseEntity<>(str, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable int id, @RequestBody Payroll payroll){
        Payroll pay = service.updatePayroll(id, payroll);
        return new ResponseEntity<>(pay, HttpStatus.OK);
    }

    @GetMapping("/getPayroll/details/{id}")
    public ResponseEntity<Payroll> getPayrollDetails(@PathVariable int id){
        Payroll pay = service.getPayroll(id);
        return new ResponseEntity<>(pay, HttpStatus.OK);
    }
}
