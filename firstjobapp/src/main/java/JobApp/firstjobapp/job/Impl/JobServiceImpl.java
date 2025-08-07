package JobApp.firstjobapp.job.Impl;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import JobApp.firstjobapp.job.Job;
import JobApp.firstjobapp.job.JobRepository;

@Service
public class JobServiceImpl{
    //List<Job> jobs = new ArrayList<>();
    JobRepository jobrRepository;

        
    public JobServiceImpl(JobRepository jobrRepository) {
        this.jobrRepository = jobrRepository;
    }
  

    
    public List<Job> findAll() {       
        return jobrRepository.findAll();
    }

   
    public void createJob(Job job) {
        // Remove the manual ID setting - let the database auto-generate it
        jobrRepository.save(job);
    }

    public Job getJobById(Long id) {
        return jobrRepository.findById(id).orElse(null);
    }

    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobrRepository.findById(id);
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
                jobrRepository.save(job);
                //to update in the database , You must save it.
                return true;            
        }
        return false;
    }

    public boolean deleteJobById(Long id) {
        try{
            jobrRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
