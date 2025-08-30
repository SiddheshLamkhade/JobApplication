package com.companyms.company.impl;
import java.util.List;
import java.util.Optional;
import com.companyms.company.Company;
import com.companyms.company.CompanyRepository;
import com.companyms.company.CompanyService;
import com.companyms.company.clients.ReviewClient;
import com.companyms.company.dto.ReviewMessage;

import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
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

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElse(null);
        if (company != null) {
            Double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
            company.setRating(averageRating);
            companyRepository.save(company);
        }
        
    }

}
