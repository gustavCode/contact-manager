package com.gustavofosu.contact_manager.model;

/*
    Created by Gustav on 2/7/2025
    @author : Gustav
    @date : 2/7/2025
    @project : contact-manager
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class ContactDTO {
    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty(message = "The email is required")
    @Email(message = "Email must be of form ---@----.---")
    private String email;

    @NotEmpty(message = "The phone number is required")
    private String phone;

    @NotEmpty(message = "The address is required")
    private String address;

    @NotNull(message = "The birthday is required")
    private LocalDate birthday;

    @NotEmpty(message = "The occupation is required")
    private String occupation;

    @NotEmpty(message = "The group is required")
    private String tags;

    @Size(min = 10, message = "The notes should be at least 10 characters")
    @Size(max = 2000, message = "The notes should not exceed 2000 characters")
    private String notes;

    private MultipartFile imageFile;
}




