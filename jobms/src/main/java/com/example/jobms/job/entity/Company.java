package com.example.jobms.job.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private Long id ;
    private  String name ;
    private  String description ;
}

