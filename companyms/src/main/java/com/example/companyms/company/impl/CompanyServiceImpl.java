package com.example.companyms.company.impl;


import com.example.companyms.company.clients.ReviewClient;
import com.example.companyms.company.dto.ReviewMessage;
import com.example.companyms.company.entity.Company;
import com.example.companyms.company.repository.CompanyRepository;
import com.example.companyms.company.service.CompanyService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    private ReviewClient reviewClient;

    public CompanyServiceImpl(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        System.out.println(companyRepository.findById(id));
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try {
            if (companyRepository.existsById(id)) {
                companyRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> existingCompanyOptional = companyRepository.findById(id);
        if (existingCompanyOptional.isPresent()) {
            Company existingCompany = existingCompanyOptional.get();
            existingCompany.setName(updatedCompany.getName());
            existingCompany.setDescription(updatedCompany.getDescription());
            companyRepository.save(existingCompany);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company =companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("company not found" + reviewMessage.getCompanyId()));
        double avrageRating = reviewClient.getAvarageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(avrageRating);
        companyRepository.save(company);
    }
}
