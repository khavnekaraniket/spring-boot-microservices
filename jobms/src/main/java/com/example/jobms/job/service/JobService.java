package com.example.jobms.job.service;


import com.example.jobms.job.dto.JobDTO;
import com.example.jobms.job.entity.Job;

import java.util.List;

public interface JobService {
   List<JobDTO> findAll();
   void createJob(Job job);
   JobDTO getJobById(Long id);
   boolean deleteJob(Long id);
   boolean updateJob(Long id, Job updatedJob);

}
