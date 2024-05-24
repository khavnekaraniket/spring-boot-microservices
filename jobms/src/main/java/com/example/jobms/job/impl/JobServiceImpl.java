package com.example.jobms.job.impl;


import com.example.jobms.job.dto.JobWithCompanyDTO;
import com.example.jobms.job.entity.Job;

import com.example.jobms.job.entity.Company;
import com.example.jobms.job.repository.JobRepository;
import com.example.jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private JobWithCompanyDTO convertToDto(Job job) {
        RestTemplate restTemplate = new RestTemplate();
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return  convertToDto(job);
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
