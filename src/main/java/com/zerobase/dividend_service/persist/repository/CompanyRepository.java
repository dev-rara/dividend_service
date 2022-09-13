package com.zerobase.dividend_service.persist.repository;

import com.zerobase.dividend_service.persist.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
