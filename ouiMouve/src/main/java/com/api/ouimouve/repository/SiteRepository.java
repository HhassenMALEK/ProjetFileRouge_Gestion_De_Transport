package com.api.ouimouve.repository;


import com.api.ouimouve.bo.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    /**
     * Find all sites by name.
     *
     * @param name the name of the site
     * @return a list of sites with the specified name
     */
    List<Site> findAllByName(String name);

}
