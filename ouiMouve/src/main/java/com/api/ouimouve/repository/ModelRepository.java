package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Model;
import com.api.ouimouve.enumeration.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for Model entity.
 * Provides methods to interact with the database.
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByMark(String mark);
    List<Model> findByCategory(VehicleCategory category);
    List<Model> findByModelName(String name);

}
