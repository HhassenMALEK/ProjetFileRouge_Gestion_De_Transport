package com.api.ouimouve.controller;

import com.api.ouimouve.dto.ModelCreateDto;
import com.api.ouimouve.dto.ModelDto;
import com.api.ouimouve.enumeration.VehicleCategory;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    /**
     * Get all models
     *
     * @return a list of ModelDto objects
     * @throws RessourceNotFoundException if no models are found
     */
    @GetMapping("/list")
    @Operation(summary = "Get all models", responses = {
            @ApiResponse(responseCode = "200", description = "List of all models"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No models found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public List<ModelDto> getAllModels() throws RessourceNotFoundException {
        List<ModelDto> models = modelService.getAllModels();
        if (models.isEmpty()) {
            throw new RessourceNotFoundException("No models found");
        }
        return models;
    }

    /**
     * Get a model by its ID
     *
     * @param id the ID of the model
     * @return the ModelDto object
     * @throws RessourceNotFoundException if the model does not exist
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a model by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Model found"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No model found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public ModelDto getModelById(@PathVariable long id) throws RessourceNotFoundException {
        ModelDto model = modelService.getModelById(id);
        if (model == null) {
            throw new RessourceNotFoundException("The model does not exist");
        }
        return model;
    }

    /**
     * Get all models by category
     *
     * @param category the category of the model
     * @return a list of ModelDto objects
     * @throws RessourceNotFoundException if no models are found
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get all models by category", responses = {
            @ApiResponse(responseCode = "200", description = "List of models by category"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No models found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public List<ModelDto> getAllModelsByCategory(@PathVariable VehicleCategory category) throws RessourceNotFoundException {
        List<ModelDto> models = modelService.GetAllModelsByCategory(category);
        if (models.isEmpty()) {
            throw new RessourceNotFoundException("No models found for this category");
        }
        return models;
    }

    /**
     * Delete a model by its ID
     *
     * @param id the ID of the model
     * @return the deleted ModelDto object
     * @throws RessourceNotFoundException if the model does not exist
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a model by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Model deleted"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No model found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public ModelDto deleteModel(@PathVariable long id) throws RessourceNotFoundException {
        ModelDto model = modelService.deleteModel(id);
        if (model == null) {
            throw new RessourceNotFoundException("The model does not exist");
        }
        return model;
    }

    /**
     * Update a model by its ID
     *
     * @param id the ID of the model
     * @param modelDto the updated ModelDto object
     * @return the updated ModelDto object
     * @throws RessourceNotFoundException if the model does not exist
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Update a model by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Model updated"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No model found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public ModelDto updateModel(@PathVariable long id, @Valid @RequestBody ModelCreateDto modelDto) throws RessourceNotFoundException {
        ModelDto updatedModel = modelService.updateModel(id, modelDto);
        if (updatedModel == null) {
            throw new RessourceNotFoundException("The model does not exist");
        }
        return updatedModel;
    }

    /**
     * Create a new model
     *
     * @param modelDto the ModelDto object to create
     * @return the created ModelDto object
     */
    @PostMapping()
    @Operation(summary = "Create a model", responses = {
            @ApiResponse(responseCode = "200", description = "Model created"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public ModelDto createModel( @Valid @RequestBody ModelCreateDto modelDto) {
        return modelService.createModel(modelDto);
    }
}
