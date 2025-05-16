package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

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
