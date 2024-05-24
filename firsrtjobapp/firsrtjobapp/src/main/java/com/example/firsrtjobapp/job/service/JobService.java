package com.example.firsrtjobapp.job.service;

import com.example.firsrtjobapp.job.entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
   List<Job> findAll();
   void createJob(Job job);
   Job getJobById(Long id);
   boolean deleteJob(Long id);

   boolean updateJob(Long id, Job updatedJob);
}
