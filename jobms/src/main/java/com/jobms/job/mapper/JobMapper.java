package com.jobms.job.mapper;

import java.util.List;

import com.jobms.job.Job;
import com.jobms.job.dto.JobDTO;
import com.jobms.job.external.Company;
import com.jobms.job.external.Review;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDTO(Job job, Company company,List<Review> reviews) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setLocation(job.getLocation());
        dto.setCompany(company);
        dto.setReview(reviews);
        return dto;
    }
}
