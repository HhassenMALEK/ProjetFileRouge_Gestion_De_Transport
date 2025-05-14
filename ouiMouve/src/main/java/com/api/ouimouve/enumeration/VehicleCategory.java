package com.api.ouimouve.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;


/** * Enum representing different vehicle categories.
 * Each category is associated with a specific string value.
 */
@Getter
@AllArgsConstructor
public enum VehicleCategory {

    /**
     * Represents a vehicle category with the string value "MICRO URBANE".
     */
    MICRO_URBANE("MICRO URBANE"),

    /**
     * Represents a vehicle category with the string value "URBAINE".
     */
    MINI_CITADINE("MINI CITADINE"),

    /**
     * Represents a vehicle category with the string value "CITADINE POLYVALENTE".
     */
    CITADINE_POLYVALENTE("CITADINE POLYVALENTE"),

    /**
     * Represents a vehicle category with the string value "COMPACTE".
     */
    COMPACTE("COMPACTE"),
    BERLINE_TAILLE_S("BERLINE TAILLE S"),

    /**
     * Represents a vehicle category with the string value "BERLINE TAILLE M".
     */
    BERLINE_TAILLE_M("BERLINE TAILLE M"),

    /**
     * Represents a vehicle category with the string value "BERLINE TAILLE L".
     */
    BERLINE_TAILLE_L("BERLINE TAILLE L"),

    /**
     * Represents a vehicle category with the string value "SUV_TOUT_TERRAIN_ET_PICKUP".
     */
    SUV_TOUT_TERRAIN_ET_PICKUP("SUV TOUT TERRAIN ET PICK UP"),
    ;

    private final String category;


}