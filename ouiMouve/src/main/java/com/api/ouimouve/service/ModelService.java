package com.api.ouimouve.service;

import com.api.ouimouve.bo.Model;
import com.api.ouimouve.dto.ModelDto;
import com.api.ouimouve.mapper.ModelMapper;
import com.api.ouimouve.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all models
     * @return a list of ModelDto
     */
    public List<ModelDto> getAllModels() {
        return modelRepository.findAll().stream()
                .map(modelMapper::toModelDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a model by its ID
     * @param id the ID of the model
     * @return the ModelDto if found, null otherwise
     */
    public ModelDto getModelById(long id) {
        return modelRepository.findById(id)
                .map(modelMapper::toModelDto)
                .orElse(null);
    }

    /**
     * Create a new model
     * @param modelDto the ModelDto to create
     * @return the created ModelDto
     */
    public ModelDto createModel(ModelDto modelDto) {
        Model model = modelMapper.toModel(modelDto);
        model = modelRepository.save(model);
        return modelMapper.toModelDto(model);
    }

    /**
     * Delete an existing model
     * @param id the ID of the model to delete
     * @return the deleted ModelDto
     */
    public ModelDto deleteModel(long id) {
        ModelDto modelDto = getModelById(id);
        if (modelDto != null) {
            modelRepository.deleteById(id);
        }
        return modelDto;
    }

    /**
     * Update an existing model
     * @param id the ID of the model to update
     * @param modelDto the new model data
     * @return the updated ModelDto
     */
    public ModelDto updateModel(long id, ModelDto modelDto) {
        if (getModelById(id) != null) {
            modelRepository.findById(id).ifPresent(model -> {
                model.setModelName(modelDto.getModelName());
                model.setMark(modelDto.getMark());
                model.setPhotoURL(modelDto.getPhotoURL());
                model.setMotorType(modelDto.getMotorType());
                model.setCategory(modelDto.getCategory());
                model.setCO2(modelDto.getCO2());
                model.setPlacesModel(modelDto.getPlacesModel());

                modelRepository.save(model);
            });
            return getModelById(id);
        }
        throw new RuntimeException("Model not found");
    }


}