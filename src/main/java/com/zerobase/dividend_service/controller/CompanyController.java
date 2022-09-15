package com.zerobase.dividend_service.controller;

import com.zerobase.dividend_service.model.Company;
import com.zerobase.dividend_service.model.constants.CacheKey;
import com.zerobase.dividend_service.persist.entity.CompanyEntity;
import com.zerobase.dividend_service.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {
	private final CompanyService companyService;
	private final CacheManager redisCacheManager;

	@GetMapping("autocomplete")
	public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
		var result = companyService.autocomplete(keyword);

		return ResponseEntity.ok(result);
	}

	@GetMapping
	@PreAuthorize("hasRole('READ')")
	public ResponseEntity<?> searchCompany(final Pageable pageable) {
		Page<CompanyEntity> companies = companyService.getAllCompany(pageable);
		return ResponseEntity.ok(companies);
	}

	@PostMapping
	@PreAuthorize("hasRole('WRITE')")
	public ResponseEntity<?> adCompany(@RequestBody Company request) {
		String ticker = request.getTicker().trim();
		if (ObjectUtils.isEmpty(ticker)) {
			throw new RuntimeException("ticker is empty");
		}

		Company company = companyService.save(ticker);
		companyService.addAutocompleteKeyword(company.getName());

		return ResponseEntity.ok(company);
	}

	@DeleteMapping("/{ticker}")
	@PreAuthorize("hasRole('WRITE')")
	public ResponseEntity<?> deleteCompany(@PathVariable String ticker) {
		String companyName = companyService.deleteCompany(ticker);
		this.clearFinanceCache(companyName);

		return ResponseEntity.ok(companyName);
	}

	public void clearFinanceCache(String companyName) {
		redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyName);
	}

}
