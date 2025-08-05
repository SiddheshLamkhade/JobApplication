package JobApp.firstjobapp.company;

import java.util.List;

import JobApp.firstjobapp.job.Job;
import jakarta.persistence.OneToMany;

public class CompanyService {
    private long id;
    private String name;
    private String description;
    @OneToMany 
    private List<Job> jobs;

    
    
}
