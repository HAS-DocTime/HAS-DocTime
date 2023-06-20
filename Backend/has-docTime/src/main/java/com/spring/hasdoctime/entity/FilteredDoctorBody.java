package com.spring.hasdoctime.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class FilteredDoctorBody {

    private List<Symptom> symptoms;
    private Timestamp timeSlotStartTime;
    private Timestamp timeSlotEndTime;
    private String description;
}
