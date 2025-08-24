package com.reviewms.review;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Long companyId, Review review);
    Review getReviewById(Long companyId, Long reviewId);
    Review getReview(Long reviewId);
    boolean updateReview(Long reviewId, Review review);
    boolean deleteReview(Long reviewId);
}
