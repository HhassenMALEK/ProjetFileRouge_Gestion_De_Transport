package com.api.ouimouve.repository;


import com.api.ouimouve.bo.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes
    List<Site> findAllByName(String name);

}
