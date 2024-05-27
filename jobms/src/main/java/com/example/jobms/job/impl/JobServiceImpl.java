package com.example.jobms.job.impl;


import com.example.jobms.job.clients.CompanyClient;
import com.example.jobms.job.clients.ReviewClient;
import com.example.jobms.job.dto.JobDTO;
import com.example.jobms.job.entity.Job;

import com.example.jobms.job.entity.Company;
import com.example.jobms.job.entity.Review;
import com.example.jobms.job.mapper.JobMapper;
import com.example.jobms.job.repository.JobRepository;
import com.example.jobms.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ReviewClient reviewClient;

    @Override
    @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return  list;
    }
    private JobDTO convertToDto(Job job) {
        //RestTemplate restTemplate = new RestTemplate();
        //JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        //jobWithCompanyDTO.setJob(job);
        //using restTemplate
        //Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
        //Using Feing
        Company company = companyClient.getCompany(job.getCompanyId());
        //Review using Rest Template
//       ResponseEntity<List<Review>>  reviewResponse = restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null, new ParameterizedTypeReference<List<Review>>() {
//                });
        //Review using Open Feing
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        //  List<Review> reviews = reviewResponse.getBody();
        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);
        // jobDTO.setCompany(company);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
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
