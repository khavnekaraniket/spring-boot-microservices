package com.example.firsrtjobapp.company.entity;

import com.example.firsrtjobapp.job.entity.Job;
import com.example.firsrtjobapp.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private  String name ;
    private  String description ;

    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    public  List<Job> jobs;


    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    public  List<Review> reviews;

}
