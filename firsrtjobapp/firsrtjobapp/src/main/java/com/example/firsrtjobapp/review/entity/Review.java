package com.example.firsrtjobapp.review.entity;

import com.example.firsrtjobapp.company.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title ;
    private  String description ;
    private Double rating ;
    @JsonIgnore
    @ManyToOne
    private Company company ;
}
