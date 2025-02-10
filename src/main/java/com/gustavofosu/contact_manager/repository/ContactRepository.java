package com.gustavofosu.contact_manager.repository;

/*
    Created by Gustav on 1/16/2025
    @author : Gustav
    @date : 1/16/2025
    @project : contact-manager
*/

import com.gustavofosu.contact_manager.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Custom queries can be added here if needed

    @Query("SELECT c FROM Contact c " +
            "WHERE LOWER(SUBSTRING(c.name, 1, LOCATE(' ', c.name) - 1)) LIKE LOWER(CONCAT(:keyword, '%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Contact> searchByFirstNameOrEmail(@Param("keyword") String keyword, Pageable pageable);

}





