package com.jobms.job.Impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import com.jobms.job.Job;
import com.jobms.job.JobRepository;
import com.jobms.job.clients.CompanyClient;
import com.jobms.job.clients.ReviewClient;
import com.jobms.job.dto.JobDTO;
import com.jobms.job.external.Company;
import com.jobms.job.external.Review;
import com.jobms.job.mapper.JobMapper;

@Service
public class JobServiceImpl{
    
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate; 

    private CompanyClient companyClient;
    private ReviewClient  reviewClient;
    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient=companyClient;
        this.reviewClient=reviewClient;
    }
    
    /*
    * ============================================================
    * |                    USECASE OF RESTTEMPLATE               |
    * ============================================================
    */    

    public List<JobDTO> findAll() {  
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDTOs = new ArrayList<>();

        for (Job job : jobs) {
            Company company = null;
            Long companyId = job.getCompanyId();
            if (companyId != null) {
                try {
                    //Normally, a RestTemplate makes calls to a specific URL like:
// company = restTemplate.getForObject("http://localhost:8081/companies/{id}", Company.class, companyId);
                    // But here, we are using Eureka service discovery with @LoadBalanced RestTemplate.
                    //If you use a Load-Balanced RestTemplate, you annotate it with @LoadBalanced
//Now, you can call services by service name (not host/port):
//restTemplate.getForObject("http://PRODUCT-SERVICE/products", String.class);
                    // Use Eureka service ID with @LoadBalanced RestTemplate (no fixed port)
                    company = companyClient.getCompany(job.getCompanyId());
                } catch (HttpClientErrorException.NotFound e) {
                    // Company not found for this job; leave as null
                    company = null;
                } catch (RestClientException e) {
                    // companyms unavailable or other client error; proceed gracefully
                    company = null;
                }
            }

            List<Review> reviews=reviewClient.getReviews(job.getCompanyId());

            JobDTO jobWithCompanyDTO =JobMapper.mapToJobWithCompanyDTO(job, company,reviews);
            jobWithCompanyDTO.setCompany(company);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }

        return jobWithCompanyDTOs;
    }

    

   
    public void createJob(Job job) {
        // Remove the manual ID setting - let the database auto-generate it
        jobRepository.save(job);
    }


    public Job getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return job;
       
    }

    // Build a JobDTO (job + company + reviews) for a single job id
    public JobDTO getJobDtoById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) return null;

        Company company = null;
        java.util.List<Review> reviews = null;

        Long companyId = job.getCompanyId();
        if (companyId != null) {
            try { company = companyClient.getCompany(companyId); } catch (Exception e) { company = null; }
            try { reviews = reviewClient.getReviews(companyId); } catch (Exception e) { reviews = null; }
        }

        return JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
    }

    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);
        //Optional class is used as return type....
        if(jobOptional.isPresent())
        {  
                //isPresent() it is method of Optional class
                Job job=jobOptional.get();
                //get in method of Optional class HOVER on it to see the details
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                //to update in the database , You must save it.
                return true;            
        }
        return false;
    }



    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }


}
