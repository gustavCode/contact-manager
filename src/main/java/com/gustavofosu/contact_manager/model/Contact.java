package com.gustavofosu.contact_manager.model;

/*
    Created by Gustav on 1/16/2025
    @author : Gustav
    @date : 1/16/2025
    @project : contact-manager
*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthday;
    private String occupation;
    private String tags;
    private String notes;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String imageFileName;
}




