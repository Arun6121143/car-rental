package com.carrental.carrental.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "location_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @Column(name="ID")
    private Integer locationId;
    @Column(name="Name")
    private String locationName;
    @Column(name="latitude")
    private String latitude;
    @Column(name = "longtitude")
    private String longitude;
}