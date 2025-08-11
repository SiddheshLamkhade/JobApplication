package JobApp.firstjobapp.review;

import com.fasterxml.jackson.annotation.JsonIgnore;

import JobApp.firstjobapp.company.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double rating;

    @JsonIgnore
    @ManyToOne
    private Company company;

    public Review(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }   

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}

/*
Get / companies / {companyId} / reviews 
Post / companies / {companyId} / reviews 
Get / companies / {companyId} / reviews / {reviewId}
Put / companies / {companyId} / reviews / {reviewId} 
Delete / companies / {companyId} / reviews / {reviewId}
*/
