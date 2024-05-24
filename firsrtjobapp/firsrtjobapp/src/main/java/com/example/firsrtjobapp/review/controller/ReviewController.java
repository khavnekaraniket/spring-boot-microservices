package com.example.firsrtjobapp.review.controller;


import com.example.firsrtjobapp.review.entity.Review;
import com.example.firsrtjobapp.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId) {
        try {
            List<Review> reviews = reviewService.getAllReviews(companyId);
            if (reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reviews);
            } else {
                return ResponseEntity.ok(reviews);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReviews(@PathVariable Long companyId, @RequestBody Review review) {
        try {
           boolean isReviewSaved = reviewService.addReview(companyId, review);
           if(isReviewSaved)
            return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
           else
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add review");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add review");
        }
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable ("companyId") Long companyId, @PathVariable ("reviewId") Long reviewId) {
        try {
            Review review = reviewService.getReview(companyId, reviewId);
            if (review != null) {
                return ResponseEntity.ok(review);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable("companyId") Long companyId,
                                               @PathVariable("reviewId") Long reviewId,
                                               @RequestBody Review updatedReview) {
        boolean success = reviewService.updateReview(companyId, reviewId, updatedReview);
        if (success) {
            return ResponseEntity.ok("Review updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found or company not found");
        }
    }
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(companyId, reviewId);
        if (isDeleted) {
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }
    }

}
