package com.gustavofosu.contact_manager.controller;

/*
    Created by Gustav on 2/7/2025
    @author : Gustav
    @date : 2/7/2025
    @project : contact-manager
*/

import com.gustavofosu.contact_manager.model.Contact;
import com.gustavofosu.contact_manager.model.ContactDTO;
import com.gustavofosu.contact_manager.service.ContactService2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController2 {
    private final ContactService2 contactService;

    @GetMapping
    public String getAllContacts(Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "8") int size,
                                 @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Contact> contactPage = contactService.searchContacts(keyword, page, size);
        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "contacts";
    }

    @GetMapping("/add")
    public String showAddContactPage(Model model) {
        ContactDTO contactDto = new ContactDTO();
        model.addAttribute("contactDto", contactDto);
        return "add-contact";
    }

    @PostMapping("/add")
    public String addContact(@Valid @ModelAttribute("contactDto") ContactDTO contactDto, BindingResult bindingResult) {
        if (contactDto.getImageFile().isEmpty()) {
            bindingResult.addError(
                    new FieldError("contactDto", "imageFile", "Image file is required")
            );
        }

        if (bindingResult.hasErrors()) {
            return "add-contact";
        }

        contactService.saveContact(contactDto);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String showEditContactPage(@PathVariable Long id, Model model) {
        try {
            Contact contact = contactService.getContactById(id);
            model.addAttribute("contact", contact);

            ContactDTO contactDto = new ContactDTO();
            contactDto.setName(contact.getName());
            contactDto.setEmail(contact.getEmail());
            contactDto.setPhone(contact.getPhone());
            contactDto.setAddress(contact.getAddress());
            contactDto.setBirthday(contact.getBirthday());
            contactDto.setOccupation(contact.getOccupation());
            contactDto.setTags(contact.getTags());
            contactDto.setNotes(contact.getNotes());

            model.addAttribute("contactDto", contactDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/contacts";
        }

        return "edit-contact";
    }

    @PostMapping("/edit/{id}")
    public String editContact(Model model,
                              @PathVariable Long id,
                              @Valid @ModelAttribute("contactDto") ContactDTO contactDto,
                              BindingResult bindingResult) {
        try {
            Contact contact = contactService.getContactById(id);
            model.addAttribute("contact", contact);

            if (bindingResult.hasErrors()) {
                return "edit-contact";
            }

            contactService.updateContact(contact, contactDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/contacts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/contacts";
    }
}













