package com.example.cloudtest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;


public record WorkerRequest(@NotBlank(message = "Name must not be blank")
                            @Size(max = 50, message = "Name must not exceed 50 characters") String name,
                            @NotBlank(message = "Surname must not be blank")
                            @Size(max = 50, message = "Surname must not exceed 50 characters") String surName,
                            @NotNull(message = "Date of birth must not be null")
                            @Past(message = "Date of birth must be in the past")
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
                            LocalDate dateOfBirth,
                            @NotBlank(message = "Address must not be blank") String address) {
}
