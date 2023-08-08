package com.carrental.carrental.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "car_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID carId;
    private String carName;
    private Integer carRentalPrice;
    @Column(columnDefinition = "boolean default false")
    private Boolean bookedStatus;
    @Column(name = "car_image_url")
    private String carImageUrl;
    @OneToMany
    private List<Feedback> feedbackcarList;
}