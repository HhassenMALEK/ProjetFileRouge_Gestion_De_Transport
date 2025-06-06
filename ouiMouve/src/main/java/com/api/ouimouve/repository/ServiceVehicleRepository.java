package com.api.ouimouve.repository;

import com.api.ouimouve.bo.ServiceVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Reparation entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface ServiceVehicleRepository extends JpaRepository<ServiceVehicle, Long> {

    List<ServiceVehicle> findAllBySiteId(Long siteId);

    /**
     * Finds all service vehicles by site ID and model ID.
     *
     * @param siteId the ID of the site
     * @param modelId the ID of the model
     * @return a list of ServiceVehicle objects
     */
    @Query("""
    SELECT sv FROM ServiceVehicle sv
    WHERE (:siteId IS NULL OR sv.site.id = :siteId)
    AND (:modelId IS NULL OR sv.model.id = :modelId)
    AND (:seats IS NULL OR sv.seats >= :seats)
    AND (:status IS NULL OR sv.status =:status)
    """)
    List<ServiceVehicle> findAllByFilters(
            @Param("siteId") Long siteId,
            @Param("modelId") Long modelId,
            @Param("seats") Integer seats,
            @Param ("status") String status
    );

}
