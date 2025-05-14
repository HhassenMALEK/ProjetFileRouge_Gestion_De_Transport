package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Adress;
import com.api.ouimouve.dto.AdressDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {

    //Adress findAdressBySiteId(long siteID);

    @Query("SELECT new Adress(a.id, a.label,a.city,a.latX, a.longY, a.siteId, a.departures, a.arrivals) "+
    "FROM Adress a  WHERE a.label = :label AND a.city = :city")
    AdressDto findAdressByLabelAndCity(@Param("label") String label, @Param("city") String city);

    //List<AdressDto> findByCarPoolingDeparturesId(long departuresId);

    //List<AdressDto> findByCarPoolingArrivalsId(long arrivalsId);

    @Query("SELECT new Adress(a.id, a.label,a.city,a.latX, a.longY, a.siteId, a.departures, a.arrivals) "+
            "FROM Adress a  WHERE a.latX = :latX AND a.longY = :longY")
    AdressDto findAdressByLatXAndLatY(@Param("latX") float latX, @Param("longY") float longY);
}
