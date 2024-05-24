package com.example.companyms.company.controller;



import com.example.companyms.company.entity.Company;
import com.example.companyms.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        if (companies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(companies);
        } else {
            return ResponseEntity.ok(companies);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Company created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable("id") Long id, @RequestBody Company updatedCompany) {
        boolean success = companyService.updateCompany(id, updatedCompany);
        if (success) {
            return ResponseEntity.ok("Company with ID " + id + " updated successfully");
        } else {
            return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id) {
        boolean success = companyService.deleteCompany(id);
        if (success) {
            return ResponseEntity.ok("Company with ID " + id + " deleted successfully");
        } else {
            return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
        }
    }

}
