package com.jobms.job.Impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.jobms.job.Job;
import com.jobms.job.JobRepository;
import com.jobms.job.dto.JobWithCompanyDTO;
import com.jobms.job.external.Company;

@Service
public class JobServiceImpl{
    
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    /*
    * ============================================================
    * |                    USECASE OF RESTTEMPLATE               |
    * ============================================================
    */    

    public List<JobWithCompanyDTO> findAll() {  
        List<Job> jobs=jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs=new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();  
        for(Job job:jobs){
            Company company = restTemplate
            .getForObject("http://localhost:8081/companies/"+job.getCompanyId(), Company.class);  

            JobWithCompanyDTO jobWithCompanyDTO=new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);
            jobWithCompanyDTO.setCompany(company);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }

        //Company company= restTemplate.getForObject("http://localhost:8081/companies/1", Company.class);  
        //It converts the json response from  *the url* to *Company class object*
        // System.out.println(company.getName());
        // System.out.println(company.getDescription());
        // System.out.println(company.getId());
        return jobWithCompanyDTOs;
    }


   
    public void createJob(Job job) {
        // Remove the manual ID setting - let the database auto-generate it
        jobRepository.save(job);
    }



    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
