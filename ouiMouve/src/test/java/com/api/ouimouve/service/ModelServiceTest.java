package com.api.ouimouve.service;

import com.api.ouimouve.bo.Model;
import com.api.ouimouve.dto.ModelDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.ModelMapper;
import com.api.ouimouve.repository.ModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ModelService modelService;

    private Model model;
    private ModelDto modelDto;
    private List<Model> models;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        model = new Model();
        model.setId(1L);
        model.setModelName("Model3");
        model.setMark("Tesla");
        model.setMotorType("Electric");
        model.setCategory(2);
        model.setCO2(0);
        model.setPlacesModel(5);
        model.setPhotoURL("http://example.com/photo.jpg");

        modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setModelName("Model3");
        modelDto.setMark("Tesla");
        modelDto.setMotorType("Electric");
        modelDto.setCategory(2);
        modelDto.setCO2(0);
        modelDto.setPlacesModel(5);
        modelDto.setPhotoURL("http://example.com/photo.jpg");

        models = new ArrayList<>();
        models.add(model);
    }

    @Test
    void getAllModels_ShouldReturnListOfModelDtos() {
        // Given
        when(modelRepository.findAll()).thenReturn(models);
        when(modelMapper.toModelDto(any(Model.class))).thenReturn(modelDto);

        // When
        List<ModelDto> result = modelService.getAllModels();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(modelDto);
        verify(modelRepository, times(1)).findAll();
    }

    @Test
    void getModelById_WithExistingId_ShouldReturnModelDto() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(model));
        when(modelMapper.toModelDto(any(Model.class))).thenReturn(modelDto);

        // When
        ModelDto result = modelService.getModelById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(modelDto);
        verify(modelRepository, times(1)).findById(1L);
    }

    @Test
    void getModelById_WithNonExistingId_ShouldReturnNull() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        ModelDto result = modelService.getModelById(999L);

        // Then
        assertThat(result).isNull();
        verify(modelRepository, times(1)).findById(999L);
    }

    @Test
    void createModel_ShouldReturnCreatedModelDto() {
        // Given
        when(modelMapper.toModel(any(ModelDto.class))).thenReturn(model);
        when(modelRepository.save(any(Model.class))).thenReturn(model);
        when(modelMapper.toModelDto(any(Model.class))).thenReturn(modelDto);

        // When
        ModelDto result = modelService.createModel(modelDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(modelDto);
        verify(modelRepository, times(1)).save(model);
        verify(modelMapper, times(1)).toModel(modelDto);
        verify(modelMapper, times(1)).toModelDto(model);
    }

    @Test
    void deleteModel_WithExistingId_ShouldReturnDeletedModelDto() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(model));
        when(modelMapper.toModelDto(any(Model.class))).thenReturn(modelDto);
        doNothing().when(modelRepository).deleteById(anyLong());

        // When
        ModelDto result = modelService.deleteModel(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(modelDto);
        verify(modelRepository, times(1)).findById(1L);
        verify(modelRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteModel_WithNonExistingId_ShouldReturnNull() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        ModelDto result = modelService.deleteModel(999L);

        // Then
        assertThat(result).isNull();
        verify(modelRepository, times(1)).findById(999L);
        verify(modelRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateModel_WithExistingId_ShouldReturnUpdatedModelDto() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(model));
        when(modelMapper.toModel(any(ModelDto.class))).thenReturn(model);
        when(modelRepository.save(any(Model.class))).thenReturn(model);
        when(modelMapper.toModelDto(any(Model.class))).thenReturn(modelDto);

        // When
        ModelDto result = modelService.updateModel(1L, modelDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(modelDto);
        verify(modelRepository, times(1)).findById(1L);
        verify(modelRepository, times(1)).save(model);
    }

    @Test
    void updateModel_WithNonExistingId_ShouldThrowException() {
        // Given
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> modelService.updateModel(999L, modelDto))
                .isInstanceOf(RessourceNotFoundException.class)
                .hasMessage("Model not found");
    }
}