package JobApp.firstjobapp.job;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobApp.firstjobapp.job.Impl.JobServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	private JobServiceImpl jobService;
	
	public JobController(JobServiceImpl jobServiceImpl) {
		this.jobService = jobServiceImpl;
	}

	@GetMapping
	public List<Job> findall() {
		return jobService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Job> findById(@PathVariable Long id) {
		Job job=jobService.getJobById(id);
		if (job != null) return new ResponseEntity<>(job,HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public String createJob(@RequestBody Job job) {
		jobService.createJob(job);
		return "job added successfully";
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable Long id){
		boolean deleted=jobService.deleteJobById(id);
		if(deleted){
			return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateJob (@PathVariable Long id, @RequestBody Job updatedJob){				
		boolean updated = jobService.updateJob(id,updatedJob);
		if(updated){
			return new ResponseEntity<>("Job updated successfully",HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

/// For Jobs ///
/*
    Get/jobs : Get all jobs
    Get/jobs/{id} : Get a specific job by ID
    
    Post/jobs : Create a new job (RequestBody should contain the job details)
    
    Delete/jobs/{id} : Delete a specific job by ID
    
    Put/jobs/{id} : update a specific job by ID(RequestBody should contain a
    updated job details)
    
    Get/jobs/{id}/company : Get the company associated with a specific job by ID
*/


/// For Company ///
/*
 GET/ companies
 PUT/ companies/{id}
 POST/ companies
 DELETE/ companies/{id}
 GET/ companies/{id}/
*/


 /// For Reviews ///
/*
 GET/ companies/{companyId}/reviews
 POST/ companies/{companyId}/reviews
 GET/ companies/{companyId}/reviews/{reviewId}
 PUT/ companies/{companyId}/reviews/{reviewId}
 DELETE/ companies/{companyId}/reviews/{reviewId}
*/
