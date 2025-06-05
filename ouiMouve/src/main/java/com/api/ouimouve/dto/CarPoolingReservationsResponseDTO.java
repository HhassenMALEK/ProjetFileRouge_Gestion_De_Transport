package com.api.ouimouve.dto;


import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object (DTO) for CarPoolingReservations.
 */
@Data
public class CarPoolingReservationsResponseDTO {
    private long id;
    private Date date;
    private CarPoolingResponseDto carPooling;
    private CarPoolingReservationStatus status;
    private Integer participantCount;
    private List<String> bookedUsers;
}
