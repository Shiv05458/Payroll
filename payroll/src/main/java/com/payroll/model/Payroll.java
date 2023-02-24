package com.payroll.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data

@Document(collection = "Payroll")
public class Payroll {

    @Id
    private int payrollId;
    private int employeeId;
    private String registeredBank;
    private String bankAccountNumber;
    private double monthlySalary;
}
