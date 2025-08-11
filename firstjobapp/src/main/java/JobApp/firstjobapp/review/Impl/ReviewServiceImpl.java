package JobApp.firstjobapp.review.Impl;

import java.util.List;
import org.springframework.stereotype.Service;
import JobApp.firstjobapp.company.Company;
import JobApp.firstjobapp.company.CompanyService;
import JobApp.firstjobapp.review.Review;
import JobApp.firstjobapp.review.ReviewRepository;
import JobApp.firstjobapp.review.ReviewService;
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;
    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;   
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews= reviewRepository.findByCompanyId(companyId);
        return reviews; 
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company= companyService.getCompanyById(companyId); 
        if (company != null){
            review.setCompany(company);
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
    public Review getReview(Long companyId, Long reviewId) {
        return reviewRepository.findById(reviewId)
            .filter(review -> java.util.Objects.equals(companyId, review.getCompany().getId()))
            .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review review) {
        Review existingReview = getReview(companyId, reviewId);
        if (existingReview != null) {
            review.setId(reviewId);
            review.setCompany(existingReview.getCompany());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!=null && 
        reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(company, companyId);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else{
            return false;
        }
    }

}
