package com.example.reviewms.review.controller;



import com.example.reviewms.review.entity.Review;
import com.example.reviewms.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
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

    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId, @RequestBody Review review) {
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

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview( @PathVariable ("reviewId") Long reviewId) {
//        try {
//            Review review = reviewService.getReview(companyId, reviewId);
//            if (review != null) {
//                return ResponseEntity.ok(review);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
        return  new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
                                               @PathVariable("reviewId") Long reviewId,
                                               @RequestBody Review updatedReview) {
        boolean success = reviewService.updateReview( reviewId, updatedReview);
        if (success) {
            return ResponseEntity.ok("Review updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found or company not found");
        }
    }
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview( @PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if (isDeleted) {
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }
    }

}
