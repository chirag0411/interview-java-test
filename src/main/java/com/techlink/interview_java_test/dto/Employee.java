package com.techlink.interview_java_test.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {

    @Positive(message = "id must be greater than 0")
    private int id;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "salary is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "salary must be greater than 0")
    private BigDecimal salary;

    @Positive(message = "departmentId must be greater than 0")
    private int departmentId;

    private User user;
}
