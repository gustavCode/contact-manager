package com.gustavofosu.contact_manager.repository;

/*
    Created by Gustav on 1/16/2025
    @author : Gustav
    @date : 1/16/2025
    @project : contact-manager
*/

import com.gustavofosu.contact_manager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Custom queries can be added here if needed
}
