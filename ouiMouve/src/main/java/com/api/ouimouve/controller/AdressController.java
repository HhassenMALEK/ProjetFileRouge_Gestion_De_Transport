package com.api.ouimouve.controller;

import com.api.ouimouve.dto.AdressDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.AdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adress ")
public class AdressController {

    @Autowired
    private AdressService adressService;

    @GetMapping("/{id}")
    public AdressDto getAdressById(@PathVariable long id) throws RessourceNotFoundException {
        AdressDto adress = adressService.getAdressById(id);
        if (adress == null) {
            throw new RessourceNotFoundException("Adress not found");
        }
        return adress;
    }

    @GetMapping("/{latX}/{longY}")
    public AdressDto getAdressById(@PathVariable float latX, @PathVariable float longY) throws RessourceNotFoundException {
        AdressDto adress = adressService.getAdressByLatXAndLatY(latX, longY);
        if (adress == null) {
            throw new RessourceNotFoundException("Adress not found");
        }
        return adress;
    }

    @GetMapping("/{label}/{city}")
    public AdressDto getAdressById(@PathVariable String label, @PathVariable String city) throws RessourceNotFoundException {
        AdressDto adress = adressService.getSiteAdressByLabelAndCity(label, city);
        if (adress == null) {
            throw new RessourceNotFoundException("Adress not found");
        }
        return adress;
    }

    @DeleteMapping("/{id}")
    public  AdressDto deleteAdress(@PathVariable long id) throws RessourceNotFoundException {
        AdressDto adress = adressService.deleteAdress(id);
        if (adress == null) {
            throw new RessourceNotFoundException("Adress don't exist");
        }
        return adress;
    }

    @PatchMapping("/{id}")
    public AdressDto updateAdress(@PathVariable long id, @RequestBody AdressDto adress) throws RessourceNotFoundException {
        AdressDto adressUpdate = adressService.updateAdress(id, adress);
        if (adressUpdate == null) {
            throw new RessourceNotFoundException("Adress not found");
        }
        return adressUpdate;
    }

    @PostMapping()
    public AdressDto createAdress(@RequestBody AdressDto adress) throws RessourceNotFoundException {
        return adressService.createAdresse(adress);
    }
}
