package com.example.firsrtjobapp.job.impl;

import com.example.firsrtjobapp.job.entity.Job;

import com.example.firsrtjobapp.job.repository.JobRepository;
import com.example.firsrtjobapp.job.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> existingJobOptional = jobRepository.findById(id);
        if (existingJobOptional.isPresent()) {
            // Get the existing job
            Job existingJob = existingJobOptional.get();

            // Update the non-null fields of the existing job with the new details
            if (updatedJob.getTitle() != null) {
                existingJob.setTitle(updatedJob.getTitle());
            }
            if (updatedJob.getDescription() != null) {
                existingJob.setDescription(updatedJob.getDescription());
            }
            if (updatedJob.getMinSalary() != null) {
                existingJob.setMinSalary(updatedJob.getMinSalary());
            }
            if (updatedJob.getMaxSalary() != null) {
                existingJob.setMaxSalary(updatedJob.getMaxSalary());
            }
            if (updatedJob.getLocation() != null) {
                existingJob.setLocation(updatedJob.getLocation());
            }

            // Save the updated job
            jobRepository.save(existingJob);
            return true;
        } else {
            return false;
        }
    }


}
