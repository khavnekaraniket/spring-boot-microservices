package com.example.companyms.company.service;



import com.example.companyms.company.dto.ReviewMessage;
import com.example.companyms.company.entity.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> getAllCompanies();
    void createCompany(Company company);

    Company getCompanyById(Long id);

    boolean deleteCompany(Long id);

    boolean updateCompany(Long id, Company updatedCompany);
    public void updateCompanyRating(ReviewMessage reviewMessage);
}
