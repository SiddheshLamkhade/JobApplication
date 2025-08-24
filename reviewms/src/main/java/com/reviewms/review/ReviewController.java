package com.reviewms.review;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
  
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    } 

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review){
        boolean isReviewSaved = reviewService.addReview(companyId, review); 
        if(isReviewSaved)
        return new ResponseEntity<>("review added successfully", HttpStatus.CREATED);
        else 
        return new ResponseEntity<>("review not saved", HttpStatus.NOT_FOUND);
    } 
    
    
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview (@PathVariable Long reviewId, @RequestBody Review review) {
        boolean isUpdated = reviewService.updateReview(reviewId, review);
        if (isUpdated) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
        
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if (isReviewDeleted) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }
    
}


/*New URLs for this Microservice based Architecture:

GET /reviews?companyId={companyId} - Get all reviews for a specific company
POST /reviews?companyId={companyId} - Add a new review for a specific company
GET /reviews/{reviewId} - Get a specific review by its ID
PUT /reviews/{reviewId} - Update a specific review by its ID
DELETE /reviews/{reviewId} - Delete a specific review by its ID

*/