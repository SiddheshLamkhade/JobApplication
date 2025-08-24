package com.reviewms.review.Impl;
import com.reviewms.review.Review;
import com.reviewms.review.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import com.reviewms.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews= reviewRepository.findByCompanyId(companyId);
        return reviews; 
    }

    @Override
    public boolean addReview(Long companyId, Review review) {  
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);  
        return reviews.stream().filter(review->review.getId().equals(reviewId)).findFirst().orElse(null);               
    }


    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if (existingReview != null) {
            existingReview.setTitle(updatedReview.getTitle());
            existingReview.setDescription(updatedReview.getDescription());  
            existingReview.setRating(updatedReview.getRating());
            existingReview.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(existingReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null) {
            reviewRepository.delete(review);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Review getReview(Long reviewId) {      
        return reviewRepository.findById(reviewId).orElse(null);
    }

}
