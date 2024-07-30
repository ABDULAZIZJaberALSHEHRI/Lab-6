package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "ID cannot be Empty!")
    @Size(min = 2, max = 10, message = "ID must be more than '2' characters and less than '10' !")
    private String id;

    @NotEmpty(message = "Name cannot be Empty!")
    @Size(min = 4, max = 20, message = "Name must be more than '4' characters and less than '20' !")
    @Pattern(regexp = "^[a-zA-Z '.-]*$",message = "Name must contain only 'characters' and '-' !")
    private String name;

    @NotEmpty(message = "Email cannot be Empty!")
    @Email(message = "The email doesn't contain '@' !")
    private String eMail;

    @NotEmpty(message = "Phone number cannot be Empty!")
    @Pattern(regexp = "^05\\d*$",message = "Phone number must start with '05' !")
    @Size(min = 10,max = 10,message = "Phone number must be '10' digit only!")
    private String phoneNumber;

    @NotNull(message = "Age cannot be Empty!")
    @Positive(message = "Age must be positive number!")
    @Min(value = 25,message = "Age must be more than '25' !")
    private int age;

    @NotEmpty(message = "Position cannot be Empty!")
    @Pattern(regexp = "^(supervisor|coordinator)$",message = "Position should be either 'supervisor' or 'coordinator' !")
    private String position;

    @AssertFalse
    private boolean onLeave;

    @NotNull(message = "Date cannot be Empty!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date should be in 'present' or 'past' !")
    private LocalDate employmentYear;

    @NotNull(message = "Annual leave cannot be Empty!")
    @Positive(message = "Annual leave must be 'Positive number'")
    private int annualLeave;
}
