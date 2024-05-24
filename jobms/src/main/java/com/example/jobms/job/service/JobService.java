package com.example.jobms.job.service;


import com.example.jobms.job.dto.JobWithCompanyDTO;
import com.example.jobms.job.entity.Job;

import java.util.List;

public interface JobService {
   List<JobWithCompanyDTO> findAll();
   void createJob(Job job);
   JobWithCompanyDTO getJobById(Long id);
   boolean deleteJob(Long id);
   boolean updateJob(Long id, Job updatedJob);

}
