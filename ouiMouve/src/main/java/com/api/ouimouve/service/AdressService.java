package com.api.ouimouve.service;

import com.api.ouimouve.dto.AdressDto;
import com.api.ouimouve.mapper.AdressMapper;
import com.api.ouimouve.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdressService {

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private AdressMapper adressMapper;

    // Add methods to handle CRUD operations for Adress entity

    public AdressDto getAdressById(Long id){
        return adressRepository.findById(id)
                .map(adressMapper::toAdressDto)
                .orElse(null);

    }

    /**
     * Fetches an adress by label and City
     * @param label of this adress
     * @param city  of this adress
     * @return an adress
     */
    public AdressDto getSiteAdressByLabelAndCity(String label, String city){
        return adressMapper.toAdressDto(
                adressRepository.findAdressByLabelAndCity(label, city)
        );

    }

    /**
     * Fetches an adress by her latitude and longitude
     * @param latX
     * @param longY
     * @return
     */
    public AdressDto getAdressByLatXAndLatY(float latX, float longY){
        return adressMapper.toAdressDto(
                adressRepository.findAdressByLatXAndLongY(latX, longY)
        );

    }

    /**
     * Create a new adress
     * @param adressDto
     * @return the AdressDto Object
     */
    public AdressDto createAdresse(AdressDto adressDto){
        // TODO test sur l'existence avant création sinon renvoit d'une erreur
            return adressMapper.toAdressDto(
                    adressRepository.save(adressMapper.toAdress(adressDto)));

    }



    /**
     * Update an existing adress
     * @param id the id of thie adress
     * @param adressDto the updated adressDto
     * @return the updated adressDto
     */
    public AdressDto updateAdress(long id, AdressDto adressDto){
        if(getAdressById(id) != null){
            adressRepository.findById(id).ifPresent(adress -> {
                adressDto.setLabel(adressDto.getLabel());
                adressDto.setCity(adressDto.getCity());
                adressDto.setLatX(adressDto.getLatX());
                adressDto.setLongY(adressDto.getLongY());
                adressDto.setSiteId(adressDto.getSiteId());
                adressDto.setDeparturesId(adressDto.getDeparturesId());
                adressDto.setArrivalsId(adressDto.getArrivalsId());
                adressRepository.save(adress);
            });
        }
        throw new RuntimeException("Reparation not found");
    }

    /**
     * Delete an adress
     * @param id the id of the adress to delete
     * @return adressDto deleted
     */
    public AdressDto deleteAdress(long id){
        AdressDto adressDto = getAdressById(id);
        //check if this adress exists before deleting
        if(adressDto != null){
            adressRepository.deleteById(id);
        }
        return adressDto;
    }





}
