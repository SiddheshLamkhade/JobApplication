package com.companyms.company;
import java.util.List;

import org.springframework.stereotype.Service;

import com.companyms.company.dto.ReviewMessage;
@Service
public interface CompanyService {
    List<Company> getAllCompanies();
    boolean updateCompany(Company company, Long id);
    void createCompany(Company company); 
    boolean deleteCompanyById(Long id);
    Company getCompanyById(Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);
}
