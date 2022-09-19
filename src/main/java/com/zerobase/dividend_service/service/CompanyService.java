package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.Company;
import com.zerobase.dividend_service.persist.entity.CompanyEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

	Company save(String ticker);

	Page<CompanyEntity> getAllCompany(Pageable pageable);

	void addAutocompleteKeyword(String keyword);

	// 자동완성 Trie 방식
	List<String> autocomplete(String keyword);

	// 자동완성 Like 방식
	List<String> getCompanyNamesByKeyword(String keyword);

	void deleteAutocompleteKeyword(String keyword);

	String deleteCompany(String ticker);
}
