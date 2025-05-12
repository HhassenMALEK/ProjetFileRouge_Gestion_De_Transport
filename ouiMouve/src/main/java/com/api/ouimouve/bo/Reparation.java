package com.api.ouimouve.bo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Date start;
    @Column(nullable = false)
    private Date end;
    @Column(nullable = false)
    private String motive;
//    @ManyToOne
//    @JoinColumn(name = "vehicle_id", nullable = false)
//    private Vehicle vehicle;
}
