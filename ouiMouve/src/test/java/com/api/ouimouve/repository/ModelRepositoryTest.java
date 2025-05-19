package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static com.api.ouimouve.enumeration.VehicleCategory.MICRO_URBANE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ModelRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    void findAll_ShouldReturnAllModels() {
        // Given
        Model model1 = createModel("Model3", "Tesla");
        Model model2 = createModel("Model S", "Tesla");
        entityManager.persist(model1);
        entityManager.persist(model2);
        entityManager.flush();

        // When
        List<Model> models = modelRepository.findAll();

        // Then
        assertThat(models).hasSize(2);
        assertThat(models).extracting(Model::getModelName).containsExactlyInAnyOrder("Model3", "Model S");
    }

    @Test
    void findById_WithExistingId_ShouldReturnModel() {
        // Given
        Model model = createModel("Model3", "Tesla");
        entityManager.persist(model);
        entityManager.flush();

        // When
        Optional<Model> found = modelRepository.findById(model.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getModelName()).isEqualTo("Model3");
        assertThat(found.get().getMark()).isEqualTo("Tesla");
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // When
        Optional<Model> found = modelRepository.findById(999L);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void save_ShouldPersistModel() {
        // Given
        Model model = createModel("Model3", "Tesla");

        // When
        Model saved = modelRepository.save(model);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(entityManager.find(Model.class, saved.getId())).isNotNull();
    }

    @Test
    void deleteById_ShouldRemoveModel() {
        // Given
        Model model = createModel("Model3", "Tesla");
        entityManager.persist(model);
        entityManager.flush();
        long id = model.getId();

        // When
        modelRepository.deleteById(id);

        // Then
        assertThat(entityManager.find(Model.class, id)).isNull();
    }

    @Test
    void findByMark_WithExistingMark_ShouldReturnModels() {
        // Given
        Model model1 = createModel("Model3", "Tesla");
        Model model2 = createModel("Model S", "Tesla");
        Model model3 = createModel("A4", "Audi");
        entityManager.persist(model1);
        entityManager.persist(model2);
        entityManager.persist(model3);
        entityManager.flush();

        // When
        List<Model> models = modelRepository.findByMark("Tesla");

        // Then
        assertThat(models).hasSize(2);
        assertThat(models).extracting(Model::getModelName).containsExactlyInAnyOrder("Model3", "Model S");
    }

    @Test
    void findByMark_WithNonExistingMark_ShouldReturnEmptyList() {
        // Given
        Model model = createModel("Model3", "Tesla");
        entityManager.persist(model);
        entityManager.flush();

        // When
        List<Model> models = modelRepository.findByMark("BMW");

        // Then
        assertThat(models).isEmpty();
    }

    private Model createModel(String modelName, String mark) {
        Model model = new Model();
        model.setModelName(modelName);
        model.setMark(mark);
        model.setMotorType("Electric");
        model.setCategory(MICRO_URBANE);
        model.setCO2(0);
        model.setSeatsModel(5);
        model.setPhotoURL("http://example.com/photo.jpg");
        return model;
    }
}