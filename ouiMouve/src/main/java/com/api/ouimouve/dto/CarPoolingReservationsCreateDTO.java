package com.api.ouimouve.dto;

import java.util.Date;

import org.springframework.lang.Nullable;

import com.api.ouimouve.enumeration.CarPoolingReservationStatus;

import lombok.Data;

@Data
public class CarPoolingReservationsCreateDTO {
    private Date date;
    private long userId;
    private long carPoolingId;
    @Nullable
    private CarPoolingReservationStatus status;
    @Nullable
    private Long id;
}
