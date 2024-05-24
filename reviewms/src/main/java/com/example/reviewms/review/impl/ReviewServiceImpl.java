package com.example.reviewms.review.impl;


import com.example.reviewms.review.entity.Review;
import com.example.reviewms.review.repository.ReviewRepository;
import com.example.reviewms.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if (companyId != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReview(Long reviewId) {
       return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        // Check if the company exists
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            // Update the review
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            // Set the company for the updated review
            review.setCompanyId(updatedReview.getCompanyId());
            // Save the updated review
            reviewRepository.save(review);
            return true;
        } else {
            return false; // Company not found
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        // Check if the company exists

        if (review != null) {
            // Check if the review exists
            reviewRepository.deleteById(reviewId);
            return true;

        } else {
            return false; // Company not found
        }
    }
}
