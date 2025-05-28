package com.api.ouimouve.repository;


import com.api.ouimouve.bo.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    /**
     * Find all sites by name.
     *
     * @param name the name of the site
     * @return a list of sites with the specified name
     */
    List<Site> findAllByName(String name);
    Optional<Site> findByName(String name);

    // Adress findAdressByLabelAndCity(@Param("label") String label, @Param("city") String city);
    //Adress findAdressByLatXAndLongY(@Param("latX") float latX, @Param("longY") float longY);
}
