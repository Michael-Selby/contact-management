package com.contactmanagementsystem.cms.repository;

import com.contactmanagementsystem.cms.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

}