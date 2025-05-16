package com.api.ouimouve.controller;

import com.api.ouimouve.dto.ModelDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.api.ouimouve.enumeration.VehicleCategory.MINI_CITADINE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ModelControllerTest {

    @Mock
    private ModelService modelService;

    @InjectMocks
    private ModelController modelController;

    private MockMvc mockMvc;

    private ModelDto modelDto;
    private List<ModelDto> modelDtos;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(modelController).build();

        // Initialisation des données de test
        modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setModelName("Model3");
        modelDto.setMark("Tesla");
        modelDto.setMotorType("Electric");
        modelDto.setCategory(MINI_CITADINE);
        modelDto.setCO2(0);
        modelDto.setPlacesModel(5);
        modelDto.setPhotoURL("http://example.com/photo.jpg");

        modelDtos = new ArrayList<>();
        modelDtos.add(modelDto);
    }

    @Test
    void getAllModels_WithModels_ShouldReturnListOfModels() throws Exception {
        // Given
        when(modelService.getAllModels()).thenReturn(modelDtos);

        // When & Then
        mockMvc.perform(get("/api/model/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].modelName").value("Model3"))
                .andExpect(jsonPath("$[0].mark").value("Tesla"));
    }

    @Test
    void getAllModels_WithNoModels_ShouldThrowException() throws Exception {
        // Given
        when(modelService.getAllModels()).thenReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/api/model/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getModelById_WithExistingId_ShouldReturnModel() throws Exception {
        // Given
        when(modelService.getModelById(anyLong())).thenReturn(modelDto);

        // When & Then
        mockMvc.perform(get("/api/model/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelName").value("Model3"))
                .andExpect(jsonPath("$.mark").value("Tesla"));
    }

    @Test
    void getModelById_WithNonExistingId_ShouldThrowException() throws Exception {
        // Given
        when(modelService.getModelById(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/model/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createModel_ShouldReturnCreatedModel() throws Exception {
        // Given
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\",\"category\":CITADINE_POLYVALENTE,\"co2\":0,\"placesModel\":5,\"photoURL\":\"http://example.com/photo.jpg\"}";
        when(modelService.createModel(any(ModelDto.class))).thenReturn(modelDto);

        // When & Then
        mockMvc.perform(post("/api/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelName").value("Model3"))
                .andExpect(jsonPath("$.mark").value("Tesla"));
    }

    @Test
    void deleteModel_WithExistingId_ShouldReturnDeletedModel() throws Exception {
        // Given
        when(modelService.deleteModel(anyLong())).thenReturn(modelDto);

        // When & Then
        mockMvc.perform(delete("/api/model/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelName").value("Model3"))
                .andExpect(jsonPath("$.mark").value("Tesla"));
    }

    @Test
    void deleteModel_WithNonExistingId_ShouldThrowException() throws Exception {
        // Given
        when(modelService.deleteModel(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(delete("/api/model/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateModel_WithExistingId_ShouldReturnUpdatedModel() throws Exception {
        // Given
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\",\"category\":CITADINE_POLYVALENTE,\"co2\":0,\"placesModel\":5,\"photoURL\":\"http://example.com/photo.jpg\"}";
        when(modelService.updateModel(anyLong(), any(ModelDto.class))).thenReturn(modelDto);

        // When & Then
        mockMvc.perform(patch("/api/model/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelName").value("Model3"))
                .andExpect(jsonPath("$.mark").value("Tesla"));
    }

    @Test
    void updateModel_WithNonExistingId_ShouldThrowException() throws Exception {
        // Given
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\",\"category\":CITADINE_POLYVALENTE,\"co2\":0,\"placesModel\":5,\"photoURL\":\"http://example.com/photo.jpg\"}";
        when(modelService.updateModel(anyLong(), any(ModelDto.class))).thenThrow(new RessourceNotFoundException("The model does not exist"));

        // When & Then
        mockMvc.perform(patch("/api/model/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createModel_WithInvalidHttpPrefix_ShouldReturnBadRequest() throws Exception {
        // Test with URL that doesn't start with http:// or https://
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\"," +
                "\"category\":2,\"co2\":0,\"placesModel\":5," +
                "\"photoURL\":\"ftp://example.com/photo.jpg\"}";

        mockMvc.perform(post("/api/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createModel_WithInvalidFileExtension_ShouldReturnBadRequest() throws Exception {
        // Test with URL that has incorrect file extension
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\"," +
                "\"category\":2,\"co2\":0,\"placesModel\":5," +
                "\"photoURL\":\"http://example.com/photo.gif\"}";

        mockMvc.perform(post("/api/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateModel_WithInvalidPhotoURL_ShouldReturnBadRequest() throws Exception {
        // Test with URL that doesn't have required image extension
        String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\"," +
                "\"category\":2,\"co2\":0,\"placesModel\":5," +
                "\"photoURL\":\"http://example.com/photo\"}";

        mockMvc.perform(patch("/api/model/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modelJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createModel_WithValidPhotoURL_ShouldReturnOk() throws Exception {
        // Test with all three allowed extensions
        String[] validUrls = {
                "http://example.com/photo.jpg",
                "https://example.com/image.png",
                "https://example.com/icon.svg"
        };

        for (String url : validUrls) {
            String modelJson = "{\"modelName\":\"Model3\",\"mark\":\"Tesla\",\"motorType\":\"Electric\"," +
                    "\"category\":2,\"co2\":0,\"placesModel\":5," +
                    "\"photoURL\":\"" + url + "\"}";

            when(modelService.createModel(any(ModelDto.class))).thenReturn(modelDto);

            mockMvc.perform(post("/api/model")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(modelJson))
                    .andExpect(status().isOk());
        }
    }
}
