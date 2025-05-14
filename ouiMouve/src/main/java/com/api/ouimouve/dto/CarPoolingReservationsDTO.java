package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.CarPoolingStatus;
import lombok.Data;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for CarPoolingReservations.
 */
@Data
public class CarPoolingReservationsDTO {
    private long id;
    private Date date;
    private long userId;
    private long carPoolingId;
    private CarPoolingStatus status;
}
