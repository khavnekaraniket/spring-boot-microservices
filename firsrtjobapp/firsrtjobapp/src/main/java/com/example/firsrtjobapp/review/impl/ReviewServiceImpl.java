package com.example.firsrtjobapp.review.impl;

import com.example.firsrtjobapp.company.entity.Company;
import com.example.firsrtjobapp.company.service.CompanyService;
import com.example.firsrtjobapp.review.entity.Review;
import com.example.firsrtjobapp.review.repository.ReviewRepository;
import com.example.firsrtjobapp.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CompanyService companyService;


    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company!= null){
            review.setCompany(company);
             reviewRepository.save(review);
             return true ;
        }else{
            return  false ;
        }
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId)) // Filter by review ID, not company ID
                .findFirst()
                .orElse(null);
    }
    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        // Check if the company exists
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            // Check if the review exists
            Optional<Review> existingReviewOptional = reviewRepository.findById(reviewId);
            if (existingReviewOptional.isPresent()) {
                // Update the review
                Review existingReview = existingReviewOptional.get();
                existingReview.setDescription(updatedReview.getDescription());
                existingReview.setRating(updatedReview.getRating());
                existingReview.setTitle(updatedReview.getTitle());
                // Set the company for the updated review
                existingReview.setCompany(company);
                // Save the updated review
                reviewRepository.save(existingReview);
                return true;
            } else {
                return false; // Review not found
            }
        } else {
            return false; // Company not found
        }
    }
    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        // Check if the company exists
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            // Check if the review exists
            Optional<Review> existingReviewOptional = reviewRepository.findById(reviewId);
            if (existingReviewOptional.isPresent()) {
                // Delete the review
                reviewRepository.deleteById(reviewId);
                return true;
            } else {
                return false; // Review not found
            }
        } else {
            return false; // Company not found
        }
    }
}
