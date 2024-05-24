package com.example.jobms.job.dto;

import com.example.jobms.job.entity.Company;
import com.example.jobms.job.entity.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobWithCompanyDTO {
    private Job job;
   private  Company company;
}
