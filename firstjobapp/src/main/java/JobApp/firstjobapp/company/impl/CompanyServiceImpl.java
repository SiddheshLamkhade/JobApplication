package JobApp.firstjobapp.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import JobApp.firstjobapp.company.Company;
import JobApp.firstjobapp.company.CompanyRepository;
import JobApp.firstjobapp.company.CompanyService;
import JobApp.firstjobapp.job.Job;
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> companyOptional=companyRepository.findById(id);
        //Optional class is used as return type....
        if(companyOptional.isPresent())
        {  
                //isPresent() it is method of Optional class
                Company companyToUpdate=companyOptional.get();
                //get in method of Optional class HOVER on it to see the details
                companyToUpdate.setDescription(company.getDescription());
                companyToUpdate.setName(company.getName());
                companyToUpdate.setJobs(company.getJobs());
               
                companyRepository.save(companyToUpdate);
                //to update in the database , You must save it.
                return true;            
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {    
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

}
